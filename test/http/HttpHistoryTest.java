package http;

import com.google.gson.Gson;
import manager.Managers;
import manager.TaskManager;
import manager.http.HttpTaskServer;
import manager.http.tokens.TaskTypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpHistoryTest {
    // создаём экземпляр InMemoryTaskManager
    private final TaskManager manager = Managers.getDefault();
    // передаём его в качестве аргумента в конструктор HttpTaskServer
    private final HttpTaskServer taskServer = new HttpTaskServer(manager);
    private Gson gson = taskServer.getGson();
    private static final String BASE_URL = "http://localhost:8080/history";

    @BeforeEach
    void setUp() {
        manager.clearTasks();
        manager.clearSubTasks();
        manager.clearEpics();
        taskServer.start();
    }

    @AfterEach
    void shutDown() {
        taskServer.stop();
    }

    @Test
    void getHistory() throws IOException, InterruptedException {
        Task task1 = new Task(1, "task", Status.NEW, "description",
                Duration.ofMinutes(15),
                LocalDateTime.of(2024, 8, 24, 20, 40));
        Epic epic2 = new Epic(12, "epic", Status.NEW, "description",
                LocalDateTime.of(2024, 8, 24, 10, 0),
                LocalDateTime.of(2024, 8, 24, 10, 15),
                Duration.ofMinutes(15));
        Subtask subtask3 = new Subtask(4, "subtask5", Status.NEW, "sfsfasfas", 12, Duration.ofMinutes(12), LocalDateTime.of(2024, 8, 26, 20, 40));

        manager.addNewTask(task1);
        manager.addNewEpicTask(epic2);
        manager.addNewSubTask(subtask3);

        //для добавления в историю воспользуемся TaskManager
        manager.findEpicById(epic2.getId());
        manager.getTaskById(task1.getId());
        manager.findSubTaskById(subtask3.getId());

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create(BASE_URL);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Task> historyByHttp = gson.fromJson(response.body(), new TaskTypeToken().getType());

        assertEquals(200, response.statusCode());

    }
}