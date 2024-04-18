package vn.edu.ptit.planta.ui.myplant.search;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.myplant.MyPlant;

public class SearchMyPlantViewModel extends ViewModel {

    private SearchMyPlantNavigator searchMyPlantNavigator;
    private MutableLiveData<List<MyPlant>> listMyPlants;
    private MutableLiveData<Boolean> isSearch = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isLoad;
    private MutableLiveData<Boolean> isData;
    private List<MyPlant> myPlants;
    private int idUserResponse;
//    private MutableLiveData<String> textSearch;
    private String textSearch;

    public SearchMyPlantViewModel() {
        this.listMyPlants = new MutableLiveData<>();
    }

    public void setSearchMyPlantNavigator(SearchMyPlantNavigator navigator) {
        this.searchMyPlantNavigator = navigator;
    }

    public void setIdUserResponse(int id){
        this.idUserResponse = id;
    }

    public MutableLiveData<Boolean> getIsData() {
        if(isData == null) isData = new MutableLiveData<>();
        return isData;
    }

    public MutableLiveData<Boolean> getIsSearch() {
        if(isSearch == null) isSearch = new MutableLiveData<>();
        return isSearch;
    }

    public MutableLiveData<Boolean> getIsLoad() {
        if(isLoad == null) isLoad = new MutableLiveData<>();
        return isLoad;
    }

//    public MutableLiveData<String> getTextSearch() {
//        if(textSearch == null) textSearch = new MutableLiveData<>();
//        return textSearch;
//    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
        initData();
    }

    public MutableLiveData<List<MyPlant>> getListMyPlants() {
        return listMyPlants;
    }

    private void initData() {
        myPlants = new ArrayList<>();
        isLoad.setValue(true);
        RetrofitClient.getMyPlantService().getAllMyPlantByKey(idUserResponse, textSearch).enqueue(new Callback<ApiResponse<List<MyPlant>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<MyPlant>>> call, Response<ApiResponse<List<MyPlant>>> response) {
                if(response.isSuccessful()){
                    ApiResponse<List<MyPlant>> apiResponse = response.body();
                    if (apiResponse.isSuccess()){
                        myPlants = apiResponse.getResult();
                        listMyPlants.setValue(myPlants);

                        if(myPlants.size() == 0){
                            isData.setValue(false);
                            isSearch.setValue(true);
                            isLoad.setValue(false);
                        }
                        else {
                            isData.setValue(true);
                            isSearch.setValue(true);
                            isLoad.setValue(false);
                        }
                    }
                    else{
                        isData.setValue(false);
                        isSearch.setValue(false);
                        isLoad.setValue(false);
                    }
                }
                else{
                    isData.setValue(false);
                    isSearch.setValue(false);
                    isLoad.setValue(false);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<MyPlant>>> call, Throwable throwable) {
                isData.setValue(false);
            }
        });
    }
    public void onBackClick() {
        Log.e("BACK", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        searchMyPlantNavigator.handleBack();
    }
}
