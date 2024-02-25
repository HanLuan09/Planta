package vn.edu.ptit.planta.ui.home.myplant.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchMyPlantViewModel extends ViewModel {

    private MutableLiveData<String> textSearch;

    public MutableLiveData<String> getTextSearch() {
        if(textSearch == null) textSearch = new MutableLiveData<>();
        return textSearch;
    }
}
