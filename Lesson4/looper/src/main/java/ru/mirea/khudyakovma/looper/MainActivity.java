package ru.mirea.khudyakovma.looper;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import ru.mirea.khudyakovma.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MyLooper myLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String result = msg.getData().getString("result");
                binding.textView.setText(result);
            }
        };

        myLooper = new MyLooper(mainHandler);
        myLooper.start();

        binding.button.setOnClickListener(view -> {
            String ageStr = binding.editTextAge.getText().toString();
            String work = binding.editTextJob.getText().toString();

            if (!ageStr.isEmpty() && !work.isEmpty() && myLooper.mHandler != null) {
                int age = Integer.parseInt(ageStr);
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putInt("AGE", age);
                bundle.putString("WORK", work);
                msg.setData(bundle);
                myLooper.mHandler.sendMessage(msg);
            }
        });
    }
}
