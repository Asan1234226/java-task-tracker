import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;

    Epic(String newTitle, String newDescription){
        super(newTitle, newDescription);
        subtasks = new ArrayList<>();
    }
    Epic(int newId,String newTitle, String newDescription,  Tasktatus newStatus)  {
        super(newId,newTitle,newDescription,newStatus);
        subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void refreshStatus() {
        // обновление статуса эпика, на основе статусов подзадач
        // - если subtasks пустой — NEW
        // - если все сабтаски имеют статус NEW  — NEW
        // - если все сабтаски имеют статус DONE — DONE
        // - в противном случае - IN_PROGRESS
        boolean allNew = true;
        boolean allDone = true;

        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != Tasktatus.NEW) {
                allNew = false;
            }
            if (subtask.getStatus() != Tasktatus.DONE) {
                allDone = false;
            }
        }
        if (allNew == false && allDone == false) {
            setStatus(Tasktatus.IN_PROGRESS);
        } else if (allNew == true) {
            setStatus(Tasktatus.NEW);
        } else if (allDone == true) {
            setStatus(Tasktatus.DONE);
        }
    }
}

