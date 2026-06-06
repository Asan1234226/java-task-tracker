import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<Epic> epics = new ArrayList<>();
    private ArrayList<Subtask> subtasks = new ArrayList<>();
    private ArrayList<Task> history = new ArrayList<>();
    private static final String HISTORY_FILE_PATH = "history.txt";
    private static final String TASK_FILE_PATH = "task.txt";
    private int nextId = 1;


    // TODO
    // - createEpic, getEpicById, remove...
    // - createSubtask, getSubtaskById, remove...

    public void setNextId(int newId) {
        nextId = newId;
    }

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

    public ArrayList<Task> getHistory() {
        return history;
    }

    public void saveToHistory(Task task) {
        history.remove(task);
        history.addFirst(task);
    }

    public boolean removeTaskById(int id) throws Exception {
        Path path = Path.of("tasks.txt");
        List<String> list = Files.readAllLines(path);
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                list.remove(i);
                return true;
            }
        }
        return false;
    }


    public void removeAllTasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

//

    public void createSubtask(Subtask subtask) {
        subtask.setId(nextId);
        nextId++;
        subtasks.add(subtask);

        Epic epic = subtask.getEpic();
        epic.getSubtasks().add(subtask);
    }

    public ArrayList<Subtask> getSubtask() {
        return subtasks;
    }


    public Subtask getSubtaskById(int id) {
        for (Subtask subtask : subtasks) {
            if (subtask.getId() == id) {
                return subtask;
            }
        }
        return null;
    }

    public boolean removeSubtaskById(int id) {
        for (int i = 0; i < subtasks.size(); i++) {
            Subtask subtask = subtasks.get(i);
            if (subtasks.get(i).getId() == id) {
                subtasks.remove(i);
                Epic epic = subtask.getEpic();
                epic.getSubtasks().remove(subtask);
                epic.refreshStatus();
                return true;
            }
        }
        return false;
    }

    public ArrayList<Subtask> getSubtaskByStatus(Tasktatus status) {
        ArrayList<Subtask> list = new ArrayList<>();
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() == status) {
                list.add(subtask);
            }
        }
        return list;
    }


    // -----------------------------------------------------------------------------------------------------
    public void createEpic(Epic epic) {
        epic.setId(nextId);
        nextId++;
        epics.add(epic);
    }

    public ArrayList<Epic> getEpic() {
        return epics;
    }

    public Epic getEpicById(int id) {
        for (Epic epic : epics) {
            if (epic.getId() == id) {
                return epic;
            }
        }
        return null;
    }

    public boolean removeEpicById(int id) {
        for (int i = 0; i < epics.size(); i++) {
            Epic epic = epics.get(i);
            if (epics.get(i).getId() == id) {
                epics.remove(i);
                for (Subtask subtask : epic.getSubtasks()) {
                    subtasks.remove(subtask);
                }
                return true;
            }
        }
        return false;
    }

    public ArrayList<Epic> getEpicByStatus(Tasktatus status) {
        ArrayList<Epic> list = new ArrayList<>();
        for (Epic epic : epics) {
            if (epic.getStatus() == status) {
                list.add(epic);
            }
        }
        return list;
    }


    public ArrayList<Task> getTasksByStatus(Tasktatus status) {
        ArrayList<Task> list = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus() == status) {
                list.add(task);
            }
        }
        return list;
    }
}