package vn.edu.ptit.planta.ui.myplant.myplantdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivityMyPlantDetailBinding;
import vn.edu.ptit.planta.ui.myplant.editmyplant.EditMyPlantActivity;

public class MyPlantDetailActivity extends AppCompatActivity {

    private final List<String> tabTitles = Arrays.asList("Care", "Notes", "Chart", "About");
    private ActivityMyPlantDetailBinding binding;
    private MyPlantDetailPagerAdapter adapter;
    private Toolbar toolbar;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyPlantDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initMyDetail(binding.tabLayout, binding.detalViewPager2);
        initBundle();

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.colorGreenText));

        // Thiết lập sự kiện nhấn vào biểu tượng "Back"
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        // sự kiện trở về mặc định
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

    private void initBundle() {
        bundle = getIntent().getExtras();
        if(bundle == null) return;
        int id = bundle.getInt("id_myplant");
        String name = bundle.getString("name_myplant");
        String image = bundle.getString("image_myplant");

        binding.collapsingToolbar.setTitle(name);

        Glide.with(this)
                .load(image)
                .override(400,400)
                .into(binding.ivMyPlantDetail);
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

        // Lấy Bundle từ Activity và truyền vào Fragment
        adapter.setBundle(getIntent().getExtras());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_plant_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            Intent intent = new Intent(this, EditMyPlantActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}