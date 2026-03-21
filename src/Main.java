import java.util.ArrayList;


  public  class Main {
    public  static  void  main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task = new Task("Исправить кнопку", " коде  кнопка удаление не роботает");
        taskManager.createTask(task);

        System.out.println(task.getId());
        System.out.println(taskManager.getTasks());

        Task foundTask = taskManager.getTaskById(1);
        System.out.println(foundTask.getTitle());

        Task foundTask2 = taskManager.getTaskById(99);
        System.out.println(foundTask2);

        taskManager.removeTaskById(1);
        System.out.println(taskManager.getTasks().size());



        taskManager.createTask(new Task("Ознакомиться с полиморфизмом", "..."));
        taskManager.createTask(new Task("Создать портфолио", "..."));
        taskManager.createTask(new Task("Проверить тз", "..."));


        Task foundTask3 = taskManager.getTaskById(3);
        foundTask3.setStatus("IN_PROGRESS");

        Task foundTask4 = taskManager.getTaskById(4);
        foundTask4.setStatus("DONE");

//        ArrayList<Task> newTasks = taskManager.getNewTasks();
//        System.out.println(newTasks.size()); // 1
//        System.out.println(newTasks.get(0).getTitle()); // Ознакомиться с полиморфизмом
//
//        ArrayList<Task> inProgressTasks = taskManager.getInProgressTasks();
//        System.out.println(inProgressTasks.size()); // 1
//        System.out.println(inProgressTasks.get(0).getTitle()); // Создать портфолио
//
//        ArrayList<Task> doneTasks = taskManager.getDoneTasks();
//        System.out.println(doneTasks.size()); // 1
//        System.out.println(doneTasks.get(0).getTitle()); // Проверить тз
    }
  }
