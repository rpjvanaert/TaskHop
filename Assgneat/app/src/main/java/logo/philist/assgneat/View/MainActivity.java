package logo.philist.assgneat.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import logo.philist.assgneat.Data.Task;
import logo.philist.assgneat.R;
import logo.philist.assgneat.TaskViewModel;

public class MainActivity extends AppCompatActivity implements CallbackTask {

    public static final int ADD_TASK_REQUEST = 1;

    private TaskViewModel taskViewModel;

    private ExtendedFloatingActionButton fabAddTask;
    private ExtendedFloatingActionButton fabDeleteAllTasks;
    private ExtendedFloatingActionButton fabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        fabAddTask = findViewById(R.id.FAB_add);
        fabAddTask.shrink();
        fabDeleteAllTasks = findViewById(R.id.FAB_delete_all);
        fabDeleteAllTasks.shrink();
        fabMenu = findViewById(R.id.FAB_menu);
        fabMenu.shrink();
        fabAddTask.setOnClickListener(view -> {
            if (fabAddTask.isExtended()){
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST);
                closeFabMenu();
            } else {
                fabAddTask.extend();
            }

        });
        fabDeleteAllTasks.setOnClickListener(view -> {
            if (fabDeleteAllTasks.isExtended()){
                taskViewModel.deleteAllTasks();
                closeFabMenu();
            } else {
                fabDeleteAllTasks.extend();
            }
        });
        fabMenu.setOnClickListener(view -> {
            if (fabMenu.isExtended()){
                fabMenu.shrink();
                closeFabMenu();
            } else {
                fabMenu.extend();
                openFabMenu();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TaskAdapter adapter = new TaskAdapter(this);
        recyclerView.setAdapter(adapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getTasks().observe(this, adapter);
//            @Override
//            public void onChanged(List<Task> tasks) {
//                adapter.setTasks(tasks);
//                adapter.notifyDataSetChanged();
////                String stringTask = "Task: ";
////                for (Task task : tasks) {
////                    Log.i(MainActivity.class.getName(), stringTask + task.getId() + task.isCheck() + " --with name: " + task.getName());
////                }
//            }
//        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                taskViewModel.delete(adapter.getTaskAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this,
                        "Task deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void openFabMenu() {
        fabAddTask.animate().translationY(-getResources().getDimension(R.dimen.standard_add_task));
        fabDeleteAllTasks.animate().translationY(-getResources().getDimension(R.dimen.standard_delete_all_task));
    }

    private void closeFabMenu() {
        fabAddTask.animate().translationY(0);
        fabDeleteAllTasks.animate().translationY(0);
        fabMenu.shrink();
        fabAddTask.shrink();
        fabDeleteAllTasks.shrink();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK){
            String name = data.getStringExtra(AddTaskActivity.TAG_NAME);
            String description = data.getStringExtra(AddTaskActivity.TAG_DESCRIPTION);

            Task task = new Task(name, description, false);
            taskViewModel.insert(task);

            Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void editTask(Task task) {
        //@TODO edit task
    }

    @Override
    public void updateCheck(Task task) {
        taskViewModel.updateCheck(task);
        Log.i(MainActivity.class.getName(), "Updating task " + task.getId() + task.getName() + " to check --- " + task.isCheck());
    }
}