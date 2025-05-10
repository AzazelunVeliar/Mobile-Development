package ru.mirea.khudyakovma.securesharedpreferences;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {
    private TextView tvPoetName;
    private ImageView ivPoet;
    private EncryptedSharedPreferences securePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPoetName = findViewById(R.id.tvPoetName);
        ivPoet     = findViewById(R.id.ivPoet);

        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            securePrefs = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    masterKeyAlias,
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Не удалось инициализировать securePrefs", e);
        }

        String poetName  = securePrefs.getString("POET_NAME",  "Михаил Юрьевич Лермонтов");
        String imageName = securePrefs.getString("POET_IMAGE", "lermontov");

        tvPoetName.setText(poetName);
        int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        ivPoet.setImageResource(resId);
    }
}