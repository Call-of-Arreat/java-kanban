import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    TaskManager taskManager = Managers.getDefault();

    @Test
    public void subtasksShoulbBeEqualsIfIdEquals() {
        Subtask subtask1 = new Subtask("subtask1", "asdfdasfdasf", new Epic("1", "sdsdfak"));
        Subtask subtask2 = new Subtask("subtask2", " asdf das asd", new Epic("2", "asdfsdf"));

        taskManager.addNewTask(subtask1);
        taskManager.addNewTask(subtask2);

        subtask1.setId(subtask2.getId());
        assertEquals(subtask1, subtask2, "Ошибка, подзадачи с одинаковым >>id<< должны быть равны друг другу");
    }


    @Test
    void getEpic() {
    }
}