import java.io.FileWriter;
import java.util.ArrayList;

public class Task {
    private int id;
    private String title;
    private String description;
    private Tasktatus status;

    public Task(String newTitle, String newDescription)  {
        status = Tasktatus.NEW;
        title = newTitle;
        description = newDescription;
    }

    public Task(int newId,String newTitle, String newDescription,  Tasktatus newStatus)  {
        status = newStatus;
        title = newTitle;
        description = newDescription;
        id = newId;
    }


    public int getId() {
        return id;
    }

    public void setId(int newId) {
        id = newId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String newDescription) {
        description = newDescription;
    }

    public Tasktatus getStatus() {
        return status;
    }

    public void setStatus(Tasktatus newStatus) {
        status = newStatus;
    }
}

