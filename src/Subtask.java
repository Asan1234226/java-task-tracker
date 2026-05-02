import java.util.ArrayList;

public class Subtask extends Task {
    private Epic epic;

    Subtask(String newTitle, String newDescription, Epic newEpic) throws  Exception {
        super(newTitle, newDescription);
        epic = newEpic;
    }

    public Epic getEpic() {
        return epic;
    }
}
