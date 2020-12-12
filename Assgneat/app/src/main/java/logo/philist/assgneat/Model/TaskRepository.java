package logo.philist.assgneat.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import logo.philist.assgneat.Data.Task;

public class TaskRepository {

    private final TaskDao taskDao;

    private final LiveData<List<Task>> taskList;

    private final ThreadFactory threadFactory;

    public TaskRepository(Application application){
        TaskDatabase database = TaskDatabase.getInstance(application);

        taskDao = database.taskDao();

        taskList = taskDao.getTasksByOrder();

        this.threadFactory = Executors.defaultThreadFactory();
    }

    public void insert(Task task){
        threadFactory.newThread(new InsertTaskRunnable(taskDao, task)).start();
    }

    public void update(Task task){
        threadFactory.newThread(new UpdateTaskRunnable(taskDao, task)).start();
    }

    public void updateCheck(Task task){
        threadFactory.newThread(new UpdateCheckRunnable(taskDao, task)).start();
    }

    public void delete(Task task){
        threadFactory.newThread(new DeleteTaskRunnable(taskDao, task)).start();
    }

    public void deleteAllTasks(){
        threadFactory.newThread(new DeleteAllTasksRunnable(taskDao)).start();
    }

    public LiveData<List<Task>> getTasks(){
        return taskList;
    }

    private static class InsertTaskRunnable implements Runnable{

        private TaskDao taskDao;
        private Task task;

        private InsertTaskRunnable(TaskDao taskDao, Task task){
            this.taskDao = taskDao;
            this.task = task;
        }

        @Override
        public void run() {
            taskDao.insert(task);
        }
    }

    private static class UpdateCheckRunnable implements  Runnable{

        private TaskDao taskDao;
        private Task task;

        private  UpdateCheckRunnable(TaskDao taskDao, Task task){
            this.taskDao = taskDao;
            this.task = task;
        }

        @Override
        public void run() {
            taskDao.updateCheck(task.getId(), task.isCheck());
        }
    }

    private static class UpdateTaskRunnable implements Runnable{

        private TaskDao taskDao;
        private Task task;

        private UpdateTaskRunnable(TaskDao taskDao, Task task){
            this.taskDao = taskDao;
            this.task = task;
        }

        @Override
        public void run() {
            taskDao.update(task);
        }
    }

    private static class DeleteTaskRunnable implements Runnable {

        private TaskDao taskDao;
        private Task task;

        private DeleteTaskRunnable(TaskDao taskDao, Task task){
            this.taskDao = taskDao;
            this.task = task;
        }

        @Override
        public void run() {
            taskDao.delete(task);
        }
    }

    private static class DeleteAllTasksRunnable implements Runnable{

        private TaskDao taskDao;

        private DeleteAllTasksRunnable(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        public void run() {
            taskDao.deleteAll();
        }
    }
}