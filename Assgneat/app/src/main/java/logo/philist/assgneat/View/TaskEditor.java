package logo.philist.assgneat.View;

import logo.philist.assgneat.Data.Task;

public interface TaskEditor {
    void editTask(Task task);
    void updateCheck(Task task);
    void deleteTask(Task task);
    void deleteAllTasks();
}
