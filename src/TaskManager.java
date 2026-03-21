import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> tasks = new ArrayList<>();

    private int nextId = 1;

    public void createTask(Task task) {
        task.setId(nextId);
        nextId++;
        tasks.add(task);
    }
    public ArrayList<Task> getTasks() {
        return tasks;
    }
    public Task getTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }
    public boolean removeTaskById(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                return true;
            }
        }
        return false;
    }


     public ArrayList<Task> getTasksByStatus(String status) {
        ArrayList<Task> list = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus().equals(status)) {
                list.add(task);
            }
        }
        return list;
    }
}