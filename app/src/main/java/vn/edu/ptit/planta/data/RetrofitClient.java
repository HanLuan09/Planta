package vn.edu.ptit.planta.data;

import retrofit2.Retrofit;
import vn.edu.ptit.planta.data.service.PlantService;

public class RetrofitClient {
//    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.example.com/";

//    public static Retrofit getRetrofitInstance() {
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return retrofit;
//    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build();

    PlantService service = retrofit.create(PlantService.class);
}

