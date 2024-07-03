import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Subtask> subtasksList;

    public Epic(String name, String description) {
        super(name, description);
        this.subtasksList = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasksList() {
        return subtasksList;
    }

    public void setInSubtasksList(Subtask subtask) {
        subtasksList.add(subtask);
    }


}
