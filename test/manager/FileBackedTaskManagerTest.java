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
        task = new Task("testTask", "testTaskDescription", Status.IN_PROGRESS);
        epic = new Epic("testEpic", "testEpicDescription", Status.IN_PROGRESS);
        subTask = new Subtask("testSub", "testSubDescription", Status.IN_PROGRESS, 2);
    }

    @Test
    void testLoadFromFile() {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        fileBackedTaskManager.addNewTask(task);
        fileBackedTaskManager.addNewEpicTask(epic);
        fileBackedTaskManager.addNewSubTask(subTask);

        assertEquals(1, fileBackedTaskManager.taskHashMap.size());
        assertEquals(1, fileBackedTaskManager.epicHashMap.size());
        assertEquals(1, fileBackedTaskManager.subtaskHashMap.size());

        FileBackedTaskManager fileManager = FileBackedTaskManager.loadFromFile(file);

        assertEquals(fileBackedTaskManager.getAllTasks(), fileManager.getAllTasks());
        assertEquals(fileBackedTaskManager.getAllEpicTasks(), fileManager.getAllEpicTasks());
        assertEquals(fileBackedTaskManager.getAllSubTasks(), fileManager.getAllSubTasks());
    }

    @Test
    void loadFromEmptyFile() throws IOException {
        File emptyFile = File.createTempFile("emptyTest", ".csv");
        FileBackedTaskManager loadFile = FileBackedTaskManager.loadFromFile(emptyFile);
        assertNotNull(loadFile); //попробовал создать объект класса на основе пустого файла и, соответственно, сравнить с null
        assertEquals(0, loadFile.epicHashMap.size());
        assertEquals(0, loadFile.subtaskHashMap.size());
        assertEquals(0, loadFile.taskHashMap.size());
    }
}
