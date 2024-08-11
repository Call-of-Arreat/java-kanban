import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Subtask> subtasksList;
    private final List<Integer> subTaskIds;

    public Epic(String name, String description, List<Integer> subTaskIds) {
        super(name, description);
        this.subTaskIds = subTaskIds;
        this.subtasksList = new ArrayList<>();
    }

    public List<Subtask> getSubtasksList() {
        return subtasksList;
    }

    public void setInSubtasksList(Subtask subtask) {
        subtasksList.add(subtask);
    }


}
