package ru.mirea.khudyakovma.cryptoloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ru.mirea.khudyakovma.cryptoloader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private static final int LOADER_ID = 2025;
    private ActivityMainBinding binding;
    public static final String ARG_WORD = "encrypted";

    public static SecretKey generateKey() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            javax.crypto.KeyGenerator kg = javax.crypto.KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            return new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encryptMsg(String message, SecretKey secret) {
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(message.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phrase = binding.editTextPhrase.getText().toString();
                SecretKey key = generateKey();
                byte[] encrypted = encryptMsg(phrase, key);

                binding.textViewStatus.setText("Зашифровано:\n" + Base64.encodeToString(encrypted, Base64.DEFAULT));

                Bundle bundle = new Bundle();
                bundle.putByteArray(ARG_WORD, encrypted);
                bundle.putByteArray("key", key.getEncoded());

                LoaderManager.getInstance(MainActivity.this)
                        .restartLoader(LOADER_ID, bundle, MainActivity.this);
            }
        });
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new MyLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String result) {
        Toast.makeText(this, "Дешифровка: " + result, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {}
}
