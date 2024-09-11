package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.status.Status;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;
    private static final String HEADER = "id,type,name,description, epic\n";

    public FileBackedTaskManager(File file) {
        super(new InMemoryHistoryManager());
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileManager = new FileBackedTaskManager(file);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                Task task = taskFromString(line);
                if (TaskType.EPIC.equals(task.getType())) {
                    fileManager.getEpicHashMap().put(task.getId(), (Epic) task);
                } else if (TaskType.SUBTASK.equals(task.getType())) {
                    fileManager.getSubtaskHashMap().put(task.getId(), (Subtask) task);
                    fileManager.getEpicHashMap().get(((Subtask) task).getEpicId()).setSubTaskIds(task.getId());
                } else {
                    fileManager.getTaskHashMap().put(task.getId(), task);
                }
                if (fileManager.getTaskId() < task.getId()) {
                    fileManager.setTaskId(task.getId());
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось загрузить данные из файла");
        }
        return fileManager;
    }

    public void save() {
        try {
            if (Files.exists(file.toPath())) {
                Files.delete(file.toPath());
            }
            Files.createFile(file.toPath());
        } catch (IOException io) {
            throw new ManagerSaveException("Не удалось найти файл");
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            bufferedWriter.write(HEADER);

            for (Task task : getAllTasks()) {
                bufferedWriter.write(taskToString(task));
            }
            for (Epic epic : getAllEpicTasks()) {
                bufferedWriter.write(taskToString(epic));
            }
            for (Subtask subTask : getAllSubTasks()) {
                bufferedWriter.write(taskToString(subTask));
            }
        } catch (IOException io) {
            throw new ManagerSaveException("Не удалось сохранить файл");
        }

    }

    private static String taskToString(Task task) {
        return task.getId() + "," +
                task.getType() + "," +
                task.getName() + "," +
                task.getStatus() + "," +
                task.getDescription() +
                getEpicId(task) + "\n";

    }

    private static Task taskFromString(String value) {
        String[] lines = value.split(",");
        int id = Integer.parseInt(lines[0]);
        String taskType = lines[1];
        String name = lines[2];
        Status status = Status.valueOf(lines[3]);
        String description = lines[4];
        if ("EPIC".equals(taskType)) {
            return new Epic(id, name, status, description);
        } else if ("SUBTASK".equals(taskType)) {
            int epicId = Integer.parseInt(lines[5]);
            return new Subtask(id, name, status, description, epicId);
        } else {
            return new Task(id, name, status, description);
        }
    }

    private static String getEpicId(Task task) {
        if (TaskType.SUBTASK.equals(task.getType())) {
            return "," + (task).getId();
        } else {
            return "";
        }
    }

    @Override
    public void addNewTask(Task task) {
        super.addNewTask(task);
        save();
    }

    @Override
    public void addNewEpicTask(Epic newTask) {
        super.addNewEpicTask(newTask);
        save();
    }

    @Override
    public void addNewSubTask(Subtask newTask) {
        super.addNewSubTask(newTask);
        save();
    }

    @Override
    public void updateTask(Task updateTask) {
        super.updateTask(updateTask);
        save();
    }

    @Override
    public void updateSubTask(Subtask updateSubTask, Status status, Epic epic) {
        super.updateSubTask(updateSubTask, status, epic);
        save();
    }

    @Override
    public void updateSubTask(Subtask updateSubTask) {
        super.updateSubTask(updateSubTask);
        save();
    }

    @Override
    public void updateEpicTaskStatus(Epic epicId) {
        super.updateEpicTaskStatus(epicId);
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeSubTaskById(Integer id) {
        super.removeSubTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void clearTasks() {
        super.clearTasks();
        save();
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        save();
    }

    @Override
    public void clearSubTasks() {
        super.clearSubTasks();
        save();
    }
}