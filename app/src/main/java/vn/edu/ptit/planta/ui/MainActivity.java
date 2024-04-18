package vn.edu.ptit.planta.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.ui.tip.TipFragment;
import vn.edu.ptit.planta.ui.guesthome.GuestHomeActivity;
import vn.edu.ptit.planta.ui.home.HomeFragment;
import vn.edu.ptit.planta.ui.plant.PlantFragment;
import vn.edu.ptit.planta.ui.user.UserFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("idUser",0);
        if(idUser != 0) {

            bottomNavigationView = findViewById(R.id.bottom_nav);

            replaceFragment(new HomeFragment());

            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.id_main_home) {
                        replaceFragment(new HomeFragment());
                        return true;
                    } else if (item.getItemId() == R.id.id_main_plant) {
                        replaceFragment(new PlantFragment());
                        return true;
                    } else if (item.getItemId() == R.id.id_main_utilities) {
                        replaceFragment(new TipFragment());
                        return true;
                    } else if (item.getItemId() == R.id.id_main_user) {
                        replaceFragment(new UserFragment());
                        return true;
                    }
                    return false;
                }
            });
        }else {
            Intent intent = new Intent(this,GuestHomeActivity.class);
            startActivity(intent);
            this.overridePendingTransition(0, 0);
            finishAffinity();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}