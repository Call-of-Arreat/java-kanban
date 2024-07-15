import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    TaskManager taskManager = Managers.getDefault();

    @Test
    public void epicShoulbBeEqualsIfIdEquals() {
        Epic epic1 = new Epic("epic1", "asdfdasfdasf");
        Epic epic2 = new Epic("epic2", " asdf das asd");

        taskManager.addNewEpicTask(epic1);
        taskManager.addNewEpicTask(epic2);

        epic1.setId(epic2.getId());
        assertEquals(epic1, epic2, "Ошибка, эпики с одинаковым >>id<< должны быть равны друг другу");
    }

    @Test
    void getSubtasksList() {
        Epic epic1 = new Epic("epic1", "asdfdasfdasf");

        Subtask subtask1 = new Subtask("subtask1", "asdfdasfdasf", epic1);
        Subtask subtask2 = new Subtask("subtask2", " asdf das asd", epic1);

        taskManager.addNewEpicTask(epic1);
        taskManager.addNewSubTask(subtask1, epic1);
        taskManager.addNewSubTask(subtask2, epic1);

        List<Subtask> subtasksList = epic1.getSubtasksList();
        assertNotNull(subtasksList);

        Subtask firstSubtask = subtasksList.getFirst();
        Subtask secondSubtask = subtasksList.getLast();

        assertNotEquals(firstSubtask, secondSubtask);
        assertEquals(subtask1, firstSubtask);
        assertEquals(secondSubtask, subtask2);
    }

}