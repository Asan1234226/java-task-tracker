import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        while (true) {
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
                    System.out.println(id + " " + epic.getTitle() + " " + epic.getStatus() + " (" + "epicId=" + id + ")");
                }
            } else if (operation == 4) {
                ArrayList<Task> tasks = taskManager.getTasks();
                ArrayList<Epic> epic = taskManager.getEpic();
                ArrayList<Subtask> subtasks = taskManager.getSubtask();
                if (tasks.isEmpty() && epic.isEmpty() && subtasks.isEmpty()) {
                    System.out.println("Список пуст");
                } else {
                    for (Task task : tasks) {
                        System.out.println(task.getId() + " [TASK] " + " " + task.getTitle() + " " + task.getStatus());
                    }
                    for (Epic epic1 : epic) {
                        System.out.println(epic1.getId() + " [EPIC] " + " " + epic1.getTitle() + " " + epic1.getStatus());
                    }
                    for (Subtask subtask : subtasks) {
                        System.out.println(subtask.getId() + " [SUBTASK] " + " " + subtask.getTitle() + " " + subtask.getStatus());
                    }
                }

            } else if (operation == 5) {
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
            } else if (operation == 6) {
                System.out.println("Введите статус задачи:");
                String status = scanner.nextLine();
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

            } else if (operation == 8) {
                System.out.println("Введите id:");
                int id = Integer.parseInt(scanner.nextLine());

                Task task = taskManager.getTaskById(id);
                if (task == null) {
                    System.out.println("Id не найден");
                    continue;
                }
                System.out.println("Введите новый статус (NEW, IN_PROGRESS, DONE):");
                String status = scanner.nextLine();

                if (!status.equals("NEW") && !status.equals("IN_PROGRESS") && !status.equals("DONE")) {
                    System.out.println("Некоректный статус");

                } else if (task.getStatus().equals("NEW") && status.equals("NEW")) {
                    System.out.println("Нельзя менять NEW на NEW");
                } else if (task.getStatus().equals("IN_PROGRESS") && status.equals("NEW")) {
                    System.out.println("Нельзя менять IN_PROGRESS на NEW");
                } else if (task.getStatus().equals("DONE") && status.equals("NEW")) {
                    System.out.println("Нельзя менять DONE на NEW");
                } else {
                    System.out.println("Статус обновлен ");
                    task.setStatus(status);
                }
            } else if (operation == 0) {
                break;
            }
        }
    }
}


//  }
//    } else if (operation == 7) {
//            System.out.println("Введите  id задачи:");
//            int id = Integer.parseInt(scanner.nextLine());
//
//            if (taskManager.removeTaskById(id)) {
//                System.out.println("Задача удаленна");
//            }
//            System.out.println("Введите  id epic задачи:");
//            int id1 = Integer.parseInt(scanner.nextLine());
//
//            if (taskManager.removeEpicById(id1)) {
//                System.out.println("Задача удаленна");
//            }
//            System.out.println("Введите  id подзадачи:");
//            int id3 = Integer.parseInt(scanner.nextLine());
//
//            if (taskManager.removeSubtaskById(id3)) {
//                System.out.println("Задача удаленна");
//            } else {
//                System.out.println("Id не найден");
//            }






