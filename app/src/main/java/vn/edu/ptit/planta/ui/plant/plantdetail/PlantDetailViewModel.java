package vn.edu.ptit.planta.ui.plant.plantdetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.PlantDetail;

public class PlantDetailViewModel extends ViewModel {

    private MutableLiveData<Integer> idPlant;
    private MutableLiveData<PlantDetail> mPlantDetail;

    public PlantDetailViewModel() {
        mPlantDetail = new MutableLiveData<>();
        idPlant = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getIdPlant() {
        return idPlant;
    }

    public void initData() {
        if (idPlant == null) return;
        RetrofitClient.getPlantService().plantDetail(idPlant.getValue()).enqueue(new Callback<ApiResponse<PlantDetail>>() {
            @Override
            public void onResponse(Call<ApiResponse<PlantDetail>> call, Response<ApiResponse<PlantDetail>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<PlantDetail> apiResponse = response.body();
                    PlantDetail plantDetail = apiResponse.getResult();
                    if (apiResponse.isSuccess() && plantDetail != null) {
                        mPlantDetail.setValue(plantDetail);
                    }
                } else {
                    // Xử lý khi có lỗi trả về từ API
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<PlantDetail>> call, Throwable throwable) {
                // Xử lý khi gặp lỗi kết nối hoặc lỗi khác
            }
        });
    }

    public MutableLiveData<PlantDetail> getPlantDetail() {
        return mPlantDetail;
    }
}
