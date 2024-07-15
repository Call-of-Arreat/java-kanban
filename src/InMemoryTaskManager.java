import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private Integer taskId = 1;

    public Integer getTaskId() {
        return taskId;
    }

    InMemoryHistoryManager historyManager;
    private HashMap<Integer, Task> taskHashMap;
    private HashMap<Integer, Subtask> subtaskHashMap;
    private HashMap<Integer, Epic> epicHashMap;
    private ArrayList<Task> allTasksList;

    public InMemoryTaskManager() {
        this.taskHashMap = new HashMap<>();
        this.subtaskHashMap = new HashMap<>();
        this.epicHashMap = new HashMap<>();
        this.allTasksList = new ArrayList<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskHashMap.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubTasks() {
        return new ArrayList<>(subtaskHashMap.values());
    }

    @Override
    public ArrayList<Epic> getAllEpicTasks() {
        return new ArrayList<>(epicHashMap.values());
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void removeAllTasks() {
        taskHashMap.clear();
        subtaskHashMap.clear();
        epicHashMap.clear();
        allTasksList.clear();
    }

    @Override
    public Task getAnyTaskById(Integer id) {
        if (taskHashMap.containsKey(id)) {
            historyManager.add(allTasksList.get(id));
            return taskHashMap.get(id);
        } else if (subtaskHashMap.containsKey(id)) {
            historyManager.add(allTasksList.get(id));
            return subtaskHashMap.get(id);
        } else if (epicHashMap.containsKey(id)) {
            historyManager.add(allTasksList.get(id));
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
        epic.setInSubtasksList(newTask);
        epic.getSubtasksList().add(newTask);
        updateEpicTask(epic);
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
    public void updateSubTask(Subtask updateSubTask, Status status, Epic epic) {
        updateSubTask.setStatus(status);
        subtaskHashMap.put(updateSubTask.getId(), updateSubTask);
        updateEpicTask(epic);
    }

    @Override
    public void updateEpicTask(Epic updateEpicTask) {
        if (updateEpicTask.getSubtasksList().size() == 1) {
            updateEpicTask.setStatus(updateEpicTask.getSubtasksList().getFirst().getStatus());
        } else {
            int countNew = 0;
            int countInProgress = 0;
            int countDone = 0;
            ArrayList<Subtask> subtasksList = updateEpicTask.getSubtasksList();
            for (Subtask subtask : subtasksList) {
                if (subtask.getStatus().equals(Status.NEW)) {
                    countNew++;
                } else if (subtask.getStatus().equals(Status.IN_PROGRESS)) {
                    countInProgress++;
                } else if (subtask.getStatus().equals(Status.DONE)) {
                    countDone++;
                }
            }
            if (countNew == subtasksList.size()) {
                updateEpicTask.setStatus(Status.NEW);
            } else if (countDone == subtasksList.size()) {
                updateEpicTask.setStatus(Status.DONE);
            } else {
                updateEpicTask.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    @Override
    public void removeById(Integer id) {
        if (taskHashMap.containsKey(id)) {
            taskHashMap.remove(id);
        } else if (subtaskHashMap.containsKey(id)) {
            Subtask subtask = subtaskHashMap.get(id);
            Epic epic = subtask.getEpic();
            subtaskHashMap.remove(id);
            updateEpicTask(epic);
        } else {
            ArrayList<Subtask> subtasksListToDelete = epicHashMap.get(id).getSubtasksList();
            for (Subtask subtask : subtasksListToDelete) {
                subtaskHashMap.remove(subtask.getId());
            }
            epicHashMap.remove(id);
        }
    }
}