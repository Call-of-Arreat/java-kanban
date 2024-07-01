public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();

        // создание task
        Task task1 = new Task("Task1", "some description");
        Task task2 = new Task("Task2", "some description 2");

        Subtask subtask1 = new Subtask("Subtask1", "some subtask description");
        Subtask subtask2 = new Subtask("Subtask2", "some subtask description 2");
        Subtask subtask3 = new Subtask("Subtask3", "some subtask description 3");

        Epic epic1 = new Epic("Epic 1", "descripiton epic 1");
        Epic epic2 = new Epic("Epic 2", "descripiton epic 2");

        taskManager.addNewEpicTask(epic1);
        taskManager.addNewEpicTask(epic2);


        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        taskManager.addNewSubTask(subtask1, epic1);
        taskManager.addNewSubTask(subtask2, epic1);
        taskManager.addNewSubTask(subtask3, epic2);

        // распечатать список задач
        System.out.println("taskManager.getAllTasks = " + taskManager.getAllTasks());
        System.out.println("taskManager.getAllSubTasks = " + taskManager.getAllSubTasks());
        System.out.println("taskManager.getAllEpicTasks = " + taskManager.getAllEpicTasks());
        System.out.println();
        // меняю статусы задач
        taskManager.refreshTask(task1, Status.DONE);
        taskManager.refreshSubTask(subtask1, Status.IN_PROGRESS);
        taskManager.refreshSubTask(subtask3, Status.DONE);

        taskManager.updateEpicTask(epic1);
        taskManager.updateEpicTask(epic2);

        System.out.println("taskManager.getAllTasks = " + taskManager.getAllTasks());
        System.out.println("taskManager.getAllSubTasks = " + taskManager.getAllSubTasks());
        System.out.println("taskManager.getAllEpicTasks = " + taskManager.getAllEpicTasks());
        System.out.println();

        // удалю первую задачу и первый эпик
        taskManager.removeById(3);
        taskManager.removeById(7);

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
