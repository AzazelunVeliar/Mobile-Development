// app/src/main/java/ru/mirea/khudyakovma/mireaproject/ui/weather/WeatherFragment.java
package ru.mirea.khudyakovma.mireaproject.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Locale;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.khudyakovma.mireaproject.R;

public class WeatherFragment extends Fragment {

    private TextView tvLocation;
    private TextView tvTemperature;
    private TextView tvWindSpeed;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvLocation = view.findViewById(R.id.tvLocation);
        tvTemperature = view.findViewById(R.id.tvTemperature);
        tvWindSpeed = view.findViewById(R.id.tvWindSpeed);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.open-meteo.com/")
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenMeteoApi api = retrofit.create(OpenMeteoApi.class);
        double latitude = 55.7522;
        double longitude = 37.6156;
        String timezone = "Europe/Moscow";

        tvLocation.setText(String.format(Locale.getDefault(), "Локация: %.4f, %.4f", latitude, longitude));

        api.getCurrentWeather(latitude, longitude, timezone).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(
                    Call<WeatherResponse> call,
                    Response<WeatherResponse> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    double temp = response.body().current_weather.temperature;
                    double wind = response.body().current_weather.windspeed;
                    tvTemperature.setText(String.format(Locale.getDefault(), "Температура: %.1f°C", temp));
                    tvWindSpeed.setText(String.format(Locale.getDefault(), "Ветер: %.1f м/с", wind));
                } else {
                    tvTemperature.setText("Температура: ошибка");
                    tvWindSpeed.setText("Ветер: ошибка");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                tvTemperature.setText("Температура: ошибка");
                tvWindSpeed.setText("Ветер: ошибка");
            }
        });
    }
}
