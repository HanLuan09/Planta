package vn.edu.ptit.planta.ui.user;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.databinding.FragmentUserBinding;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.UserResponse;
import vn.edu.ptit.planta.ui.guesthome.GuestHomeActivity;
import vn.edu.ptit.planta.ui.user.profile.ProfileActivity;
import vn.edu.ptit.planta.utils.PendingIntentUtils;


public class UserFragment extends Fragment implements UserNavigator {

    private FragmentUserBinding binding;
    private UserViewModel viewModel;
    private TextView tvFullnameUser, tvPhoneUser;
    private RelativeLayout btn_profile;
    private UserResponse userResponse;
    private int idUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.setUserViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.setUserNavigator(this);
        View view = binding.getRoot();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", 0);

        tvFullnameUser = view.findViewById(R.id.tv_fullName_user);
        tvPhoneUser = view.findViewById(R.id.tv_phone_user);

        getUser();

        btn_profile = view.findViewById(R.id.btn_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getUser() {
        RetrofitClient.getUserService().getUser(idUser).enqueue(new Callback<ApiResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserResponse>> call, Response<ApiResponse<UserResponse>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<UserResponse> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        userResponse = apiResponse.getResult();
                        initData();
                    } else {
                        String message = apiResponse.getMessage();
                        responseFalse(message);
                    }
                } else {
                    String message = "Phản hồi không hợp lệ";
                    responseInvalid(message);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserResponse>> call, Throwable throwable) {
                String message = " Kết nối tới server thất bại!";
                connectFail(message);
            }
        });
    }

    private void initData() {
        if (userResponse != null) {
            if (userResponse.getName() == null) {
                tvFullnameUser.setText("Người dùng " + idUser);
            } else {
                tvFullnameUser.setText(userResponse.getName());
            }
            if (userResponse.getPhone() == null) {
                tvPhoneUser.setText("Chưa có sđt");
            } else {
                tvPhoneUser.setText(userResponse.getPhone());
            }
        }
    }

    private void responseFalse(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void responseInvalid(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void connectFail(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void handleLogout() {
        final Dialog dialog = new Dialog(requireContext());
        openDialog(dialog, "Thông báo", "Bạn có chắc chắn muốn thoát phiên đăng nhập?");
        dialog.show();
    }

    @Override
    public void handle() {
        Toast.makeText(requireContext(), "Tính năng chưa phát triển", Toast.LENGTH_SHORT).show();
    }

    private void openDialog(@NonNull Dialog dialog, String name, String message) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_dialog);
        Window window = dialog.getWindow();
        if(window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(false);
        TextView tvCancel = dialog.findViewById(R.id.dialog_cancel);
        TextView tvOk = dialog.findViewById(R.id.dialog_sure);
        TextView tvName = dialog.findViewById(R.id.dialog_text_name);
        TextView tvMessage = dialog.findViewById(R.id.dialog_text_message);
        tvMessage.setText(message);
        tvName.setText(name);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("idUser");
                editor.apply();

                //cancelAllAlarms();

                Intent intent = new Intent(requireContext(), GuestHomeActivity.class);
                startActivity(intent);
                requireActivity().finishAffinity();
                dialog.dismiss();
            }
        });
    }

    private void cancelAllAlarms() {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        List<PendingIntent> pendingIntentList = PendingIntentUtils.getPendingIntentList(requireContext());
        if (alarmManager != null && pendingIntentList != null) {
            for (PendingIntent pendingIntent : pendingIntentList) {
                alarmManager.cancel(pendingIntent); // Hủy bỏ PendingIntent
            }
            pendingIntentList.clear();
        }
    }


}

