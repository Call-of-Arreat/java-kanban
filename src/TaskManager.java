import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface TaskManager {
    List<Task> getAllTasks();

    List<Subtask> getAllSubTasks();

    List<Epic> getAllEpicTasks();

    List<Task> getHistory();

    void removeAllTasks();

    Task getAnyTaskById(Integer id);

    void addNewTask(Task newTask);

    void addNewSubTask(Subtask newTask, Epic epic);

    void addNewEpicTask(Epic newTask);

    void updateTask(Task updateTask, Status status);

    void updateSubTask(Subtask updateTask, Status status, Epic epic);

    void updateEpicTask(Epic updateEpicTask);

    void removeById(Integer id);
}
