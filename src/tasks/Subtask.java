package tasks;

import manager.TaskType;
import tasks.status.Status;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {

    private final int epicId;

    public Subtask(int taskId, String taskName, Status taskStatus, String taskDescription, int epicId,
                   Duration duration, LocalDateTime startTime) {
        super(taskId, taskName, taskStatus, taskDescription, duration, startTime);
        this.epicId = epicId;

    }

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                "} " + super.toString();
    }

}
