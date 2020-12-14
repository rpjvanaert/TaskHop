package logo.philist.assgneat.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import logo.philist.assgneat.R;

public class AddEditTaskActivity extends AppCompatActivity {

    public static final String EXTRA_NAME =
            "logo.philist.assgneat.View.AddTaskActivity.EXTRA_NAME";
    public static final String EXTRA_DESCRIPTION =
            "logo.philist.assgneat.View.AddTaskActivity.EXTRA_DESCRIPTION";
    public static final String EXTRA_ID =
            "logo.philist.assgneat.View.AddTaskActivity.EXTRA_ID";
    public static final String EXTRA_CHECK =
            "logo.philist.assgneat.View.AddTaskActivity.EXTRA_CHECK";

    private MaterialToolbar toolbar;

    private TextInputEditText editTextName;
    private TextInputEditText editTextDescription;

    private ExtendedFloatingActionButton fabConfirm;
    private ExtendedFloatingActionButton fabCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        editTextName = findViewById(R.id.edit_text_task_name);
        editTextDescription = findViewById(R.id.edit_text_task_description);
        fabConfirm = findViewById(R.id.FAB_confirm_add);
        fabCancel = findViewById(R.id.FAB_cancel_add);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
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

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            toolbar.setTitle(R.string.edit_task);
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));

        } else {
            toolbar.setTitle(R.string.add_task);
        }
    }

    private void saveNote(){
        Log.d(AddEditTaskActivity.class.getName(), "Trying to save note...");
        String name = editTextName.getText().toString();
        String description = editTextDescription.getText().toString();

        if (name.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please insert name and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_DESCRIPTION, description);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
            data.putExtra(EXTRA_CHECK, getIntent().getBooleanExtra(EXTRA_CHECK, false));
        }

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