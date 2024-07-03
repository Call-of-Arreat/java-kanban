import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private Integer taskId = 1;
    private HashMap<Integer, Task> taskHashMap;
    private HashMap<Integer, Subtask> subtaskHashMap;
    private HashMap<Integer, Epic> epicHashMap;
    private ArrayList<Object> allTasksList;

    public TaskManager() {
        this.taskHashMap = new HashMap<>();
        this.subtaskHashMap = new HashMap<>();
        this.epicHashMap = new HashMap<>();
        this.allTasksList = new ArrayList<>();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskHashMap.values());
    }

    public ArrayList<Subtask> getAllSubTasks() {
        return new ArrayList<>(subtaskHashMap.values());
    }

    public ArrayList<Epic> getAllEpicTasks() {
        return new ArrayList<>(epicHashMap.values());
    }

    public void removeAllTasks() {
        taskHashMap.clear();
        subtaskHashMap.clear();
        epicHashMap.clear();
        allTasksList.clear();
    }

    public Object getTaskById(Integer id) {
        if (taskHashMap.containsKey(id)) {
            return taskHashMap.get(id);
        } else if (subtaskHashMap.containsKey(id)) {
            return subtaskHashMap.get(id);
        } else if (epicHashMap.containsKey(id)) {
            return epicHashMap.get(id);
        }
        return null;
    }


    public void addNewTask(Task newTask) {
        newTask.setId(taskId);
        taskId++;
        newTask.setStatus(Status.NEW);
        taskHashMap.put(newTask.getId(), newTask);
        allTasksList.add(newTask);
    }


    public void addNewSubTask(Subtask newTask, Epic epic) {
        newTask.setId(taskId);
        taskId++;
        newTask.setStatus(Status.NEW);
        subtaskHashMap.put(newTask.getId(), newTask);
        allTasksList.add(newTask);
        epic.setInSubtasksList(newTask);
        epic.getSubtasksList().add(newTask);
    }

    public void addNewEpicTask(Epic newTask) {
        taskId++;
        newTask.setStatus(Status.NEW);
        newTask.setId(taskId);
        epicHashMap.put(newTask.getId(), newTask);
        allTasksList.add(newTask);
    }

    public void refreshTask(Task updateTask, Status status) {
        updateTask.setStatus(status);
        taskHashMap.put(updateTask.getId(), updateTask);
    }

    public void refreshSubTask(Subtask updateTask, Status status, Epic epic) {
        updateTask.setStatus(status);
        subtaskHashMap.put(updateTask.getId(), updateTask);
        updateEpicTask(epic);
    }

    private void updateEpicTask(Epic updateEpicTask) {
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

    public void removeById(Integer id) {
        if (taskHashMap.containsKey(id)) {
            taskHashMap.remove(id);
        } else if (subtaskHashMap.containsKey(id)) {
            subtaskHashMap.remove(id);
        } else {
            ArrayList<Subtask> subtasksListToDelete = epicHashMap.get(id).getSubtasksList();
            for (Subtask subtask : subtasksListToDelete) {
                subtaskHashMap.remove(subtask.getId());
            }
            epicHashMap.remove(id);
        }


    }
}