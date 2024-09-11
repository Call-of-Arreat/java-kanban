package tasks;

import manager.TaskType;
import tasks.status.Status;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subTaskIds;

    public Epic(String name, String taskDescription) {
        super(name, taskDescription);
        subTaskIds = new ArrayList<>();
    }

    public Epic(String taskName, String taskDescription, Status taskStatus) {
        super(taskName, taskDescription, taskStatus);
        subTaskIds = new ArrayList<>();
    }

    public Epic(int taskId, String taskName, Status taskStatus, String taskDescription) {
        super(taskId, taskName, taskStatus, taskDescription);
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

    public TaskType getType() {
        return TaskType.EPIC;
    }

}
