package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.status.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Comparator;

import java.time.Duration;
import java.time.LocalDateTime;

public class InMemoryTaskManager implements TaskManager {
    private static InMemoryHistoryManager history;
    private Integer taskId = 1;

    private Map<Integer, Task> taskHashMap = new HashMap<>();
    private Map<Integer, Subtask> subtaskHashMap = new HashMap<>();
    private Map<Integer, Epic> epicHashMap = new HashMap<>();
    private List<Task> allTasksList = new ArrayList<>();
    private Set<Task> prioritized = new TreeSet<>(Comparator.comparing(Task::getStartTime));

    public InMemoryTaskManager(InMemoryHistoryManager history) {
        InMemoryTaskManager.history = history;
    }


    public void updateEpicTime(Epic epic) {
        List<Task> subTasksList = getPrioritizedTasks().stream()
                .filter(task -> task.getType().equals(TaskType.SUBTASK))
                .filter(task -> ((Subtask) task).getEpicId() == epic.getId())
                .toList();

        if (subTasksList.isEmpty()) {
            return;
        }

        Duration duration = Duration.ofMinutes(0);
        for (Task subTask : subTasksList) {
            duration = duration.plus(subTask.getDuration());
        }

        LocalDateTime startTime = subTasksList.getFirst().getStartTime();
        LocalDateTime endTime = subTasksList.getLast().getEndTime();

        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
        epic.setDuration(duration);
    }

    public void addPrioritized(Task task) {
        if (task.getType().equals(TaskType.EPIC)) return;
        List<Task> taskList = getPrioritizedTasks();
        if (task.getStartTime() != null && task.getEndTime() != null) {
            for (Task task1 : taskList) {
                if (task1.getId() == task.getId()) prioritized.remove(task1);
                if (checkForIntersection(task, task1)) {
                    return;
                }
            }
            prioritized.add(task);
        }
    }

    private boolean checkForIntersection(Task task1, Task task2) {
        return !task1.getEndTime().isBefore(task2.getStartTime()) &&
                !task1.getStartTime().isAfter(task2.getEndTime());
    }

    private void validatePrioritized(Task task) {
        if (task == null || task.getStartTime() == null) return;
        List<Task> taskList = getPrioritizedTasks();

        for (Task someTask : taskList) {
            if (someTask == task) {
                continue;
            }
            boolean taskIntersection = checkForIntersection(task, someTask);

            if (taskIntersection) {
                throw new ManagerSaveException("Задачи - " + task.getId() + " и + " + someTask.getId()
                        + "пересекаются");
            }
        }
    }

    public static void setHistory(InMemoryHistoryManager history) {
        InMemoryTaskManager.history = history;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Map<Integer, Task> getTaskHashMap() {
        return taskHashMap;
    }

    public void setTaskHashMap(Map<Integer, Task> taskHashMap) {
        this.taskHashMap = taskHashMap;
    }

    public Map<Integer, Subtask> getSubtaskHashMap() {
        return subtaskHashMap;
    }

    public void setSubtaskHashMap(Map<Integer, Subtask> subtaskHashMap) {
        this.subtaskHashMap = subtaskHashMap;
    }

    public Map<Integer, Epic> getEpicHashMap() {
        return epicHashMap;
    }

    public void setEpicHashMap(Map<Integer, Epic> epicHashMap) {
        this.epicHashMap = epicHashMap;
    }

    public List<Task> getAllTasksList() {
        return allTasksList;
    }

    public void setAllTasksList(List<Task> allTasksList) {
        this.allTasksList = allTasksList;
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritized);
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskHashMap.values());
    }

    @Override
    public List<Subtask> getAllSubTasks() {
        return new ArrayList<>(subtaskHashMap.values());
    }

    @Override
    public List<Epic> getAllEpicTasks() {
        return new ArrayList<>(epicHashMap.values());
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }

    @Override
    public void removeAllTasks() {
        taskHashMap.clear();
        subtaskHashMap.clear();
        epicHashMap.clear();
        allTasksList.clear();
    }

    @Override
    public Task getTaskById(Integer id) {
        if (taskHashMap.containsKey(id)) {
            history.add(allTasksList.get(id));
            return taskHashMap.get(id);
        } else if (subtaskHashMap.containsKey(id)) {
            history.add(allTasksList.get(id));
            return subtaskHashMap.get(id);
        } else if (epicHashMap.containsKey(id)) {
            history.add(allTasksList.get(id));
            return epicHashMap.get(id);
        }
        return null;
    }


    @Override
    public void addNewTask(Task newTask) {
        newTask.setId(taskId);
        taskId++;
        validatePrioritized(newTask);
        addPrioritized(newTask);
        taskHashMap.put(newTask.getId(), newTask);
        allTasksList.add(newTask);
    }

    @Override
    public void addNewSubTask(Subtask newTask, Epic epic) {
        newTask.setId(taskId);
        taskId++;
        newTask.setStatus(Status.NEW);
        validatePrioritized(newTask);
        addPrioritized(newTask);
        subtaskHashMap.put(newTask.getId(), newTask);
        allTasksList.add(newTask);
        epic.setSubTaskIds(newTask.getId());
        epic.getSubTaskIds().add(newTask.getId());
        updateEpicTaskStatus(epic);
    }

    @Override
    public void addNewSubTask(Subtask newTask) {
        newTask.setId(taskId);
        Epic epic = epicHashMap.get(newTask.getId());
        validatePrioritized(newTask);
        addPrioritized(newTask);
        if (epic != null) {
            epic.setSubTaskIds(taskId);
            subtaskHashMap.put(newTask.getId(), newTask);
            updateEpicTaskStatus(epic);
            updateEpicTime(epic);
        }
    }

    @Override
    public void addNewEpicTask(Epic newTask) {
        taskId++;
        newTask.setStatus(Status.NEW);
        newTask.setId(taskId);
        epicHashMap.put(newTask.getId(), newTask);
        allTasksList.add(newTask);
    }


    @Override
    public void updateTask(Task updateTask, Status status) {
        updateTask.setStatus(status);
        taskHashMap.put(updateTask.getId(), updateTask);
    }

    @Override
    public void updateTask(Task updateTask) {
        if (taskHashMap.containsKey(updateTask.getId())) {
            taskHashMap.put(updateTask.getId(), updateTask);
            validatePrioritized(updateTask);
            addPrioritized(updateTask);
        }
    }

    @Override
    public void updateSubTask(Subtask updateSubTask, Status status, Epic epic) {
        updateSubTask.setStatus(status);
        subtaskHashMap.put(updateSubTask.getId(), updateSubTask);
        updateEpicTaskStatus(epic);
    }

    @Override
    public void updateSubTask(Subtask updateSubTask) {
        if ((updateSubTask == null) || (!subtaskHashMap.containsKey(updateSubTask.getId()))) {
            return;
        }
        Epic epic = epicHashMap.get(updateSubTask.getEpicId());
        if (epic == null) {
            return;
        }
        subtaskHashMap.replace(updateSubTask.getId(), updateSubTask);
        updateEpicTaskStatus(epic);
        updateEpicTime(epic);
        validatePrioritized(epic);
        addPrioritized(updateSubTask);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epicHashMap.containsKey(epic.getId())) {
            Epic actualEpic = epicHashMap.get(epic.getId());
            actualEpic.setTaskName(epic.getName());
            actualEpic.setTaskDescription(epic.getDescription());
        }
    }

    @Override
    public void updateEpicTaskStatus(Epic epicId) {
        if (!epicHashMap.containsKey(epicId.getId())) {
            return;
        }

        List<Subtask> epicSubTasks = new ArrayList<>();

        int countNew = 0;
        int countDone = 0;

        for (int i = 0; i < epicId.getSubTaskIds().size(); i++) {
            epicSubTasks.add(subtaskHashMap.get(epicId.getSubTaskIds().get(i)));
        }

        for (Subtask subTask : epicSubTasks) {
            if (subTask.getStatus().equals(Status.DONE)) {
                countDone++;
            } else if (subTask.getStatus().equals(Status.NEW)) {
                countNew++;
            } else {
                epicId.setStatus(Status.IN_PROGRESS);
                return;
            }

            if (countNew == epicSubTasks.size()) {
                epicId.setStatus(Status.NEW);
            } else if (countDone == epicSubTasks.size()) {
                epicId.setStatus(Status.DONE);
            } else {
                epicId.setStatus(Status.IN_PROGRESS);
            }

        }
    }

    public List<Subtask> findAllSubtaskByEpicId(int id) {
        ArrayList<Subtask> newSubtasks = new ArrayList<>();
        Epic epic = epicHashMap.get(id);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubTaskIds()) {
                newSubtasks.add(subtaskHashMap.get(subtaskId));
            }
        }
        return newSubtasks;
    }

    @Override
    public Epic findEpicById(int id) {
        history.add(epicHashMap.get(id));
        return epicHashMap.get(id);
    }

    @Override
    public Subtask findSubTaskById(int id) {
        history.add(subtaskHashMap.get(id));
        return subtaskHashMap.get(id);
    }

    @Override
    public void removeSubTaskById(Integer id) {
        Subtask subtask = subtaskHashMap.get(id);
        if (subtask != null) {
            Epic epic = epicHashMap.get(subtask.getEpicId());
            epic.removeSubTask(id);
            updateEpicTaskStatus(epic);
            updateEpicTime(epic);
            prioritized.remove(subtask);
            subtaskHashMap.remove(id);
        }
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epicHashMap.get(id);
        if (epic != null) {
            for (Integer subTask : epic.getSubTaskIds()) {
                prioritized.remove(subtaskHashMap.get(subTask));
                subtaskHashMap.remove(subTask);
            }
        }
        epicHashMap.remove(id);
    }

    @Override
    public void removeTaskById(int id) {
        taskHashMap.remove(id);
    }

    @Override
    public void clearTasks() {
        for (Task task : taskHashMap.values()) {
            history.remove(task.getId());
            prioritized.remove(task);
        }
        taskHashMap.clear();
    }

    @Override
    public void clearEpics() {
        for (Subtask subTask : subtaskHashMap.values()) {
            history.remove(subTask.getId());
            prioritized.remove(subTask);
        }

        for (Epic epic : epicHashMap.values()) {
            history.remove(epic.getId());
        }
        subtaskHashMap.clear();
        epicHashMap.clear();
    }

    @Override
    public void clearSubTasks() {
        prioritized.removeAll(epicHashMap.values());
        subtaskHashMap.clear();
        for (Epic epic : epicHashMap.values()) {
            epic.clearSubTasks();
            updateEpicTaskStatus(epic);
            updateEpicTime(epic);
        }
    }
}