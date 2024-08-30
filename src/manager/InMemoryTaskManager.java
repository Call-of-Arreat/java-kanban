package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.status.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    static InMemoryHistoryManager history;
    protected Integer taskId = 1;

    protected Map<Integer, Task> taskHashMap;
    protected Map<Integer, Subtask> subtaskHashMap;
    protected Map<Integer, Epic> epicHashMap;
    protected List<Task> allTasksList;

    public InMemoryTaskManager(InMemoryHistoryManager history) {
        this.taskHashMap = new HashMap<>();
        this.subtaskHashMap = new HashMap<>();
        this.epicHashMap = new HashMap<>();
        this.allTasksList = new ArrayList<>();
        InMemoryTaskManager.history = history;
    }

    public InMemoryTaskManager() {
        history = new InMemoryHistoryManager();
    }

    @Override
    public int generateId() {
        return taskId++;
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
        newTask.setStatus(Status.NEW);
        taskHashMap.put(newTask.getId(), newTask);
        allTasksList.add(newTask);
    }

    @Override
    public void addNewSubTask(Subtask newTask, Epic epic) {
        newTask.setId(taskId);
        taskId++;
        newTask.setStatus(Status.NEW);
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
        if (epic != null) {
            epic.setSubTaskIds(taskId);
            subtaskHashMap.put(newTask.getId(), newTask);
            updateEpicTaskStatus(epic);
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


    @Override
    public void removeSubTaskById(Integer id) {
        Subtask subtask = subtaskHashMap.get(id);
        if (subtask != null) {
            Epic epic = epicHashMap.get(subtask.getEpicId());
            epic.removeSubTask(id);
            updateEpicTaskStatus(epic);
            subtaskHashMap.remove(id);
        }
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epicHashMap.get(id);
        if (epic != null) {
            for (Integer subTask : epic.getSubTaskIds()) {
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
        }
        taskHashMap.clear();
    }

    @Override
    public void clearEpics() {
        for (Subtask subTask : subtaskHashMap.values()) {
            history.remove(subTask.getId());
        }

        for (Epic epic : epicHashMap.values()) {
            history.remove(epic.getId());
        }
        subtaskHashMap.clear();
        epicHashMap.clear();
    }

    @Override
    public void clearSubTasks() {
        subtaskHashMap.clear();
        for (Epic epic : epicHashMap.values()) {
            epic.clearSubTasks();
            updateEpicTaskStatus(epic);
        }
    }
}