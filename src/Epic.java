import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subTaskIds;

    public Epic(String name, String taskDescription) {
        super(name, taskDescription);
        subTaskIds = new ArrayList<>();
    }

    public List<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void setSubTaskIds(int id) {
        subTaskIds.add(id);
    }

    public void removeSubTask(Integer id) {
        subTaskIds.remove(id);
    }

    public void clearSubTasks() {
        subTaskIds.clear();
    }

}
