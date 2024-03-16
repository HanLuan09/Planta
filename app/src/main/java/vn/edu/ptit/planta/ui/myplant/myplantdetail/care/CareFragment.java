package vn.edu.ptit.planta.ui.myplant.myplantdetail.care;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.FragmentCareBinding;
import vn.edu.ptit.planta.model.Schedule;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.care.adapter.CareAdapter;
import vn.edu.ptit.planta.ui.schedule.notification.AddNotificationActivity;

public class CareFragment extends Fragment implements CareNavigator {

    private FragmentCareBinding binding;
    private CareViewModel viewModel;
    private CareAdapter careAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_care, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(CareViewModel.class);
        binding.setCareViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.setCareNavigator(this);

        initRecyclerView();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        recyclerView = binding.idRcvCare;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        viewModel.getListSchedules().observe(requireActivity(), new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> schedules) {
                if (careAdapter == null) {
                    // Nếu adapter chưa được tạo
                    careAdapter = new CareAdapter((schedules));

                    careAdapter.setCareNavigator(CareFragment.this);
                    recyclerView.setAdapter(careAdapter);
                } else {
                   careAdapter.updateData(schedules);
                }
            }
        });

    }

    @Override
    public void handleEditNotification(Schedule schedule) {
        Intent intent = new Intent(requireContext(), AddNotificationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("schedule_care", schedule);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}