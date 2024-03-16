package vn.edu.ptit.planta.ui.myplant.myplantdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivityMyPlantDetailBinding;

public class MyPlantDetailActivity extends AppCompatActivity {

    private final List<String> tabTitles = Arrays.asList("Care", "Notes", "Chart", "About");
    private ActivityMyPlantDetailBinding binding;
    private MyPlantDetailPagerAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyPlantDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initMyDetail(binding.tabLayout, binding.detalViewPager2);

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.colorGreenText));

        // Thiết lập sự kiện nhấn vào biểu tượng "Back"
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

    }

    private void initMyDetail(TabLayout tabLayout, @NonNull ViewPager2 viewPager2) {
        adapter = new MyPlantDetailPagerAdapter(this);
        viewPager2.setUserInputEnabled(false);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) ->
                tab.setText(tabTitles.get(position))
        ).attach();

        for (int i = 0; i < tabTitles.size(); i++) {
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_title, null);
            textView.setText(tabTitles.get(i));
            tabLayout.getTabAt(i).setCustomView(textView);
        }
    }

}