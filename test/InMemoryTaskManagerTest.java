import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryTaskManagerTest {

    TaskManager taskManager = Managers.getDefault();

    @Test
    void addNewTask() {
        Task task = new Task("some Test Name", "some description");

        taskManager.addNewTask(task);
        int taskId = task.getId();
        Task anyTaskById = taskManager.getAnyTaskById(taskId - 1);

        assertNotNull(anyTaskById, "Задача не найдена");
        assertEquals(task, anyTaskById);

        List<Task> allTasks = taskManager.getAllTasks();
        assertNotNull(allTasks, "Задачи не возвращаются.");
        assertEquals(1, allTasks.size(), "Неверное количество задач.");
        assertEquals(task, allTasks.getFirst(), "Задачи не совпадают.");

    }

}