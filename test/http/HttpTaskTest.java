package http;

import com.google.gson.Gson;
import manager.Managers;
import manager.TaskManager;
import manager.http.HttpTaskServer;
import manager.http.tokens.TaskTypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tasks.Task;
import tasks.status.Status;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskTest {

    // создаём экземпляр InMemoryTaskManager
    private final TaskManager manager = Managers.getDefault();
    // передаём его в качестве аргумента в конструктор HttpTaskServer
    private final HttpTaskServer taskServer = new HttpTaskServer(manager);
    private Gson gson = taskServer.getGson();
    private static final String BASE_URL = "http://localhost:8080/tasks";

    @BeforeEach
    void setUp() {
        manager.clearTasks();
        manager.clearSubTasks();
        manager.clearEpics();
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    void getTask() throws IOException, InterruptedException {
        Task task1 = new Task(1, "task1", Status.NEW, "description1",
                (Duration.ofMinutes(15)), LocalDateTime.of(2024, 8, 24, 20, 40));
        Task task2 = new Task(2, "task2", Status.NEW, "description2",
                (Duration.ofMinutes(15)), LocalDateTime.of(2024, 8, 25, 20, 40));
        Task task3 = new Task(3, "task3", Status.NEW, "description3",
                Duration.ofMinutes(15),
                LocalDateTime.of(2024, 8, 26, 20, 40));

        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewTask(task3);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create(BASE_URL);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Task> fromManager = manager.getAllTasks();
        List<Task> fromHttp = gson.fromJson(response.body(), new TaskTypeToken().getType());

        assertEquals(fromManager.size(), fromHttp.size());
        assertEquals(fromManager.get(0), fromHttp.get(0));
        assertEquals(fromManager.get(1), fromHttp.get(1));
        assertEquals(fromManager.get(2), fromHttp.get(2));
        client.close();
    }


    @Test
    void testAddTask() throws IOException, InterruptedException {
        // создаём задачу
        Task task = new Task("Test 2", "Testing task 2");
        task.setDuration(Duration.ofMinutes(5));
        task.setStartTime(LocalDateTime.now());

        // конвертируем её в JSON
        String taskJson = gson.toJson(task);

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode());

        // проверяем, что создалась одна задача с корректным именем
        List<Task> tasksFromManager = manager.getAllTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 2", tasksFromManager.getFirst().getName(), "Некорректное имя задачи");
        client.close();
    }

    @Test
    void testUpdateTask() throws IOException, InterruptedException {
        Task task1 = new Task(1, "task1", Status.NEW, "description1",
                Duration.ofMinutes(15),
                LocalDateTime.of(2024, 8, 24, 15, 30));
        Task newTask1 = new Task(1, "task2", Status.IN_PROGRESS, "description2",
                Duration.ofMinutes(15),
                LocalDateTime.of(2024, 8, 24, 16, 30));
        manager.addNewTask(task1);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create(BASE_URL + "/" + task1.getId());
        String taskJson = gson.toJson(newTask1);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        client.close();
    }

    @Test
    void testStatusCode200() throws IOException, InterruptedException {
        Task task1 = new Task(1, "task1", Status.NEW, "description1",
                Duration.ofMinutes(15),
                LocalDateTime.of(2024, 8, 24, 15, 30));
        Task newTask1 = new Task(2, "task2", Status.IN_PROGRESS, "description2",
                Duration.ofMinutes(15),
                LocalDateTime.of(2024, 8, 24, 15, 30));
        manager.addNewTask(task1);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create(BASE_URL + "/" + task1.getId());
        String taskJson = gson.toJson(newTask1);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        client.close();
    }

    @Test
    void deleteTask() throws IOException, InterruptedException {
        Task task1 = new Task("task1", "description1");
        manager.addNewTask(task1);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create(BASE_URL + "/" + task1.getId());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(1, manager.getAllTasks().size());
        client.close();
    }

}
