package manager;

import tasks.Epic;
import tasks.status.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getAllTasks();

    List<Subtask> getAllSubTasks();

    List<Epic> getAllEpicTasks();

    List<Task> getHistory();

    void removeAllTasks();

    Task getTaskById(Integer id);

    void addNewTask(Task newTask);

    void addNewSubTask(Subtask newTask, Epic epic);

    void addNewEpicTask(Epic newTask);

    void updateTask(Task updateTask, Status status);

    void updateSubTask(Subtask updateTask, Status status, Epic epic);

    void updateEpicTaskStatus(Epic updateEpicTask);

    void removeSubTaskById(Integer id);

    void removeEpicById(int id);

    void removeTaskById(int id);
}
