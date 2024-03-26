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

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.FragmentCareBinding;
import vn.edu.ptit.planta.model.myschedule.MySchedule;
import vn.edu.ptit.planta.model.care.CareCalendar;
import vn.edu.ptit.planta.model.care.CareCalendarSchedule;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.care.adapter.CareAdapter;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.care.adapter.CareCalendarAdapter;
import vn.edu.ptit.planta.ui.schedule.notification.AddNotificationActivity;

public class CareFragment extends Fragment implements CareNavigator {

    private FragmentCareBinding binding;
    private CareViewModel viewModel;
    private CareAdapter careAdapter;
    private CareCalendarAdapter careCalendarAdapter;
    private RecyclerView recyclerViewCare, rcvSchedule;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_care, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(CareViewModel.class);
        binding.setCareViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.setCareNavigator(this);

        initRecyclerViewCare();
        initRecyclerViewSchedule();

        Bundle bundle = getArguments();
        if (bundle != null) {
            int id = bundle.getInt("id_myplant");
        }

        return binding.getRoot();
    }

    private void initRecyclerViewSchedule() {
        rcvSchedule = binding.idRcvSchedule;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvSchedule.setLayoutManager(linearLayoutManager);
        careCalendarAdapter = new CareCalendarAdapter(getList());
        rcvSchedule.setAdapter(careCalendarAdapter);


    }

    private List<CareCalendar> getList() {
        List<CareCalendarSchedule> list = new ArrayList<>();
        List<CareCalendar> careCalendars = new ArrayList<>();
        careCalendars.add(new CareCalendar("10/10/2024", list));
        careCalendars.add(new CareCalendar("10/11/2024", list));
        careCalendars.add(new CareCalendar("20/01/2024", list));
        careCalendars.add(new CareCalendar("10/02/2024", list));
        careCalendars.add(new CareCalendar("10/06/2024", list));
        careCalendars.add(new CareCalendar("10/08/2024", list));
        careCalendars.add(new CareCalendar("10/09/2024", list));
        careCalendars.add(new CareCalendar("10/06/2024", list));
        careCalendars.add(new CareCalendar("10/01/2024", list));
        return careCalendars;
    }

    private void initRecyclerViewCare() {
        recyclerViewCare = binding.idRcvCare;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCare.setLayoutManager(linearLayoutManager);

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

    @Override
    public void handleEditNotification(MySchedule schedule) {
        Intent intent = new Intent(requireContext(), AddNotificationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("schedule_care", schedule);
        bundle.putInt("care_id", 1);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void handleAddNotification() {
        Intent intent = new Intent(requireContext(), AddNotificationActivity.class);
        startActivity(intent);
    }
}