package ru.mirea.khudyakovma.internalfilestorage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "mirea.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etHistory = findViewById(R.id.etHistory);
        Button btnSave   = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String text = etHistory.getText().toString().trim();
            if (text.isEmpty()) {
                Toast.makeText(this, "Введите текст перед сохранением", Toast.LENGTH_SHORT).show();
                return;
            }

            try (FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
                fos.write(text.getBytes());
                Toast.makeText(this, "Сохранено в " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(this, "Ошибка при сохранении: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
