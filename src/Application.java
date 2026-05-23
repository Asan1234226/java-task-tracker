import java.awt.image.SinglePixelPackedSampleModel;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws Exception {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        loadFromFile(taskManager);
        while (true) {
            printMenu(scanner);
            int operation = Integer.parseInt(scanner.nextLine());
            if (operation == 1) {
                createTask(taskManager, scanner);
            } else if (operation == 2) {
                displayAllTasks(taskManager, scanner);
            } else if (operation == 3) {
                findTaskById(taskManager, scanner);
            } else if (operation == 4) {
                findTaskByStatus(taskManager, scanner);
            } else if (operation == 5) {
                removeTaskById(taskManager, scanner);
            } else if (operation == 6) {
                updateTaskStatus(taskManager, scanner);
            } else if (operation == 7) {
                removeAllTasks(taskManager);
            } else if (operation == 8) {
                history(taskManager);
            } else if (operation == 0) {
                saveTasksToFile(taskManager);
                break;
            }
        }
    }

    public static void printMenu(Scanner scanner) {
        System.out.println("1. Создать задачу");
        System.out.println("2. Вывести список всех задач");
        System.out.println("3. Найти задачу по id");
        System.out.println("4. Найти задачи по статусу");
        System.out.println("5. Удалить задачу по id");
        System.out.println("6. Обновить статус задачи");
        System.out.println("7. Удалить все задачи");
        System.out.println("8. История");
        System.out.println("0. Выход");
        System.out.println("Выберите действие:");
    }

    public static void createTask(TaskManager taskManager, Scanner scanner) throws Exception {
        System.out.println("1. Task");
        System.out.println("2. Epic");
        System.out.println("3. Subtask");
        System.out.println("Выберите тип задачи");
        int taskNum = Integer.parseInt(scanner.nextLine());
        if (taskNum == 1) {
            System.out.println("Введите название задачи:");
            String name = scanner.nextLine();

            System.out.println("Введите описание задачи:");
            String description = scanner.nextLine();

            Task task = new Task(name, description);
            taskManager.createTask(task);
            saveTasksToFile(taskManager);
            System.out.println("Задача создана: " + task.getId() + " " + task.getTitle() + " " + task.getStatus());
        } else if (taskNum == 2) {
            System.out.println("Введите эпик задачу:");
            String epic = scanner.nextLine();
            System.out.println("Введите описание эпик:");
            String description = scanner.nextLine();
            Epic epic1 = new Epic(epic, description);
            taskManager.createEpic(epic1);
            saveTasksToFile(taskManager);
            System.out.println("Задача создана: " + epic1.getId() + " " + epic1.getTitle() + " " + epic1.getStatus());
        } else if (taskNum == 3) {
            System.out.println("Введите название подзадачи:");
            String name = scanner.nextLine();

            System.out.println("Введите описание подзадачи:");
            String description = scanner.nextLine();

            System.out.println("Введите Id эпика:");
            int id = Integer.parseInt(scanner.nextLine());

            Epic epic = taskManager.getEpicById(id);
            if (epic == null) {
                System.out.println("Id не найден");
            } else {
                Subtask subtask = new Subtask(name, description, epic);
                taskManager.createSubtask(subtask);
                subtask.getEpic().refreshStatus();
                saveTasksToFile(taskManager);
                System.out.println(subtask.getId() + " " + subtask.getTitle() + " " + subtask.getStatus() + " (" + "epicId=" + subtask.getId() + ")");
            }
        }
    }

    public static void displayAllTasks(TaskManager taskManager, Scanner scanner) {
        ArrayList<Task> tasks = taskManager.getTasks();
        ArrayList<Epic> epics = taskManager.getEpic();
        ArrayList<Subtask> subtasks = taskManager.getSubtask();
        if (tasks.isEmpty() && epics.isEmpty() && subtasks.isEmpty()) {
            System.out.println("Список пуст");
        } else {
            for (Task task : tasks) {
                System.out.println(task.getId() + " [TASK] " + " " + task.getTitle() + " " + task.getStatus());
            }
            for (Epic epic : epics) {
                System.out.println(epic.getId() + " [EPIC] " + " " + epic.getTitle() + " " + epic.getStatus());
                for (Subtask sub : epic.getSubtasks()) {
                    System.out.println(sub.getId() + " [SUBTASK] " + " " + sub.getTitle() + " " + sub.getStatus());
                }
            }
        }
    }

    public static void history(TaskManager taskManager) throws  Exception {
        ArrayList<Task> tasks1 = taskManager.getHistory();
        FileWriter writer = new FileWriter("history.txt");
        for (Task task : tasks1) {
            writer.write(task.getId() + "," + task.getTitle() + "," + task.getStatus() + "\n");
            System.out.println(task.getId() + " " + task.getTitle() + " " + task.getStatus());
        }
        writer.close();
    }

    public static void findTaskById(TaskManager taskManager, Scanner scanner) {
        System.out.println("Введите id задачи:");
        int id = Integer.parseInt(scanner.nextLine());

        Task task = taskManager.getTaskById(id);
        if (task != null) {
            System.out.println(task.getId() + " " + task.getTitle() + " " + task.getStatus());
            taskManager.saveToHistory(task);
            return;
        }
        Epic epic = taskManager.getEpicById(id);
        if (epic != null) {
            System.out.println(epic.getId() + " " + epic.getTitle() + " " + epic.getStatus());
            taskManager.saveToHistory(epic);
            return;
        }
        Subtask subtask = taskManager.getSubtaskById(id);
        if (subtask != null) {
            System.out.println(subtask.getId() + " " + subtask.getTitle() + " " + subtask.getStatus());
            taskManager.saveToHistory(subtask);
            return;
        } else {
            System.out.println("Id не найден");
        }
    }

    public static void findTaskByStatus(TaskManager taskManager, Scanner scanner) {
        System.out.println("Введите статус задачи:");
        Tasktatus status = Tasktatus.valueOf(scanner.nextLine());
        for (Task task : taskManager.getTasksByStatus(status)) {
            System.out.println(task.getId() + " " + task.getTitle() + " " + status);
        }
        for (Epic epic : taskManager.getEpicByStatus(status)) {
            System.out.println(epic.getId() + " " + epic.getTitle() + " " + epic.getStatus());
        }

        for (Subtask subtask : taskManager.getSubtaskByStatus(status)) {
            System.out.println(subtask.getId() + " " + subtask.getTitle() + " " + subtask.getStatus());
        }
    }

    public static void removeTaskById(TaskManager taskManager, Scanner scanner) throws Exception {
        Path path = Path.of("tasks.txt");
        List<String> list = Files.readAllLines(path);

        System.out.println("Введите  id задачи:");
        int id = Integer.parseInt(scanner.nextLine());

        if (taskManager.removeTaskById(id)) {
            list.remove(id);
            System.out.println("Задача удалена");
            saveTasksToFile(taskManager);
            return;
        }
        if (taskManager.removeEpicById(id)) {
            list.remove(id);
            System.out.println("Задача удалена");
            saveTasksToFile(taskManager);
            return;
        }
        if (taskManager.removeSubtaskById(id)) {
            list.remove(id);
            System.out.println("Задача удалена");
            saveTasksToFile(taskManager);
            return;
        } else {
            System.out.println("Id не найден");
        }
    }

    public static void updateTaskStatus(TaskManager taskManager, Scanner scanner) throws Exception {
        System.out.println("Введите id:");
        int id = Integer.parseInt(scanner.nextLine());
        Epic epic = taskManager.getEpicById(id);
        if (epic != null) {
            System.out.println("Нельзя обновть статус эпика");
        } else {
            // проверка на эпик если ищем эпик то должен вывести нельзя обновить статус эпика
            Task task = taskManager.getTaskById(id);
            if (task != null) {
                System.out.println("Введите новый статус (NEW, IN_PROGRESS, DONE):");
                Tasktatus status = Tasktatus.valueOf(scanner.nextLine());
                task.setStatus(status);
                saveTasksToFile(taskManager);
                return;
                // проверка на эти
            }
        }
        Subtask subtask = taskManager.getSubtaskById(id);
        if (subtask != null) {
            System.out.println("Введите новый статус (NEW, IN_PROGRESS, DONE):");
            Tasktatus status = Tasktatus.valueOf(scanner.nextLine());
            subtask.setStatus(status);
            subtask.getEpic().refreshStatus();
            return;
        }
        System.out.println("Задачи с таким идентификатором не найдена");
    }

    public static void removeAllTasks(TaskManager taskManager) throws Exception {
        taskManager.removeAllTasks();
        saveTasksToFile(taskManager);
    }

    public static void saveTasksToFile(TaskManager taskManager) throws Exception {
        FileWriter writer = new FileWriter("tasks.txt");
        writer.write("id,type,title,description,status,epicId\n");
        for (Task task : taskManager.getTasks()) {
            writer.write(task.getId() + ",TASK," + task.getTitle() + "," + task.getDescription() + "," + task.getStatus() + "\n");
        }
        for (Epic epic : taskManager.getEpic()) {
            writer.write(epic.getId() + ",EPIC," + epic.getTitle() + "," + epic.getDescription() + "," + epic.getStatus() + "\n");
        }
        for (Subtask subtask : taskManager.getSubtask()) {
            writer.write(subtask.getId() + ",SUBTASK," + subtask.getTitle() + "," + subtask.getDescription() + "," + subtask.getStatus() + "," + subtask.getEpic().getId() + "\n");
        }
        writer.close();
    }

    public static void loadFromFile(TaskManager taskManager) throws Exception {
        Path path = Path.of("tasks.txt");
        List<String> list = Files.readAllLines(path);
        list.remove(0);
        int maxId = 0;
        for (String s : list) {
            String[] split = s.split(",");
            int id = Integer.parseInt(split[0]);
            if (id > maxId) {
                maxId = id;
            }
            String type = split[1];
            String title = split[2];
            String description = split[3];
            Tasktatus status = Tasktatus.valueOf(split[4]);

            if (type.equals("TASK")) {
                Task task1 = new Task(id, title, type, description, status);
                taskManager.getTasks().add(task1);
            } else if (type.equals("EPIC")) {
                Epic epic = new Epic(id, title, type, description, status);
                taskManager.getEpic().add(epic);
            } else if (type.equals("SUBTASK")) {
                int epicId = Integer.parseInt(split[5]);
                Epic epic = taskManager.getEpicById(epicId);
                Subtask subtask = new Subtask(id, title, type, description, status, epic);
                taskManager.getSubtask().add(subtask);
                epic.getSubtasks().add(subtask);
            }
        }
        taskManager.setNextId(maxId + 1);
    }
}