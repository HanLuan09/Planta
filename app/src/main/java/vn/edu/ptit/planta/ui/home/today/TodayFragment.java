package vn.edu.ptit.planta.ui.home.today;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.FragmentTodayBinding;
import vn.edu.ptit.planta.model.care.CareScheduleResponse;
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
        adapter.setActivityResultLauncher(mActivityResultLauncher); //
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        setAdapterSchedules();

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(adapter != null) adapter.release();
    }

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){
                viewModel.initData();
                setAdapterSchedules();
            }
        }
    });

    private void setAdapterSchedules() {
        viewModel.getListCareSchedules().observe(requireActivity(), new Observer<List<CareScheduleResponse>>() {
            @Override
            public void onChanged(List<CareScheduleResponse> careScheduleResponses) {
                adapter.setListCareScheduleCategorys(careScheduleResponses);
                adapter.setSelectDate(dateToday());
                recyclerView.setAdapter(adapter);
            }
        });
    }
    @NonNull
    private String dateToday() {
        Calendar calendar = Calendar.getInstance();
        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d",
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        return selectedDate;
    }
}