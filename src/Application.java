import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        while (true) {
            System.out.println("1. Создать задачу");
            System.out.println("2. Вывести список всех задач");
            System.out.println("3. Найти задачу по id");
            System.out.println("4. Найти задачи по статусу");
            System.out.println("5. Удалить задачу по id");
            System.out.println("6. Обновить статус задачи");
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
            }

            if (operation == 2) {
                ArrayList<Task> tasks = taskManager.getTasks();
                if (tasks.isEmpty()) {
                    System.out.println("Список пуст");
                } else {
                    for (Task task : tasks) {
                        System.out.println(task.getId() + " " + task.getTitle() + " " + task.getStatus());
                    }
                }

            } else if (operation == 3) {
                System.out.println("Введите  id задачи:");
                int id = Integer.parseInt(scanner.nextLine());

                Task task = taskManager.getTaskById(id);
                if (task == null) {
                    System.out.println("Id не найден");
                } else {
                    System.out.println(id + " " + task.getTitle() + " " + task.getStatus());
                }
            } else if (operation == 4) {
                System.out.println("Введите статус задачи:");
                String status = scanner.nextLine();

                for (Task task : taskManager.getTasksByStatus(status)) {
                    System.out.println(task.getId() + " " + task.getTitle() + " " + status);
                }
            } else if (operation == 5) {
                System.out.println("Введите  id задачи:");
                int id = Integer.parseInt(scanner.nextLine());

                if (taskManager.removeTaskById(id)) {
                    System.out.println("Задача удаленна");
                } else {
                    System.out.println("Id не найден");
                }
            } else if (operation == 6) {
                System.out.println("Введите id:");
                int id = Integer.parseInt(scanner.nextLine());

                Task task = taskManager.getTaskById(id);
                if (task == null) {
                    System.out.println("Id не найден");
                    break;
                }
                System.out.println("Введите новый статус (NEW, IN_PROGRESS, DONE):");
                String status = scanner.nextLine();

                if (status.equals("NEW") || status.equals("IN_PROGRESS") || status.equals("DONE")) {
                    System.out.println("Статус задачи обновлен.");
                    task.setStatus(status);
                } else {
                    System.out.println("Такого статуса нету");
                }
            }
        }
    }
}













