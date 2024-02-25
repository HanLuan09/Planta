package vn.edu.ptit.planta.ui.home.myplant.myplantdetail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

import vn.edu.ptit.planta.databinding.ActivityMyPlantDetailBinding;

import vn.edu.ptit.planta.R;

public class MyPlantDetailActivity extends AppCompatActivity {

    private final List<String> tabTitles = Arrays.asList("Care", "Notes", "Note");

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
//        collapsingToolbarLayout.setTitleEnabled(false);
//        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorBackground));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.plant_img);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                int myColor = palette.getVibrantColor(getResources().getColor(R.color.colorBackground));
                int myDackColor = palette.getVibrantColor(getResources().getColor(R.color.black_trans));
                collapsingToolbarLayout.setContentScrimColor(myColor);
                collapsingToolbarLayout.setStatusBarScrimColor(myDackColor);
            }
        });
    }
}