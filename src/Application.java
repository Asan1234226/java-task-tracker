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
            System.out.println("1. Создать задачу");
            System.out.println("2. Создать эпик");
            System.out.println("3. Создать подзадачу");
            System.out.println("4. Вывести список всех задач");
            System.out.println("5. Найти задачу по id");
            System.out.println("6. Найти задачи по статусу");
            System.out.println("7. Удалить задачу по id");
            System.out.println("8. Обновить статус задачи");
            System.out.println("9. Удалить все задачи");
            System.out.println("0. Выход");
            System.out.println("Выберите действие:");
            int operation = Integer.parseInt(scanner.nextLine());
            if (operation == 1) {
              createTask(taskManager,scanner);
            } else if (operation == 2) {
                createEpic(taskManager, scanner);
            } else if (operation == 3) {
                createSubtask(taskManager, scanner);
            } else if (operation == 4) {
                displayAllTasks(taskManager, scanner);
            } else if (operation == 5) {
                findTaskById(taskManager, scanner);
            } else if (operation == 6) {
                findTaskByStatus(taskManager, scanner);
            } else if (operation == 7) {
                removeTaskById(taskManager, scanner);
            } else if (operation == 8) {
                updateTaskStatus(taskManager, scanner);
            } else if (operation == 9) {
                removeAllTasks(taskManager);
            } else if (operation == 0) {
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
                break;
            }
        }
    }
    public  static  void  createTask(TaskManager taskManager, Scanner scanner) {
            System.out.println("Введите название задачи:");
            String name = scanner.nextLine();
            System.out.println("Введите описание задачи:");
            String description = scanner.nextLine();
            Task task = new Task(name, description);
            taskManager.createTask(task);

            System.out.println("Задача создана: " + task.getId() + " " + task.getTitle() + " " + task.getStatus());
    }
    public  static  void  createEpic(TaskManager taskManager, Scanner scanner) {
        System.out.println("Введите эпик задачу:");
        String epic = scanner.nextLine();

        System.out.println("Введите описание эпик:");
        String description = scanner.nextLine();
        Epic epic1 = new Epic(epic, description);
        taskManager.createEpic(epic1);
        System.out.println("Задача создана: " + epic1.getId() + " " + epic1.getTitle() + " " + epic1.getStatus());
    }
    public  static  void  createSubtask(TaskManager taskManager, Scanner scanner) {
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
            System.out.println(subtask.getId() + " " + subtask.getTitle() + " " + subtask.getStatus() + " (" + "epicId=" + subtask.getId() + ")");
        }
    }
    public  static  void  displayAllTasks(TaskManager taskManager, Scanner scanner) {
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
    public  static  void  findTaskById(TaskManager taskManager, Scanner scanner) {
        System.out.println("Введите id задачи:");
        int id = Integer.parseInt(scanner.nextLine());

        Task task = taskManager.getTaskById(id);
        if (task != null) {
            System.out.println(task.getId() + " " + task.getTitle() + " " + task.getStatus());
            return;
        }
        Epic epic = taskManager.getEpicById(id);
        if (epic != null) {
            System.out.println(epic.getId() + " " + epic.getTitle() + " " + epic.getStatus());
            return;
        }
        Subtask subtask = taskManager.getSubtaskById(id);
        if (subtask != null) {
            System.out.println(subtask.getId() + " " + subtask.getTitle() + " " + subtask.getStatus());
            return;
        } else {
            System.out.println("Id не найден");
        }
    }
    public  static  void  findTaskByStatus(TaskManager taskManager, Scanner scanner) {
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
    public  static  void  removeTaskById(TaskManager taskManager, Scanner scanner) {
        System.out.println("Введите  id задачи:");
        int id = Integer.parseInt(scanner.nextLine());

        if (taskManager.removeTaskById(id)) {
            System.out.println("Задача удалена");
            return;
        }
        if (taskManager.removeEpicById(id)) {
            System.out.println("Задача удалена");
            return;
        }
        if (taskManager.removeSubtaskById(id)) {
            System.out.println("Задача удалена");
            return;
        } else {
            System.out.println("Id не найден");
        }
    }
    public  static  void  updateTaskStatus(TaskManager taskManager, Scanner scanner) {
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
    public  static  void  removeAllTasks(TaskManager taskManager) {
        taskManager.getRemoveAllTasks();
        taskManager.getRemoveAllEpic();
        taskManager.getRemoveAllSubtask();
    }
    public static void loadFromFile(TaskManager taskManager) throws Exception {
        Path path = Path.of("tasks.txt");
        List<String> list = Files.readAllLines(path);
        list.remove(0);
        for (String s : list) {
            String[] split = s.split(",");
            int id = Integer.parseInt(split[0]);
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
    }
}