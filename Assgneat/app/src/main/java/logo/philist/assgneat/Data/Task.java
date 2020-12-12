package logo.philist.assgneat.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name ;
    private String description;
    private boolean check;

    public Task(String name, String description, boolean check) {
        this.name = name;
        this.description = description;
        this.check = check;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCheck(boolean check){
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCheck() {
        return check;
    }
}
