package vn.edu.ptit.planta.ui.myplant.myplantdetail.care;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.FragmentCareBinding;
import vn.edu.ptit.planta.model.care.CareCalendar;
import vn.edu.ptit.planta.model.care.CareCalendarResponse;
import vn.edu.ptit.planta.model.myschedule.MySchedule;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.care.adapter.CareAdapter;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.care.adapter.CareCalendarAdapter;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.care.adapter.CareScheduleCalendarAdapter;
import vn.edu.ptit.planta.ui.schedule.notification.AddNotificationActivity;

public class CareFragment extends Fragment implements CareNavigator {

    private FragmentCareBinding binding;
    private CareViewModel viewModel;
    private CareAdapter careAdapter;
    private CareCalendarAdapter careCalendarAdapter;
    private CareScheduleCalendarAdapter careScheduleCalendarAdapter;
    private RecyclerView recyclerViewCare, rcvSchedule, rcvScheduleCalendar;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_care, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(CareViewModel.class);
        binding.setCareViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.setCareNavigator(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int myPlantId = bundle.getInt("id_myplant");
            viewModel.getIdMyPlant().setValue(myPlantId);
        }
        if(viewModel.getIdMyPlant()!= null && viewModel.getIdMyPlant().getValue()!= null) {
            viewModel.initDataSchedule();
//            viewModel.initDataCareCalendar();
        }

        initRecyclerViewCareScheduleCalendar();
        initRecyclerViewSchedule();
        initRecyclerViewCareCalendar();

        return binding.getRoot();
    }

    private void initRecyclerViewCareCalendar() {
        rcvSchedule = binding.idRcvSchedule;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvSchedule.setLayoutManager(linearLayoutManager);

        setAdapterCareCalendar();

    }

    private void initRecyclerViewSchedule() {
        recyclerViewCare = binding.idRcvCare;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCare.setLayoutManager(linearLayoutManager);
        setAdapterSchedules();
    }

    private void initRecyclerViewCareScheduleCalendar() {
        rcvScheduleCalendar = binding.rcvScheduleNote;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        rcvScheduleCalendar.setLayoutManager(linearLayoutManager);
        careScheduleCalendarAdapter = new CareScheduleCalendarAdapter();
    }

    private void setAdapterSchedules() {
        viewModel.getListSchedules().observe(requireActivity(), new Observer<List<MySchedule>>() {
            @Override
            public void onChanged(List<MySchedule> schedules) {
                if (careAdapter == null) {
                    // Nếu adapter chưa được tạo
                    careAdapter = new CareAdapter((schedules));
                    careAdapter.setCareNavigator(CareFragment.this);
                    recyclerViewCare.setAdapter(careAdapter);
                } else {
                    careAdapter.updateData(schedules);
                }

            }
        });
    }

    private void setAdapterCareCalendar() {
        viewModel.getListCareCalendars().observe(requireActivity(), new Observer<List<CareCalendarResponse>>() {
            @Override
            public void onChanged(List<CareCalendarResponse> careCalendarResponses) {

                careCalendarAdapter = new CareCalendarAdapter(careCalendarResponses);
                rcvSchedule.setAdapter(careCalendarAdapter);
                careCalendarAdapter.setOnItemClickListener(new CareCalendarAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(List<CareCalendar> schedules, String date) {
                        careScheduleCalendarAdapter.setListCareScheduleCalendars(schedules);
                        rcvScheduleCalendar.setAdapter(careScheduleCalendarAdapter);
                        binding.tvScheduleNote.setText(date);
                    }

                });
            }
        });
    }

    @Override
    public void handleEditNotification(MySchedule schedule) {
        Intent intent = new Intent(requireContext(), AddNotificationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("schedule_care", schedule);
        bundle.putInt("care_id", 1);
        bundle.putInt("care_my_plant_id", viewModel.getIdMyPlant().getValue());
        intent.putExtras(bundle);
        mActivityResultLauncher.launch(intent);
    }

    @Override
    public void handleAddNotification() {
        Intent intent = new Intent(requireContext(), AddNotificationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("care_my_plant_id", viewModel.getIdMyPlant().getValue());
        intent.putExtras(bundle);
        mActivityResultLauncher.launch(intent);
    }

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){
                viewModel.initDataSchedule();
                setAdapterSchedules();
                //viewModel.initDataCareCalendar();
                setAdapterCareCalendar();
            }
        }
    });
}