package tasks;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    TaskManager taskManager = Managers.getDefault();

    @Test
    public void tasksShoulbBeEqualsIfIdEquals() {
        Task task1 = new Task("Task1", "asdfdasfdasf");
        Task task2 = new Task("Task2", " asdf das asd");

        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);

        task1.setId(task2.getId());
        assertEquals(task1, task2, "Ошибка, задачи с одинаковым >>id<< должны быть равны друг другу");
    }

}