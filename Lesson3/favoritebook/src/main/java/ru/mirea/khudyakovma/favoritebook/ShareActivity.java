package ru.mirea.khudyakovma.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {
    private EditText editTextBook;
    private EditText editTextQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        TextView textDevBook = findViewById(R.id.textViewDevBook);
        TextView textDevQuote = findViewById(R.id.textViewDevQuote);
        editTextBook = findViewById(R.id.editTextBook);
        editTextQuote = findViewById(R.id.editTextQuote);
        Button buttonSend = findViewById(R.id.buttonSend);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String bookName = extras.getString(MainActivity.BOOK_NAME_KEY);
            String quote = extras.getString(MainActivity.QUOTES_KEY);
            textDevBook.setText("Любимая книга разработчика: " + bookName);
            textDevQuote.setText("Цитата из книги: " + quote);
        }

        buttonSend.setOnClickListener(view -> {
            String userBook = editTextBook.getText().toString();
            String userQuote = editTextQuote.getText().toString();
            String result = String.format("Название Вашей любимой книги: %s. Цитата: %s", userBook, userQuote);

            Intent data = new Intent();
            data.putExtra(MainActivity.USER_MESSAGE, result);
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}
