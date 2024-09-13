package tasks;

import manager.TaskType;
import tasks.status.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subTaskIds;
    private LocalDateTime endTime;

    public Epic(String name, String taskDescription) {
        super(name, taskDescription);
        subTaskIds = new ArrayList<>();
    }

    public Epic(int taskId, String taskName, Status taskStatus, String taskDescription, LocalDateTime startTime,
                LocalDateTime endTime, Duration duration) {
        super(taskId, taskName, taskStatus, taskDescription, duration, startTime);
        subTaskIds = new ArrayList<>();
        this.endTime = endTime;
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

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

}
