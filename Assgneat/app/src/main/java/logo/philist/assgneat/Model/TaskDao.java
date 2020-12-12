package logo.philist.assgneat.Model;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import logo.philist.assgneat.Data.Task;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Query("UPDATE task_table SET `check`= :check WHERE id = :id")
    void updateCheck(int id, boolean check);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Query("SELECT * FROM task_table ORDER BY id DESC")
    LiveData<List<Task>> getTasksByOrder();
}
