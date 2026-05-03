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
        Path path = Path.of("tasks.txt");
        List<String> list = Files.readAllLines(path);
        list.remove(0);
        int Id = 0;
        String title = "";
        String description1 = "";
        Tasktatus Status;
        for (String s : list) {
            String[] split = s.split(",");
            String id = split[0];
            String title1 = split[1];
            String description = split[2];
            String status = split[3];
            if (id.equals("id")) {
                Id = Integer.parseInt(id);
            } else if (title1.equals("title")) {
                title = title1;
            } else if (description.equals("description")) {
                description1 = description;
            } else if (status.equals("status")) {
                Status = Tasktatus.NEW;
            }
            Task task1 = new Task(Id, title, description1, Tasktatus.NEW);
            taskManager.getTasks().add(task1);
        }
        while (true) {
//                System.out.println("Загруженые фа/йлы: " + list);
            System.out.println("1. Создать задачу");
            System.out.println("2. Создать эпик");
            System.out.println("3. Создать подзадачу");
            System.out.println("4. Вывести список всех задач");
            System.out.println("5. Найти задачу по id");
            System.out.println("6. Найти задачи по статусу");
            System.out.println("7. Удалить задачу по id");
            System.out.println("8. Обновить статус задачи");
            System.out.println("0. Выход");
            System.out.println("Выберите действие:");
            int operation = Integer.parseInt(scanner.nextLine());
            if (operation == 1) {
                System.out.println(list);
                System.out.println("Введите название задачи:");
                String name = scanner.nextLine();
                System.out.println("Введите описание задачи:");
                String description = scanner.nextLine();
                Task task = new Task(name, description);
                taskManager.createTask(task);

                System.out.println("Задача создана: " + task.getId() + " " + task.getTitle() + " " + task.getStatus());
            } else if (operation == 2) {

                System.out.println("Введите эпик задачу:");
                String epic = scanner.nextLine();

                System.out.println("Введите описание эпик:");
                String description = scanner.nextLine();
                Epic epic1 = new Epic(epic, description);
                taskManager.createEpic(epic1);

                System.out.println("Задача создана: " + epic1.getId() + " " + epic1.getTitle() + " " + epic1.getStatus());

            } else if (operation == 3) {
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
            } else if (operation == 4) {
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

            } else if (operation == 5) {
                System.out.println("Введите id задачи:");
                int id = Integer.parseInt(scanner.nextLine());

                Task task = taskManager.getTaskById(id);
                if (task != null) {
                    System.out.println(task.getId() + " " + task.getTitle() + " " + task.getStatus());
                    continue;
                }
                Epic epic = taskManager.getEpicById(id);
                if (epic != null) {
                    System.out.println(epic.getId() + " " + epic.getTitle() + " " + epic.getStatus());
                    continue;
                }
                Subtask subtask = taskManager.getSubtaskById(id);
                if (subtask != null) {
                    System.out.println(subtask.getId() + " " + subtask.getTitle() + " " + subtask.getStatus());
                    continue;
                } else {
                    System.out.println("Id не найден");
                }
            } else if (operation == 6) {
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
            } else if (operation == 7) {
                System.out.println("Введите  id задачи:");
                int id = Integer.parseInt(scanner.nextLine());

                if (taskManager.removeTaskById(id)) {
                    System.out.println("Задача удалена");
                    continue;
                }
                if (taskManager.removeEpicById(id)) {
                    System.out.println("Задача удалена");
                    continue;
                }
                if (taskManager.removeSubtaskById(id)) {
                    System.out.println("Задача удалена");
                    continue;
                } else {
                    System.out.println("Id не найден");
                }
            } else if (operation == 8) {
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
                        continue;
                        // проверка на эти
                    }
                }
                Subtask subtask = taskManager.getSubtaskById(id);
                if (subtask != null) {
                    System.out.println("Введите новый статус (NEW, IN_PROGRESS, DONE):");
                    Tasktatus status = Tasktatus.valueOf(scanner.nextLine());
                    subtask.setStatus(status);
                    subtask.getEpic().refreshStatus();
                    continue;
                }
                System.out.println("Задачи с таким идентификатором не найдена");

            } else if (operation == 0) {
                FileWriter writer = new FileWriter("tasks.txt");
                writer.write("id" + "," + "title" + "," + "description" + "," + "status,epicId" + "\n");
                for (Task task : taskManager.getTasks()) {
                    writer.write(task.getId() + "," + "TASK" + "," + task.getTitle() + "," + task.getDescription() + "," + task.getStatus() + "\n");
                }
                for (Epic epic : taskManager.getEpic()) {
                    writer.write(epic.getId() + "," + "EPIC" + "," + epic.getTitle() + "," + epic.getDescription() + "," + epic.getStatus() + "\n");
                }
                for (Subtask subtask : taskManager.getSubtask()) {
                    writer.write(subtask.getId() + ",SUBTASK," + subtask.getTitle() + "," + subtask.getDescription() + "," + subtask.getStatus() + "," + subtask.getEpic().getId() + "\n");
                }
                writer.close();
                break;
            }
        }
    }
}


//  } else if (operation == 4) {
//                ArrayList<Task> tasks = taskManager.getTasks();
//                ArrayList<Epic> epic = taskManager.getEpic();
//                ArrayList<Subtask> subtasks = taskManager.getSubtask();
//                if (tasks.isEmpty() && epic.isEmpty() && subtasks.isEmpty()) {
//                    System.out.println("Список пуст");
//                } else {
//                    for (Task task : tasks) {
//                        System.out.println(task.getId() + " [TASK] " + " " + task.getTitle() + " " + task.getStatus());
//                    }
//                    for (Epic epic1 : epic) {
//                        System.out.println(epic1.getId() + " [EPIC] " + " " + epic1.getTitle() + " " + epic1.getStatus());
//                        if (epic1.getSubtasks().contains(subtasks)) {
//                            for (Subtask subtask : subtasks) {
//
//
//                                System.out.println("Введите новый статус (NEW, IN_PROGRESS, DONE):");
//                String status1 = scanner.nextLine();

//if (!status1.equals("NEW") && !status1.equals("IN_PROGRESS") && !status1.equals("DONE")) {
//     System.out.println("Некоректный статус");


//                                System.out.println(subtask.getId() + " [SUBTASK] " + " " + subtask.getTitle() + " " + subtask.getStatus());