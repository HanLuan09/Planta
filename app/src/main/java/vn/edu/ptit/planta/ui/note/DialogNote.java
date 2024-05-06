package vn.edu.ptit.planta.ui.note;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telecom.Call;
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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.ui.MainActivity;

public class DialogNote extends DialogFragment {

    private static final int REQUEST_IMAGE_CAPTURE = 141;
    ImageView plantImageView;
    ImageView captureButton;

    Bitmap capturedPhoto;

    int myPlantId = 0;

    private OnNoteAddedListener onNoteAddedListener;

    public void setOnNoteAddedListener(OnNoteAddedListener listener) {
        this.onNoteAddedListener = listener;
    }

    private OnNoteUpdate onNoteUpdate;

    public void setOnNoteUpdateListener(OnNoteUpdate listener) {
        this.onNoteUpdate = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_note, null);

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
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        tvSelectedDate.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

                // Hiển thị DatePickerDialog
                datePickerDialog.show();
            }
        });

        builder.setTitle("Add Note");
        builder.setIcon(R.drawable.add);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
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
                        String date = tvSelectedDate.getText().toString();
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
                            NoteResponse noteResponse = new NoteResponse();
                            noteResponse.setDescription(description);
                            noteResponse.setDate(date);
                            noteResponse.setFlowers(Integer.parseInt(flowers));
                            noteResponse.setFruits(Integer.parseInt(fruits));
                            noteResponse.setHeight(Integer.parseInt(height));

                            Bundle bundle = getArguments();
                            if (bundle != null) {
                                myPlantId = bundle.getInt("id_myplant1");
                            }



                            String encodedImage = null;
                            // Kiểm tra xem ảnh đã chụp có tồn tại không
                            if (capturedPhoto != null) {
                                // Mã hóa ảnh thành chuỗi Base64
                                encodedImage = encodeImage(capturedPhoto);
                                // Set ảnh vào ImageView
                                plantImageView.setImageBitmap(capturedPhoto);
                                // Lưu chuỗi Base64 vào đối tượng noteResponse
                                noteResponse.setImage(encodedImage);
                            }

                            noteResponse.setIdmyplant(myPlantId);

                            // Gọi phương thức createNote trong NoteService
                            RetrofitClient.getNoteService().createNote(noteResponse).enqueue(new Callback<ApiResponse<NoteResponse>>() {
                                @Override
                                public void onResponse(retrofit2.Call<ApiResponse<NoteResponse>> call, Response<ApiResponse<NoteResponse>> response) {
                                    // Xử lý khi tạo ghi chú thành công

                                    if (onNoteAddedListener != null) {
                                        onNoteAddedListener.onNoteAdded();
                                    }

                                    ChartFragment.getInstance().OnUpdate();

                                    Toast.makeText(requireContext(), "Note added successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss(); // Đóng dialog sau khi thêm ghi chú thành công
                                }

                                @Override
                                public void onFailure(retrofit2.Call<ApiResponse<NoteResponse>> call, Throwable throwable) {
                                    // Xử lý khi có lỗi xảy ra khi gọi API
                                    Toast.makeText(requireContext(), "Failed to add note", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });

        // Improved variable name for clarity
        plantImageView = dialogView.findViewById(R.id.surfaceView);
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
