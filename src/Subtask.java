import java.util.ArrayList;

public class Subtask extends Task {
    private Epic epic;

    Subtask(String newTitle, String newDescription, Epic newEpic) {
        super(newTitle, newDescription);
        epic = newEpic;
    }
      Subtask(int newId,String newTitle, String newDescription,  Tasktatus newStatus)  {
        super(newId,newTitle,newDescription,newStatus);
    }

    public Epic getEpic() {
        return epic;
    }
}
