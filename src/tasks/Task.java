package tasks;

import manager.TaskType;
import tasks.status.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int id;
    private Status status;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(String name, String description, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        status = Status.NEW;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(int taskId, String taskName, Status taskStatus, String taskDescription, Duration duration, LocalDateTime startTime) {
        this.id = taskId;
        this.name = taskName;
        this.status = taskStatus;
        this.description = taskDescription;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String taskName, String taskDescription) {
        this.name = taskName;
        this.description = taskDescription;
        status = Status.NEW;
    }

    public Task(String taskName, String taskDescription, Status taskStatus) {
        this.name = taskName;
        this.description = taskDescription;
        this.status = Status.NEW;
    }

    public String getName() {
        return name;
    }

    public void setTaskName(String taskName) {
        this.name = taskName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setTaskDescription(String taskDescription) {
        this.description = taskDescription;
    }

    public TaskType getType() {
        return TaskType.TASK;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        if (duration == null) {
            return startTime;
        }
        return startTime.plus(duration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Task{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", id=" + id + ", status=" + status + '}';
    }
}
