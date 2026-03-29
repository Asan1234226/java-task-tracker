import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;

    Epic(String newTitle, String newDescription) {
        super(newTitle, newDescription);
        subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void refreshStatus() {
        // обновление статуса эпика, на основе статусов подзадач
        for (Subtask subtask : subtasks) {

        }
    }
}
