package ru.mirea.khudyakovma.mireaproject.ui.player;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ru.mirea.khudyakovma.mireaproject.R;

public class MyPlayer extends Service {
    public static final String CHANNEL_ID = "MireaMusicChannel";
    public static final String EXTRA_TRACK_INDEX = "track_index";
    public static final String ACTION_PAUSE = "PAUSE";
    public static final String ACTION_RESUME = "RESUME";

    private MediaPlayer mediaPlayer;
    private final int[] trackList = {
            R.raw.rein_raus,
            R.raw.benzin,
            R.raw.giftig,
            R.raw.mein_teil
    };
    private final String[] trackTitles = {
            "Rammstein - Rein Raus",
            "Rammstein - Benzin",
            "Rammstein - Giftig",
            "Rammstein - Mein Teil"
    };

    private int currentTrackIndex = 0;
    private boolean isPaused = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "MIREA Audio Channel",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Music playback");
        NotificationManagerCompat.from(this).createNotificationChannel(channel);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (ACTION_PAUSE.equals(action) && mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPaused = true;
            updateNotification("Пауза: " + trackTitles[currentTrackIndex], true);
        } else if (ACTION_RESUME.equals(action) && mediaPlayer != null && isPaused) {
            mediaPlayer.start();
            isPaused = false;
            updateNotification("Воспроизведение: " + trackTitles[currentTrackIndex], false);
        } else {
            currentTrackIndex = intent.getIntExtra(EXTRA_TRACK_INDEX, 0);
            isPaused = false;
            playCurrentTrack();
        }
        return START_NOT_STICKY;
    }

    private void playCurrentTrack() {
        if (mediaPlayer != null) mediaPlayer.release();

        mediaPlayer = MediaPlayer.create(this, trackList[currentTrackIndex]);
        mediaPlayer.start();
        updateNotification("Играет: " + trackTitles[currentTrackIndex], false);

        mediaPlayer.setOnCompletionListener(mp -> {
            currentTrackIndex = (currentTrackIndex + 1) % trackList.length;
            playCurrentTrack();
        });
    }

    private void updateNotification(String contentText, boolean isPausedNow) {
        Intent pauseIntent = new Intent(this, MyPlayer.class);
        pauseIntent.setAction(ACTION_PAUSE);
        PendingIntent pausePending = PendingIntent.getService(this, 0, pauseIntent, PendingIntent.FLAG_IMMUTABLE);

        Intent resumeIntent = new Intent(this, MyPlayer.class);
        resumeIntent.setAction(ACTION_RESUME);
        PendingIntent resumePending = PendingIntent.getService(this, 1, resumeIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Action action = isPausedNow
                ? new NotificationCompat.Action.Builder(android.R.drawable.ic_media_play, "▶ Возобновить", resumePending).build()
                : new NotificationCompat.Action.Builder(android.R.drawable.ic_media_pause, "⏸ Пауза", pausePending).build();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Музыкальный плеер")
                .setContentText(contentText)
                .setSmallIcon(R.mipmap.ic_launcher)
                .addAction(action)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        startForeground(1, builder.build());
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
