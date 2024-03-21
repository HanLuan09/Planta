package vn.edu.ptit.planta.ui.home.today;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vn.edu.ptit.planta.model.care.CareScheduleCategory;

public class TodayViewModel extends ViewModel {

    private MutableLiveData<List<CareScheduleCategory>> listScheduleCategorys;
    private List<CareScheduleCategory> scheduleCategorys;

    public TodayViewModel() {
        listScheduleCategorys = new MutableLiveData<>();
        initData();
    }

    public MutableLiveData<List<CareScheduleCategory>> getListScheduleCategorys() {
        return listScheduleCategorys;
    }

    private void initData() {
    }
}
