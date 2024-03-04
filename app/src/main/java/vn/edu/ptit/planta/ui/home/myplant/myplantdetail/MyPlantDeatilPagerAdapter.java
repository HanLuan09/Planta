package vn.edu.ptit.planta.ui.home.myplant.myplantdetail;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.ptit.planta.ui.home.myplant.myplantdetail.care.CareFragment;
import vn.edu.ptit.planta.ui.note.ChartFragment;
import vn.edu.ptit.planta.ui.note.NoteFragment;

public class MyPlantDeatilPagerAdapter extends FragmentStateAdapter {

    public MyPlantDeatilPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Tạo và trả về Fragment tại vị trí position
        switch (position) {
            case 0:
                return new CareFragment();
            case 1:
                return new NoteFragment();
            case 2:
                return new ChartFragment();
            default:
                return new CareFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
