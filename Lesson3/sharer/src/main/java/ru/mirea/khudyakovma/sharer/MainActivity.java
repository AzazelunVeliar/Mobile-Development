package ru.mirea.khudyakovma.sharer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> pickImageLauncher;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonShare = findViewById(R.id.buttonShareText);
        Button buttonPick = findViewById(R.id.buttonPickImage);
        resultTextView = findViewById(R.id.textViewResult);

        buttonShare.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Mirea");
            startActivity(Intent.createChooser(intent, "Выбор за вами!"));
        });

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri data = result.getData().getData();
                        String msg = "Выбранное изображение: " + (data != null ? data.toString() : "null");
                        Log.d("Sharer", msg);
                        resultTextView.setText(msg);
                    }
                });

        buttonPick.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");

            Intent chooser = Intent.createChooser(intent, "Выберите приложение");
            pickImageLauncher.launch(chooser);
        });
    }
}
