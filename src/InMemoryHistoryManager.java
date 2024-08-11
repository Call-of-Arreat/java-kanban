import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int LIMIT_OF_HISTORY_LIST = 10;
    private final List<Task> taskInHistory;

    public InMemoryHistoryManager() {

        this.taskInHistory = new ArrayList<>(LIMIT_OF_HISTORY_LIST);
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (taskInHistory.size() >= LIMIT_OF_HISTORY_LIST) {
            taskInHistory.removeFirst();
        }
        taskInHistory.addLast(task);
    }


    @Override
    public List<Task> getHistory() {
        return taskInHistory;
    }

    @Override
    public void remove(int id) {

    }


}
