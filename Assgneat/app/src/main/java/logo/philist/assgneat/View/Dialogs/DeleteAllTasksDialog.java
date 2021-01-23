package logo.philist.assgneat.View.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.button.MaterialButton;

import logo.philist.assgneat.R;
import logo.philist.assgneat.View.TaskEditor;

public class DeleteAllTasksDialog extends AppCompatDialogFragment {

    private TaskEditor taskEditor;

    public DeleteAllTasksDialog(TaskEditor taskEditor) {
        this.taskEditor = taskEditor;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.deletion_dialog, null);
        TextView titleTV = view.findViewById(R.id.head_ask_delete_task);
        TextView textTV = view.findViewById(R.id.text_ask_delete_task);
        MaterialButton confirmButton = view.findViewById(R.id.confirm_delete_button);

        titleTV.setText(R.string.ask_delete_all_tasks);
        textTV.setText(R.string.warning_delete_all);
        confirmButton.setOnClickListener(v -> {
            taskEditor.deleteAllTasks();
            dismiss();
        });
        return builder.setView(view).create();
    }
}
