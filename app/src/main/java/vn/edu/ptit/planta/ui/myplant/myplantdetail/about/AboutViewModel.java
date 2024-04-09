package vn.edu.ptit.planta.ui.myplant.myplantdetail.about;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.myplant.MyPlant;

public class AboutViewModel extends ViewModel {
    private MutableLiveData<MyPlant> myPlantData;
    private MutableLiveData<Boolean> isData;
    private MyPlant myPlant;
    private int idUser;
    private int idMyPlant;

    public AboutViewModel() {
        myPlantData = new MutableLiveData<>();
    }
    public void setIdUserAndMyPlant(int idUser, int idMyPlant){
        this.idUser = idUser;
        this.idMyPlant = idMyPlant;
        initData();
    }
    public MutableLiveData<Boolean> getIsData() {
        if(isData == null) isData = new MutableLiveData<>();
        return isData;
    }
    private void initData() {
        RetrofitClient.getMyPlantService().getMyPlant(idUser,idMyPlant).enqueue(new Callback<ApiResponse<MyPlant>>() {
            @Override
            public void onResponse(Call<ApiResponse<MyPlant>> call, Response<ApiResponse<MyPlant>> response) {
                if(response.isSuccessful()){
                    ApiResponse<MyPlant> apiResponse = response.body();
                    if (apiResponse.isSuccess()){
                        myPlant = apiResponse.getResult();
                        myPlantData.setValue(myPlant);
                        if(myPlant == null){
                            isData.setValue(false);
                        }
                        else {
                            isData.setValue(true);
                        }
                    }
                    else{
                        isData.setValue(false);
                    }
                }
                else{
                    isData.setValue(false);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<MyPlant>> call, Throwable throwable) {
                isData.setValue(false);
            }
        });
    }
    public MutableLiveData<MyPlant> getMyPlant(){
        return myPlantData;
    }
}
