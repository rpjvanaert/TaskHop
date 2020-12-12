package logo.philist.assgneat.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import logo.philist.assgneat.R;

public class AddTaskActivity extends AppCompatActivity {

    public static final String TAG_NAME =
            "logo.philist.assgneat.View.AddTaskActivity.TAG_NAME";
    public static final String TAG_DESCRIPTION =
            "logo.philist.assgneat.View.AddTaskActivity.TAG_DESCRIPTION";

    private TextInputEditText editTextName;
    private TextInputEditText editTextDescription;

    private ExtendedFloatingActionButton fabConfirm;
    private ExtendedFloatingActionButton fabCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextName = findViewById(R.id.edit_text_task_name);
        editTextDescription = findViewById(R.id.edit_text_task_description);
        fabConfirm = findViewById(R.id.FAB_confirm_add);
        fabCancel = findViewById(R.id.FAB_cancel_add);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabConfirm.shrink();
        fabConfirm.setOnClickListener(view -> {
            if (fabConfirm.isExtended()){
                saveNote();
            } else {
                fabConfirm.extend();
            }
        });

        fabCancel.shrink();
        fabCancel.setOnClickListener(view -> {
            if (fabCancel.isExtended()){
                setResult(RESULT_CANCELED);
                finish();
            } else {
                fabCancel.extend();
            }
        });
    }

    private void saveNote(){
        Log.d(AddTaskActivity.class.getName(), "Trying to save note...");
        String name = editTextName.getText().toString();
        String description = editTextDescription.getText().toString();
        if (name.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please insert name and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(TAG_NAME, name);
        data.putExtra(TAG_DESCRIPTION, description);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (fabCancel.isExtended()){
            setResult(RESULT_CANCELED);
            finish();
        } else {
            fabCancel.extend();
        }
    }
}