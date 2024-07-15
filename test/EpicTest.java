import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {

    TaskManager taskManager = Managers.getDefault();

    @Test
    public void subtasksShoulbBeEqualsIfIdEquals() {
        Epic epic1 = new Epic("epic1", "asdfdasfdasf");
        Epic epic2 = new Epic("epic2", " asdf das asd");

        taskManager.addNewTask(epic1);
        taskManager.addNewTask(epic2);

        epic1.setId(epic2.getId());
        assertEquals(epic1, epic2, "Ошибка, эпики с одинаковым >>id<< должны быть равны друг другу");
    }

    @Test
    void getSubtasksList() {
    }

    @Test
    void setInSubtasksList() {
    }
}