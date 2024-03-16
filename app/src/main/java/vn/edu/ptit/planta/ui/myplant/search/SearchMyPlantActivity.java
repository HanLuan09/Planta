package vn.edu.ptit.planta.ui.myplant.search;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivitySearchMyPlantBinding;


public class SearchMyPlantActivity extends AppCompatActivity {

    private ActivitySearchMyPlantBinding binding;
    private SearchMyPlantViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =  DataBindingUtil.setContentView(this, R.layout.activity_search_my_plant);
        viewModel = new ViewModelProvider(this).get(SearchMyPlantViewModel.class);
        binding.setSearchMyPlantViewModel(viewModel);
        binding.setLifecycleOwner(this);

    }

}