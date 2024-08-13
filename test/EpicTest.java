import org.junit.jupiter.api.Test;

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

        Subtask subtask1 = new Subtask("subtask1", "asdfdasfdasf", epic1.getId());
        Subtask subtask2 = new Subtask("subtask2", " asdf das asd", epic1.getId());

        taskManager.addNewEpicTask(epic1);
        taskManager.addNewSubTask(subtask1, epic1);
        taskManager.addNewSubTask(subtask2, epic1);

        List<Integer> subTaskIds = epic1.getSubTaskIds();
        assertNotNull(subTaskIds);

        Integer first = subTaskIds.getFirst();
        Integer last = subTaskIds.getLast();

        assertNotEquals(first, last);
        assertEquals(subtask1.getId(), first);
        assertEquals(last, subtask2.getId());
    }

}