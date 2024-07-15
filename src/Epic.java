import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final ArrayList<Subtask> subtasksList;


    public Epic(String name, String description) {
        super(name, description);
        this.subtasksList = new ArrayList<>();
    }

    public List<Subtask> getSubtasksList() {
        return subtasksList;
    }

    public void setInSubtasksList(Subtask subtask) {
        subtasksList.add(subtask);
    }


}
