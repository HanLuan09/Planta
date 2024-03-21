package vn.edu.ptit.planta.ui.home.today;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.FragmentTodayBinding;
import vn.edu.ptit.planta.model.care.CareSchedule;
import vn.edu.ptit.planta.model.care.CareScheduleCategory;
import vn.edu.ptit.planta.ui.plant.PlantViewModel;
import vn.edu.ptit.planta.ui.schedule.adapter.CareScheduleCategoryAdapter;


public class TodayFragment extends Fragment {

    private FragmentTodayBinding binding;
    private TodayViewModel viewModel;
    private RecyclerView recyclerView;
    private CareScheduleCategoryAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(TodayViewModel.class);
        binding.setTodayViewModel(viewModel);
        binding.setLifecycleOwner(this);

        recyclerView = binding.rcvToday;

        adapter = new CareScheduleCategoryAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.setListCareScheduleCategorys(getListCareScheduleCategorys());
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(adapter != null) adapter.release();
    }

    @NonNull
    private List<CareScheduleCategory> getListCareScheduleCategorys() {
        List<CareScheduleCategory> careScheduleCategories = new ArrayList<>();
        List<CareSchedule> careSchedules = new ArrayList<>();
        careSchedules.add(new CareSchedule(1, "Hoa hướng dương", "9:00"));
        careSchedules.add(new CareSchedule(2, "Cây ngô", "8:00"));
        careSchedules.add(new CareSchedule(3, "Hoa cẩm tú cầu", "18:00"));

        careScheduleCategories.add(new CareScheduleCategory("Tưới cây", careSchedules));

        careSchedules = new ArrayList<>();
        careSchedules.add(new CareSchedule(1, "Hoa hồng", "9:00"));
        careSchedules.add(new CareSchedule(2, "Cây vạn niên thanh", "16:00"));
        careScheduleCategories.add(new CareScheduleCategory("Bón phân", careSchedules));

        careSchedules = new ArrayList<>();
        careSchedules.add(new CareSchedule(1, "Hoa hướng dương", "9:00"));
        careScheduleCategories.add(new CareScheduleCategory("Thu hoạch", careSchedules));


        careSchedules = new ArrayList<>();
        careSchedules.add(new CareSchedule(1, "Hoa hồng", "9:00"));
        careSchedules.add(new CareSchedule(2, "Hoa cẩm tú cầu hoa tú cầu Hoa cẩm tú cầu hoa tú cầu", "8:00"));
        careSchedules.add(new CareSchedule(3, "Hoa cẩm tú cầu hoa tú cầu", "18:00"));
        careScheduleCategories.add(new CareScheduleCategory("Cắt tỉa cây", careSchedules));
        return careScheduleCategories;
    }
}