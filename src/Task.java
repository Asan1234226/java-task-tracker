import java.io.FileWriter;
import java.util.ArrayList;

public class Task {
    private int id;
    private String title;
    private String description;
    private Tasktatus status;

    public Task(String newTitle, String newDescription) throws Exception {
        status = Tasktatus.NEW;
        title = newTitle;
        description = newDescription;
        FileWriter writer = new FileWriter("tasks.txt");
        writer.write("id" + "," + "title" + "," + "description" + "," + "status" + "\n");
        for (int i = 0; i < 1; i++) {
            writer.write(i + 1 + "," + getTitle() + "," + getDescription() + "," + getStatus() + "\n");
        }
        writer.close();
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

