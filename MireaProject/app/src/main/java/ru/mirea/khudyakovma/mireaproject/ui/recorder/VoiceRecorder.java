package ru.mirea.khudyakovma.mireaproject.ui.recorder;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.khudyakovma.mireaproject.databinding.FragmentVoiceRecorderBinding;

public class VoiceRecorder extends Fragment {
    private FragmentVoiceRecorderBinding binding;
    private boolean isRecording = false;
    private MediaRecorder recorder;
    private Uri audioUri;
    private ParcelFileDescriptor pfd;
    private final List<Recording> recordings = new ArrayList<>();
    private ArrayAdapter<Recording> adapter;
    private MediaPlayer player;
    private ActivityResultLauncher<String> micPermissionLauncher;
    private String currentRecordingName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        micPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> {
                    if (!granted) {
                        Toast.makeText(requireContext(),
                                "Permission RECORD_AUDIO is required",
                                Toast.LENGTH_LONG).show();
                    }
                }
        );
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentVoiceRecorderBinding.inflate(inflater, container, false);
        adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, recordings);
        binding.recordingsList.setAdapter(adapter);
        binding.recordingsList.setOnItemClickListener((parent, view, pos, id) -> {
            Recording rec = recordings.get(pos);
            playRecording(rec.getUri());
        });
        binding.recordButton.setOnClickListener(v -> {
            if (!isRecording) {
                EditText input = new EditText(requireContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(requireContext())
                        .setTitle("Введите имя записи")
                        .setView(input)
                        .setPositiveButton("OK", (dialog, which) -> {
                            String name = input.getText().toString().trim();
                            if (name.isEmpty()) {
                                Toast.makeText(requireContext(),
                                        "Имя не может быть пустым",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                currentRecordingName = name;
                                startRecording(name);
                                binding.recordButton.setText("Stop Recording");
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            } else {
                stopRecording();
                binding.recordButton.setText("Start Recording");
            }
            isRecording = !isRecording;
        });
        return binding.getRoot();
    }

    private void startRecording(@NonNull String name) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                String filename = name + ".3gp";
                values.put(MediaStore.Audio.Media.DISPLAY_NAME, filename);
                values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
                values.put(MediaStore.Audio.Media.RELATIVE_PATH,
                        Environment.DIRECTORY_MUSIC + "/AudioRecord");
                audioUri = requireContext().getContentResolver()
                        .insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
                pfd = requireContext().getContentResolver()
                        .openFileDescriptor(audioUri, "w");
            } else {
                File dir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_MUSIC);
                if (!dir.exists()) dir.mkdirs();
                File out = new File(dir, name + ".3gp");
                pfd = ParcelFileDescriptor.open(out,
                        ParcelFileDescriptor.MODE_CREATE
                                | ParcelFileDescriptor.MODE_READ_WRITE);
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
            e.printStackTrace();
            Toast.makeText(requireContext(),
                    "Recording error", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        try {
            recorder.stop();
            recorder.release();
            recorder = null;
            pfd.close();
            Recording rec = new Recording(audioUri, currentRecordingName);
            recordings.add(rec);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playRecording(@NonNull Uri uri) {
        if (player != null) {
            player.release();
            player = null;
        }
        player = new MediaPlayer();
        try {
            player.setDataSource(requireContext(), uri);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(),
                    "Playback error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
        if (player != null) {
            player.release();
            player = null;
        }
        binding = null;
    }
}
