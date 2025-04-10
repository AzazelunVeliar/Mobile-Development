package ru.mirea.khudyakovma.findviewbyid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView myTextView = findViewById(R.id.textView);
        Button myButton = findViewById(R.id.button);
        CheckBox myCheckBox = findViewById(R.id.checkBox);

        myTextView.setText("New text in MIREA");
        myButton.setText("MireaButton");
        myCheckBox.setChecked(true);
    }
}