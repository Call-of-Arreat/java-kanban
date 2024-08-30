package manager;

import tasks.Epic;
import tasks.status.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {

    public int generateId();

    List<Task> getAllTasks();

    List<Subtask> getAllSubTasks();

    List<Epic> getAllEpicTasks();

    List<Task> getHistory();

    void removeAllTasks();

    Task getTaskById(Integer id);

    void addNewTask(Task newTask);

    void addNewSubTask(Subtask newTask, Epic epic);

    void addNewSubTask(Subtask newSubtask);

    void addNewEpicTask(Epic newTask);

    void updateTask(Task updateTask, Status status);

    void updateTask(Task updateTask);

    void updateSubTask(Subtask updateTask, Status status, Epic epic);

    void updateSubTask(Subtask updateSubTask);

    void updateEpicTaskStatus(Epic updateEpicTask);

    void removeSubTaskById(Integer id);

    void removeEpicById(int id);

    void removeTaskById(int id);

    void clearTasks();

    void clearEpics();

    void clearSubTasks();
}
