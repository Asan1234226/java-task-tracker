import java.util.ArrayList;

public class Subtask extends Task {
    private Epic epic;

    Subtask(String newTitle, String newDescription, Epic newEpic) {
        super(newTitle, newDescription);
        epic = newEpic;
    }

    Subtask(int newId, String newTitle,String newType, String newDescription, Tasktatus newStatus, Epic newEpic) {
        super(newId, newTitle, newType, newDescription, newStatus);
        epic = newEpic;
    }

    public Epic getEpic() {
        return epic;
    }
}