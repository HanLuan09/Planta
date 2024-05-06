package vn.edu.ptit.planta.ui.note;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;

public class UpdateNote extends DialogFragment {
    private static final int REQUEST_IMAGE_CAPTURE = 141;
    ImageView plantImageView;

    Bitmap capturedPhoto;

    ImageView captureButton;

    int myPlantId = 0;

    Bitmap bitmap;

    String imgBase64;

    String selectedDate;

    public void setOnNoteUpdateListener(View.OnClickListener onClickListener) {
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_note_update, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);

        final EditText edDes, edFlower, edFruit, edHeight;
        final TextView tvSelectedDate;
        final ImageView tvSelectedDate1;

        edDes = dialogView.findViewById(R.id.edDes);
        tvSelectedDate = dialogView.findViewById(R.id.tvSelectedDate);
        tvSelectedDate1 = dialogView.findViewById(R.id.tvSelectedDate1);
        edFlower = dialogView.findViewById(R.id.edFlower);
        edFruit = dialogView.findViewById(R.id.edFruit);
        edHeight = dialogView.findViewById(R.id.edHeight);
        plantImageView = dialogView.findViewById(R.id.surfaceView);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String description = bundle.getString("description");
            String date = bundle.getString("date");
            int flowers = bundle.getInt("flowers");
            int fruits = bundle.getInt("fruits");
            int height = bundle.getInt("height");
            imgBase64 = bundle.getString("img");
            int id = bundle.getInt("id");

            edDes.setText(description);
            tvSelectedDate.setText(date);
            edFlower.setText(String.valueOf(flowers));
            edFruit.setText(String.valueOf(fruits));
            edHeight.setText(String.valueOf(height));
            if (imgBase64 != null && !imgBase64.isEmpty()) {
                byte[] decodedString = Base64.decode(imgBase64, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                plantImageView.setImageBitmap(bitmap);
            }
        }

        tvSelectedDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày hiện tại để hiển thị mặc định trong DatePickerDialog
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Tạo một DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Xử lý khi người dùng chọn ngày
                        // Ở đây, bạn có thể cập nhật TextView để hiển thị ngày đã chọn
                        selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        tvSelectedDate.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

                // Hiển thị DatePickerDialog
                datePickerDialog.show();
            }
        });

        builder.setTitle("Update Note");
        builder.setIcon(R.drawable.update);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Không cần thực hiện bất kỳ hành động nào ở đây
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Đóng dialog khi người dùng nhấn nút Cancel
            }
        });

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String description = edDes.getText().toString();
                        String date = tvSelectedDate.getText().toString(); // Lấy ngày đã chọn từ TextView
                        String flowers = edFlower.getText().toString();
                        String fruits = edFruit.getText().toString();
                        String height = edHeight.getText().toString();

                        boolean isValid = true;

                        if (description.isEmpty()) {
                            Toast.makeText(getContext(), "Vui lòng nhập Description", Toast.LENGTH_SHORT).show();
                            isValid = false;
                        } else if (flowers.isEmpty()) {
                            Toast.makeText(getContext(), "Vui lòng nhập số hoa", Toast.LENGTH_SHORT).show();
                            isValid = false;
                        } else if (fruits.isEmpty()) {
                            Toast.makeText(getContext(), "Vui lòng nhập số quả", Toast.LENGTH_SHORT).show();
                            isValid = false;
                        } else if (height.isEmpty()) {
                            Toast.makeText(getContext(), "Vui lòng nhập chiều cao", Toast.LENGTH_SHORT).show();
                            isValid = false;
                        } else if (date.isEmpty()) {
                            Toast.makeText(getContext(), "Vui lòng chọn ngày", Toast.LENGTH_SHORT).show();
                            isValid = false;
                        }

                        if (isValid) {
                            // Nếu dữ liệu hợp lệ, thực hiện các hành động cần thiết
                            Note noteResponse = new Note();
                            noteResponse.setDescription(description);
                            noteResponse.setDate(date);
                            noteResponse.setFlowers(Integer.parseInt(flowers));
                            noteResponse.setFruits(Integer.parseInt(fruits));
                            noteResponse.setHeight(Integer.parseInt(height));


                            String encodedImage = null;
                            // Kiểm tra xem ảnh đã chụp có tồn tại không
                            if (capturedPhoto != null) {
                                // Mã hóa ảnh thành chuỗi Base64
                                encodedImage = encodeImage(capturedPhoto);
                                // Set ảnh vào ImageView
                                plantImageView.setImageBitmap(capturedPhoto);
                                // Lưu chuỗi Base64 vào đối tượng noteResponse
                                noteResponse.setImage(encodedImage);
                            }else{
                                noteResponse.setImage(imgBase64);
                            }


                            int noteId = getArguments().getInt("id");
                            RetrofitClient.getNoteService().updateNote(noteId, noteResponse).enqueue(new Callback<ApiResponse<Note>>() {
                                @Override
                                public void onResponse(retrofit2.Call<ApiResponse<Note>> call, Response<ApiResponse<Note>> response) {
                                    if (response.isSuccessful()) {

                                        // Gọi phương thức onNoteAdded() của NoteFragment để cập nhật danh sách ghi chú
                                        NoteFragment.getInstance().onNoteAdded();

                                        Toast.makeText(requireContext(), "Note updated successfully", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(requireContext(), "Failed to update note", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(retrofit2.Call<ApiResponse<Note>> call, Throwable throwable) {
                                    Toast.makeText(requireContext(), "Failed to update note", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });

        // Improved variable name for clarity

        captureButton = dialogView.findViewById(R.id.btnCapture);
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
            capturedPhoto = (Bitmap) data.getExtras().get("data");
            plantImageView.setImageBitmap(capturedPhoto);
        }
    }

    // Phương thức để mã hóa ảnh thành chuỗi Base64
    public String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // Nén ảnh vào ByteArrayOutputStream
        byte[] imageBytes = baos.toByteArray(); // Chuyển đổi ByteArrayOutputStream thành mảng byte
        return Base64.encodeToString(imageBytes, Base64.DEFAULT); // Mã hóa mảng byte thành chuỗi Base64
    }




}
