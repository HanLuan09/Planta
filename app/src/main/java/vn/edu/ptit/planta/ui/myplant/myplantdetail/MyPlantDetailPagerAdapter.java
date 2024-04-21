package vn.edu.ptit.planta.ui.myplant.myplantdetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.ptit.planta.ui.myplant.myplantdetail.about.AboutFragment;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.care.CareFragment;
import vn.edu.ptit.planta.ui.note.ChartFragment;
import vn.edu.ptit.planta.ui.note.NoteFragment;

public class MyPlantDetailPagerAdapter extends FragmentStateAdapter {

    private Bundle bundle; //

    public MyPlantDetailPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Tạo và trả về Fragment tại vị trí position
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new CareFragment();
                break;
            case 1:
                fragment = new NoteFragment();
                break;
            case 2:
                fragment = new ChartFragment();
                break;
            case 3:
                fragment = new AboutFragment();
                break;
            default:
                fragment = new CareFragment();
                break;
        }
        // Truyền Bundle vào Fragment nếu có
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    // Phương thức để thiết lập Bundle
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
