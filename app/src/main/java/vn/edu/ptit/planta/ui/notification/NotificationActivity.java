package vn.edu.ptit.planta.ui.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.edu.ptit.planta.R;

public class NotificationActivity extends AppCompatActivity {
    static final String API_KEY = "e7e6c3e89c7af080adf321a9b4fa88c0";


    ImageView imgWeatherIcon ;
    TextView txtTemper;
    TextView txtTemperState;

    TextView txtHumidity;
    TextView txtWind;
    TextView txtAdviceTemper;
    TextView txtAdviceHumi;
    ImageView btnBlack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initdata();
        handleBlack();
        getJsonWeather("hanoi");
    }

    private void handleBlack() {
        btnBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public  void getJsonWeather(String city){
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+API_KEY+"&units=metric";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject weatherObj = weatherArray.getJSONObject(0);
                            String icon = weatherObj.getString("icon");

                            String urlIcon = "https://openweathermap.org/img/wn/"+icon+".png";

                            Picasso.get().load(urlIcon).into(imgWeatherIcon);
//
                            String temperState = weatherObj.getString("main");
                            txtTemperState.setText(temperState);
                            JSONObject main = response.getJSONObject("main");
                            String temp = main.getString("temp");
                            int itemp = main.getInt("temp");
//                            Toast.makeText(MainActivity.this, " nhiet do" + itemp, Toast.LENGTH_SHORT).show();
                            txtTemper.setText(temp+"°C");
                            String humidity = main.getString("humidity");
                            int ihumidity = main.getInt("humidity");
//                            Toast.makeText(MainActivity.this, "do am"+ihumidity, Toast.LENGTH_SHORT).show();
                            txtHumidity.setText("Độ ẩm:" +humidity+"%");
                            JSONObject wind = response.getJSONObject("wind");
                            String speed = wind.getString("speed");
                            txtWind.setText("Gió : " +speed+"m/s");

//                            String sNgay = response.getString("dt");
//                            long lNgay = Long.parseLong(sNgay);
//                            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, yyyy-MM-dd HH:mm:ss");
//                            Date date = new Date(lNgay);
//                            String currentTime = dateFormat.format(date);
//                            txtCurentTemper.setText(currentTime);
                            if(itemp>30){
                                txtAdviceTemper.setText("Nhiệt độ cao: Cung cấp nước thường xuyên và tránh tưới vào giữa trưa.");
                            } else if (itemp>=10) {
                                txtAdviceTemper.setText("Nhiệt độ trung bình, tưới nước đầy đủ");

                            } else {
                                txtAdviceTemper.setText("Nhiệt độ thấp: Bảo vệ  và giữ ấm cho cây .");
                            }

                            if(ihumidity>70){
                                txtAdviceHumi.setText("Độ ẩm cao: Đảm bảo không gian trồng cây thông thoáng, sử dụng quạt và thiết bị thông gió");
                            } else if (ihumidity>=40) {
                                txtAdviceHumi.setText("Độ ẩm lí tươởng nên tưới nước vừa phải");

                            } else {
                                txtAdviceHumi.setText("Độ ẩm thấp: Cung cấp nước đều đặn và sử dụng phun sương hoặc lớp phủ để giữ ẩm.");
                            }


                            Log.d("testlog1","1234");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(NotificationActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("testlog2",response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NotificationActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    public void initdata(){
        imgWeatherIcon = findViewById(R.id.imgWeatherIcon);
        txtTemper = findViewById(R.id.txtTemper);
        txtTemperState = findViewById(R.id.txtTemperState);
        txtHumidity = findViewById(R.id.txtHumidity);
        txtWind = findViewById(R.id.txtWind);
        txtAdviceTemper = findViewById(R.id.txtAdviceTemp);
        txtAdviceHumi = findViewById(R.id.txtAdviceHumi);
        btnBlack = findViewById(R.id.id_btn_noti);

    }

}