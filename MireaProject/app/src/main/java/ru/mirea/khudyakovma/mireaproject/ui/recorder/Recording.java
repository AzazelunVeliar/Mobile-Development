package ru.mirea.khudyakovma.mireaproject.ui.recorder;

import android.net.Uri;

public class Recording {
    private final Uri uri;
    private final String name;

    public Recording(Uri uri, String name) {
        this.uri = uri;
        this.name = name;
    }

    public Uri getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return name;
    }
}
