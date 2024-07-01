import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    private ArrayList<Subtask> subtasksList;
    private ArrayList<Integer> subTasksIds;
    private HashMap<Epic, ArrayList<Subtask>> mapOfEpicSubtasks;

    public Epic(String name, String description) {
        super(name, description);
        this.subtasksList = new ArrayList<>();
        this.subTasksIds = new ArrayList<>();
    }

    public HashMap<Epic, ArrayList<Subtask>> getMapOfEpicSubtasks() {
        return mapOfEpicSubtasks;
    }

    public void setMapOfEpicSubtasks(HashMap<Epic, ArrayList<Subtask>> mapOfEpicSubtasks) {
        this.mapOfEpicSubtasks = mapOfEpicSubtasks;
    }

    public ArrayList<Integer> getSubTasksIds() {
        return subTasksIds;
    }

    public ArrayList<Subtask> getSubtasksList() {
        return subtasksList;
    }

    public void setInSubtasksList(Subtask subtask) {
        subtasksList.add(subtask);
    }


}
