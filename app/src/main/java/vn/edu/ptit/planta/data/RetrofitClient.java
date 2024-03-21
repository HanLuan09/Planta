package vn.edu.ptit.planta.data;

import retrofit2.Retrofit;
import vn.edu.ptit.planta.data.service.PlantService;
import vn.edu.ptit.planta.data.service.UserService;

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

    UserService userService = retrofit.create(UserService.class);
    PlantService plantService = retrofit.create(PlantService.class);
}

