package ru.mirea.khudyakovma.lesson4;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ru.mirea.khudyakovma.lesson4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonPlay.setOnClickListener(v ->
                Toast.makeText(this, "Воспроизведение", Toast.LENGTH_SHORT).show());

        binding.buttonStop.setOnClickListener(v ->
                Toast.makeText(this, "Остановка", Toast.LENGTH_SHORT).show());
    }
}
