package vn.edu.ptit.planta.ui.plant.plantdetail;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlantDetailViewModel extends ViewModel {
    private MutableLiveData<Boolean> isExpanded;


    public MutableLiveData<Boolean> getIsExpanded() {
        if(isExpanded == null) {
            isExpanded = new MutableLiveData<>();
            isExpanded.setValue(false);
        }
        return isExpanded;
    }

    // Phương thức xử lý sự kiện khi ImageView được nhấn
    public void onExpandIconClicked() {
        isExpanded.setValue(!isExpanded.getValue());
        Log.e("Test", isExpanded.getValue().toString());
    }
}

