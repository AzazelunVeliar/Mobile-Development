package ru.mirea.khudyakovma.cryptoloader;

import android.content.Context;
import android.os.Bundle;

import androidx.loader.content.AsyncTaskLoader;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {
    private final Bundle args;
    public static final String ARG_WORD = "encrypted";

    public MyLoader(Context context, Bundle args) {
        super(context);
        this.args = args;
    }

    public static String decryptMsg(byte[] cipherText, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(cipherText));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        byte[] cryptText = args.getByteArray(ARG_WORD);
        byte[] keyBytes = args.getByteArray("key");

        SecretKey originalKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
        return decryptMsg(cryptText, originalKey);
    }
}
