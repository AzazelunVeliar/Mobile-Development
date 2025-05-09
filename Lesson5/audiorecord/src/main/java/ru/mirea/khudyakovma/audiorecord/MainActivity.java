package ru.mirea.khudyakovma.audiorecord;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AudioRecord";

    private Button recordButton, playButton;
    private boolean isStartRecording = true;
    private boolean isStartPlaying   = true;

    private MediaRecorder recorder;
    private MediaPlayer   player;

    private Uri   audioUri;
    private ParcelFileDescriptor pfd;

    private ActivityResultLauncher<String> audioPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordButton = findViewById(R.id.recordButton);
        playButton   = findViewById(R.id.playButton);
        playButton.setEnabled(false);

        audioPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> { if (!granted) finish(); }
        );
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
        }

        recordButton.setOnClickListener(v -> {
            if (isStartRecording) {
                recordButton.setText("Stop recording");
                playButton.setEnabled(false);
                startRecording();
            } else {
                recordButton.setText("Start recording");
                playButton.setEnabled(true);
                stopRecording();
            }
            isStartRecording = !isStartRecording;
        });

        playButton.setOnClickListener(v -> {
            if (isStartPlaying) {
                playButton.setText("Stop playing");
                recordButton.setEnabled(false);
                startPlaying();
            } else {
                playButton.setText("Start playing");
                recordButton.setEnabled(true);
                stopPlaying();
            }
            isStartPlaying = !isStartPlaying;
        });
    }

    private void startRecording() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                String name = "audio_" + System.currentTimeMillis() + ".3gp";
                values.put(MediaStore.Audio.Media.DISPLAY_NAME, name);
                values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
                values.put(MediaStore.Audio.Media.RELATIVE_PATH,
                        Environment.DIRECTORY_MUSIC + "/AudioRecord");
                audioUri = getContentResolver().insert(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        values
                );
                pfd = getContentResolver().openFileDescriptor(audioUri, "w");
            } else {
                File musicDir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_MUSIC);
                if (!musicDir.exists()) musicDir.mkdirs();
                File out = new File(musicDir, "audio_" + System.currentTimeMillis() + ".3gp");
                pfd = ParcelFileDescriptor.open(out,
                        ParcelFileDescriptor.MODE_READ_WRITE | ParcelFileDescriptor.MODE_CREATE);
                audioUri = Uri.fromFile(out);
            }

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile(pfd.getFileDescriptor());
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.e(TAG, "Recording failed", e);
        }
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        try {
            pfd.close();
        } catch (IOException ignored) {}
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(this, audioUri);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(TAG, "Playback failed", e);
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }
}
