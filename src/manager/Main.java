package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.status.Status;


public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        InMemoryTaskManager taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());

        // создание task
        Task task1 = new Task("Task1", "some description");
        Task task2 = new Task("Task2", "some description 2");
        Task task3 = new Task("Task3", "some description 3");
        Task task4 = new Task("Task4", "some description 4");
        Task task5 = new Task("Task5", "some description 5");
        Task task6 = new Task("Task6", "some description 6");
        Task task7 = new Task("Task7", "some description 7");
        Task task8 = new Task("Task8", "some description 8");
        Task task9 = new Task("Task9", "some description 9");
        Task task10 = new Task("Task10", "some description 10");
        Task task11 = new Task("Task11", "some description 11");

        Epic epic1 = new Epic("Epic 1", "descripiton epic 1");
        Epic epic2 = new Epic("Epic 2", "descripiton epic 2");

        Subtask subtask1 = new Subtask("Subtask1", "some subtask description", epic1.getId());
        Subtask subtask2 = new Subtask("Subtask2", "some subtask description 2", epic1.getId());
        Subtask subtask3 = new Subtask("Subtask3", "some subtask description 3", epic2.getId());


        taskManager.addNewEpicTask(epic1);
        taskManager.addNewEpicTask(epic2);


        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        taskManager.addNewTask(task3);
        taskManager.addNewTask(task4);
        taskManager.addNewTask(task5);
        taskManager.addNewTask(task6);
        taskManager.addNewTask(task7);
        taskManager.addNewTask(task8);
        taskManager.addNewTask(task9);
        taskManager.addNewTask(task10);
        taskManager.addNewTask(task11);
        taskManager.addNewSubTask(subtask1, epic1);
        taskManager.addNewSubTask(subtask2, epic1);
        taskManager.addNewSubTask(subtask3, epic2);

        // распечатать список задач
        System.out.println();
        System.out.println("распечатать список задач");
        System.out.println("taskManager.getAllTasks = " + taskManager.getAllTasks());
        System.out.println("taskManager.getAllSubTasks = " + taskManager.getAllSubTasks());
        System.out.println("taskManager.getAllEpicTasks = " + taskManager.getAllEpicTasks());
        System.out.println();

        // меняю статусы задач
        System.out.println();
        System.out.println("меняю статусы задач");
        System.out.println("У Task1 меняю с NEW на DONE");
        System.out.println("У subtask1 меняю NEW на IN_PROGRESS");
        System.out.println("У subtask3 меняю NEW на DONE");
        taskManager.updateTask(task1, Status.DONE);
        subtask2.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubTask(subtask1, Status.IN_PROGRESS, epic1);
        taskManager.updateSubTask(subtask3, Status.DONE, epic2);
        System.out.println();

        System.out.println("Задачи после обновления статусов");
        System.out.println("Таски");
        System.out.println("taskManager.getAllTasks = " + taskManager.getAllTasks());
        System.out.println("Сабтаски");
        System.out.println("taskManager.getAllSubTasks = " + taskManager.getAllSubTasks());
        System.out.println("Эпики");
        System.out.println("taskManager.getAllEpicTasks = " + taskManager.getAllEpicTasks());
        System.out.println();

        System.out.println("Выберу пару задач методом getTask");
        System.out.println(taskManager.getTaskById(2));
        System.out.println(taskManager.getTaskById(3));
        System.out.println(taskManager.getTaskById(4));
        System.out.println(taskManager.getTaskById(5));
        System.out.println(taskManager.getTaskById(6));
        System.out.println(taskManager.getTaskById(7));
        System.out.println(taskManager.getTaskById(8));
        System.out.println(taskManager.getTaskById(9));
        System.out.println(taskManager.getTaskById(10));
        System.out.println(taskManager.getTaskById(11));
        //getHistory()
        System.out.println(taskManager.getHistory());


        System.out.println();
        System.out.println("Удаляю первую задачу и первый эпик");
        // удалю первую задачу и первый эпик
        taskManager.removeTaskById(1);
        taskManager.removeEpicById(3);

        System.out.println("taskManager.getAllTasks = " + taskManager.getAllTasks());
        System.out.println("taskManager.getAllSubTasks = " + taskManager.getAllSubTasks());
        System.out.println("taskManager.getAllEpicTasks = " + taskManager.getAllEpicTasks());
        System.out.println();

        taskManager.removeAllTasks();
        System.out.println("taskManager.getAllTasks = " + taskManager.getAllTasks());
        System.out.println("taskManager.getAllSubTasks = " + taskManager.getAllSubTasks());
        System.out.println("taskManager.getAllEpicTasks = " + taskManager.getAllEpicTasks());
        System.out.println();
    }
}
