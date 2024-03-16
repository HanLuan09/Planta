package vn.edu.ptit.planta.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.ptit.planta.ui.home.today.TodayFragment;
import vn.edu.ptit.planta.ui.myplant.MyPlantFragment;

public class HomePagerAdapter extends FragmentStateAdapter {

    public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Tạo và trả về Fragment tại vị trí position
        switch (position) {
            case 0:
                return new TodayFragment();
            case 1:
                return new MyPlantFragment();
            default:
                return new TodayFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
