package logo.philist.assgneat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import logo.philist.assgneat.Data.Task;
import logo.philist.assgneat.Model.TaskRepository;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository repos;
    private LiveData<List<Task>> tasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);

        repos = new TaskRepository(application);

        tasks = repos.getTasks();
    }

    public void insert(Task task){
        repos.insert(task);
    }

    public void update(Task task){
        repos.update(task);
    }

    public void updateCheck(Task task){
        repos.updateCheck(task);
    }

    public void delete(Task task){
        repos.delete(task);
    }

    public void deleteAllTasks(){
        repos.deleteAllTasks();
    }

    public LiveData<List<Task>> getTasks(){
        return tasks;
    }
}
