package vn.edu.ptit.planta.ui.note;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.ui.MainActivity;

public class DialogNote extends DialogFragment {

    private static final int REQUEST_IMAGE_CAPTURE = 141;
    ImageView plantImageView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_note, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);

        final EditText edDes, edDate, edFlower, edFruit, edHeight;
        edDes = dialogView.findViewById(R.id.edDes);
        edDate = dialogView.findViewById(R.id.edDate);
        edFlower = dialogView.findViewById(R.id.edFlower);
        edFruit = dialogView.findViewById(R.id.edFruit);
        edHeight = dialogView.findViewById(R.id.edHeight);

        builder.setTitle("Add Note");
        builder.setIcon(R.drawable.update);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng nhấn nút Add
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng nhấn nút Cancel
            }
        });

        AlertDialog dialog = builder.create();

        // Improved variable name for clarity
        plantImageView = dialogView.findViewById(R.id.surfaceView);
        FloatingActionButton captureButton = dialogView.findViewById(R.id.btnCapture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{android.Manifest.permission.CAMERA}, 1);
                    return;
                }
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        return dialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            plantImageView.setImageBitmap(photo);
        }
    }
}
