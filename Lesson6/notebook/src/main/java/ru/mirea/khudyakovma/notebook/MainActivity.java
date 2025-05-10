package ru.mirea.khudyakovma.notebook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.*;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_PERM = 101;
    private EditText etFilename, etQuote;
    private Button btnSave, btnLoad;
    private File docsDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFilename = findViewById(R.id.etFilename);
        etQuote    = findViewById(R.id.etQuote);
        btnSave    = findViewById(R.id.btnSave);
        btnLoad    = findViewById(R.id.btnLoad);

        docsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasStoragePermission()) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQ_PERM);
            }
        }

        btnSave.setOnClickListener(v -> saveToFile());
        btnLoad.setOnClickListener(v -> loadFromFile());
    }

    private boolean hasStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void saveToFile() {
        String name = etFilename.getText().toString().trim();
        String quote= etQuote.getText().toString().trim();
        if (name.isEmpty() || quote.isEmpty()) {
            Toast.makeText(this, "Введите и имя файла, и цитату", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(docsDir, name + ".txt");
        try (FileOutputStream fos = new FileOutputStream(file, false)) {
            fos.write(quote.getBytes("UTF-8"));
            Toast.makeText(this, "Сохранено в " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Ошибка записи: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void loadFromFile() {
        String name = etFilename.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Введите имя файла для загрузки", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(docsDir, name + ".txt");
        if (!file.exists()) {
            Toast.makeText(this, "Файл не найден: " + file.getName(), Toast.LENGTH_SHORT).show();
            return;
        }
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
             BufferedReader br = new BufferedReader(isr)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append('\n');
            etQuote.setText(sb.toString().trim());
            Toast.makeText(this, "Загружено из " + file.getName(), Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(this, "Ошибка чтения: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int req, @NonNull String[] perms, @NonNull int[] res) {
        super.onRequestPermissionsResult(req, perms, res);
        if (req == REQ_PERM && (res.length == 0 || res[0] != PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(this, "Нет разрешения на запись во внешнее хранилище", Toast.LENGTH_LONG).show();
        }
    }
}
