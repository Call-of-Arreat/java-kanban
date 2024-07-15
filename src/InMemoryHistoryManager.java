import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final static int LIMIT_OF_HISTORY_LIST = 10;
    private List<Task> taskInHistory;

    public InMemoryHistoryManager() {

        this.taskInHistory = new ArrayList<>(10);
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

}
