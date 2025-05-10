package ru.mirea.khudyakovma.lesson6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "mirea_settings";
    private EditText etGroup, etNumber, etMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etGroup  = findViewById(R.id.etGroup);
        etNumber = findViewById(R.id.etNumber);
        etMovie  = findViewById(R.id.etMovie);
        Button btnSave = findViewById(R.id.btnSave);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        etGroup.setText(prefs.getString("GROUP", ""));
        etNumber.setText(String.valueOf(prefs.getInt("NUMBER", 0)));
        etMovie.setText(prefs.getString("MOVIE", ""));

        btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor ed = prefs.edit();
            ed.putString("GROUP", etGroup.getText().toString());
            int num = 0;
            try { num = Integer.parseInt(etNumber.getText().toString()); }
            catch (NumberFormatException ignored) {}
            ed.putInt("NUMBER", num);
            ed.putString("MOVIE", etMovie.getText().toString());
            ed.apply();
        });
    }
}