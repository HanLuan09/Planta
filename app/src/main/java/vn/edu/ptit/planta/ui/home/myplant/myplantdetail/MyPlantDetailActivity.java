package vn.edu.ptit.planta.ui.home.myplant.myplantdetail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

import vn.edu.ptit.planta.databinding.ActivityMyPlantDetailBinding;

import vn.edu.ptit.planta.R;

public class MyPlantDetailActivity extends AppCompatActivity {

    private final List<String> tabTitles = Arrays.asList("Care", "Notes", "Chart");
    private ActivityMyPlantDetailBinding binding;
    private MyPlantDeatilPagerAdapter adapter;
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

        // Thiết lập sự kiện nhấn vào biểu tượng "Back"
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        initCollapsingToolbarLayout();
    }

    private void initMyDetail(TabLayout tabLayout, @NonNull ViewPager2 viewPager2) {
        adapter = new MyPlantDeatilPagerAdapter(this);
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

    private void initCollapsingToolbarLayout() {

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

        AppBarLayout appBarLayout = binding.appBarLayout;
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0) {
                    // Collapsed
                    isShow = true;
                    updateToolbarColors(true);
                } else if (isShow) {
                    // Expanded
                    isShow = false;
                    updateToolbarColors(false);
                }
            }
        });
    }
    private void updateToolbarColors(boolean collapsed) {
        if (collapsed) {
            // Collapsed
            toolbar.getNavigationIcon().setTint(getResources().getColor(android.R.color.black));
        } else {
            // Expanded
            toolbar.getNavigationIcon().setTint(getResources().getColor(android.R.color.white));
        }
    }
}