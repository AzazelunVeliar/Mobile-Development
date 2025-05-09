package ru.mirea.khudyakovma.mireaproject.ui.gyroscope;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Locale;
import ru.mirea.khudyakovma.mireaproject.databinding.FragmentGyroscopeBinding;

public class Gyroscope extends Fragment implements SensorEventListener {
    private FragmentGyroscopeBinding binding;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentGyroscopeBinding.inflate(inflater, container, false);
        sensorManager = (SensorManager) requireActivity()
                .getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        binding.textX.setText(String.format(Locale.getDefault(),
                "X: %.2f", x));
        binding.textY.setText(String.format(Locale.getDefault(),
                "Y: %.2f", y));
        if (Math.abs(x) < 1f && Math.abs(y) < 1f) {
            binding.textLevel.setText("Уровень: Ровно");
        } else {
            binding.textLevel.setText("Уровень: Не ровно");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
