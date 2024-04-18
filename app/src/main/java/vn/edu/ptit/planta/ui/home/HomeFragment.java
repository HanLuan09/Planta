package vn.edu.ptit.planta.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.FragmentHomeBinding;
import vn.edu.ptit.planta.ui.calendarmyplant.CalendarMyPlantActivity;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.MyPlantDetailActivity;
import vn.edu.ptit.planta.ui.plant.chooseplant.ChoosePlantActivity;

public class HomeFragment extends Fragment implements HomeNavigator {

    private final List<String> tabTitles = Arrays.asList("To day", "My plants");

    private HomePagerAdapter homePagerAdapter;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        binding.setHomeViewModel(homeViewModel);
        binding.setLifecycleOwner(this);

        // Thiết lập interface trong ViewModel
        homeViewModel.setHomeNavigator(this);

        initMyGarden(binding.tabLayout, binding.viewPager2);

        return binding.getRoot();
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void handleMyPlantFragment() {
        Intent intent = new Intent(requireContext(), MyPlantDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void handleCalendarMyPlant() {
        Intent intent = new Intent(requireContext(), CalendarMyPlantActivity.class);
        startActivity(intent);
    }

    @Override
    public void handleAddMyGarden() {
        Intent intent = new Intent(requireContext(), ChoosePlantActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(0, 0);
    }

    private void initMyGarden(TabLayout tabLayout, @NonNull ViewPager2 viewPager2) {
        homePagerAdapter = new HomePagerAdapter(requireActivity());
        viewPager2.setAdapter(homePagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) ->
                tab.setText(tabTitles.get(position))
        ).attach();

        for (int i = 0; i < tabTitles.size(); i++) {
            TextView textView = (TextView) LayoutInflater.from(requireContext()).inflate(R.layout.tab_title, null);
            textView.setText(tabTitles.get(i));
            tabLayout.getTabAt(i).setCustomView(textView);
        }
    }
}
