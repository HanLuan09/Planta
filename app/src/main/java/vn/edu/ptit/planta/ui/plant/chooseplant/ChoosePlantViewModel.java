package vn.edu.ptit.planta.ui.plant.chooseplant;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.model.Plant;

public class ChoosePlantViewModel extends ViewModel {

    private ChoosePlantNavigator choosePlantNavigator;
    private MutableLiveData<List<Plant>> listPlant;
    private List<Plant> plants;

    public ChoosePlantViewModel() {
        listPlant = new MutableLiveData<>();
        initData();
    }

    private void initData() {
        plants = new ArrayList<>();
        plants.add(new Plant(1, "Hoa hướng dương", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
        plants.add(new Plant(2, "Cây chuối", "https://i.ex-cdn.com/mientay.giadinhonline.vn/files/content/2023/06/28/cu-cai-da-u-muoi-1057.jpeg"));
        plants.add(new Plant(3, "Hoa hướng dương", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
        plants.add(new Plant(4, "Hoa hướng dương", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
        plants.add(new Plant(5, "Hoa hướng dương", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
        plants.add(new Plant(6, "Cây ngô", "https://tuyensinhso.vn/images/files/tuyensinhso.com/nganh-khoa-hoc-cay-trong.jpg"));

        listPlant.setValue(plants);
    }

    public MutableLiveData<List<Plant>> getListPlant() {
        return listPlant;
    }
}
