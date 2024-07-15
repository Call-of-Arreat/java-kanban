import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryTaskManagerTest {

    TaskManager taskManager = Managers.getDefault();

    @Test
    void getTaskId() {
    }

    @Test
    void getAllTasks() {
    }

    @Test
    void getAllSubTasks() {
    }

    @Test
    void getAllEpicTasks() {
    }

    @Test
    void getHistory() {
    }

    @Test
    void removeAllTasks() {
    }

    @Test
    void getAnyTaskById() {
    }

    @Test
    void addNewTask() {
        Task task = new Task("some Test Name", "some description");

        taskManager.addNewTask(task);
        int taskId = taskManager.getAllTasks().getFirst().getId();

        Task anyTaskById = taskManager.getAnyTaskById(taskId - 1);

        assertNotNull(anyTaskById, "Задача не найдена");
        assertEquals(task, anyTaskById);

        ArrayList<Task> allTasks = taskManager.getAllTasks();
        assertNotNull(allTasks, "Задачи не возвращаются.");
        assertEquals(1, allTasks.size(), "Неверное количество задач.");
        assertEquals(task, allTasks.getFirst(), "Задачи не совпадают.");

    }

    @Test
    void addNewSubTask() {
    }

    @Test
    void addNewEpicTask() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void updateSubTask() {
    }

    @Test
    void updateEpicTask() {
    }

    @Test
    void removeById() {
    }
}