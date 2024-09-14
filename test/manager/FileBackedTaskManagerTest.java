package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.status.Status;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileBackedTaskManagerTest {

    private File file;
    private Task task;
    private Epic epic;
    private Subtask subTask;

    @BeforeEach
    void beforeEach() throws IOException {
        file = File.createTempFile("test", ".csv");
        task = new Task("testTask", "testTaskDescription");
        epic = new Epic("testEpic", "testEpicDescription");
        subTask = new Subtask("testSub", "testSubDescription", 2);
    }

    @Test
    void testLoadFromFile() {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        fileBackedTaskManager.addNewTask(task);
        fileBackedTaskManager.addNewEpicTask(epic);
        fileBackedTaskManager.addNewSubTask(subTask);

        assertEquals(1, fileBackedTaskManager.getTaskHashMap().size());
        assertEquals(1, fileBackedTaskManager.getEpicHashMap().size());
        assertEquals(1, fileBackedTaskManager.getSubtaskHashMap().size());

    }

    @Test
    void loadFromEmptyFile() throws IOException {
        File emptyFile = File.createTempFile("emptyTest", ".csv");
        FileBackedTaskManager loadFile = FileBackedTaskManager.loadFromFile(emptyFile);
        assertNotNull(loadFile);
        assertEquals(0, loadFile.getEpicHashMap().size());
        assertEquals(0, loadFile.getSubtaskHashMap().size());
        assertEquals(0, loadFile.getTaskHashMap().size());
    }
}
