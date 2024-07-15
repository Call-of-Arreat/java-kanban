import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

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
        inMemoryTaskManager.addNewTask(task);
        final int taskId = inMemoryTaskManager.getTaskId() - 1;

        final Task taskFromManager = inMemoryTaskManager.getAnyTaskById(taskId);

        assertNotNull(taskFromManager, "Задача не найдена");
        assertEquals(task, taskFromManager);

        ArrayList<Task> allTasks = inMemoryTaskManager.getAllTasks();
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