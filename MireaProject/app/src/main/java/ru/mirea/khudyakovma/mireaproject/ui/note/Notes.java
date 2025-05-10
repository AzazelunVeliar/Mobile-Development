package ru.mirea.khudyakovma.mireaproject.ui.note;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ru.mirea.khudyakovma.mireaproject.databinding.FragmentNotesBinding;

public class Notes extends Fragment {
    private FragmentNotesBinding binding;
    private File photoFile;
    private ActivityResultLauncher<String> cameraPerm;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private final ArrayList<String> titles = new ArrayList<>();
    private final ArrayList<Uri> uris = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private SharedPreferences prefs;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotesBinding.inflate(inflater, container, false);
        prefs = requireContext()
                .getSharedPreferences("notes_prefs", Context.MODE_PRIVATE);
        loadNotes();

        adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, titles);
        ListView list = binding.notesList;
        list.setAdapter(adapter);

        cameraPerm = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> {
                    if (!granted) {
                        Toast.makeText(requireContext(),
                                "Разрешение на камеру отклонено",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == requireActivity().RESULT_OK) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                            requireActivity().sendBroadcast(
                                    new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                            Uri.fromFile(photoFile))
                            );
                        }
                        String name = binding.noteTitle.getText()
                                .toString().trim();
                        titles.add(name);
                        uris.add(Uri.fromFile(photoFile));
                        adapter.notifyDataSetChanged();
                        saveNotes();
                    }
                }
        );

        binding.captureButton.setOnClickListener(v -> {
            String title = binding.noteTitle.getText().toString().trim();
            if (title.isEmpty()) {
                binding.noteTitle.setError("Введите имя заметки");
                return;
            }
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                cameraPerm.launch(Manifest.permission.CAMERA);
            } else {
                launchCamera();
            }
        });

        list.setOnItemClickListener((parent, view, pos, id) -> {
            Uri uri = uris.get(pos);
            ImageView iv = new ImageView(requireContext());
            iv.setImageBitmap(BitmapFactory.decodeFile(
                    new File(uri.getPath()).getAbsolutePath()));
            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setView(iv)
                    .setPositiveButton("Закрыть", null)
                    .show();
        });

        return binding.getRoot();
    }

    private void launchCamera() {
        String raw = binding.noteTitle.getText().toString()
                .replaceAll("[^\\w]", "_");
        String ts = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH)
                .format(new Date());
        String fname = raw + "_" + ts + ".jpg";

        File dir = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES),
                "Images");
        if (!dir.exists()) dir.mkdirs();

        photoFile = new File(dir, fname);

        Uri imageUri = FileProvider.getUriForFile(
                requireContext(),
                requireActivity().getPackageName() + ".fileprovider",
                photoFile);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cameraLauncher.launch(intent);
    }

    private void loadNotes() {
        String json = prefs.getString("notes_json", "[]");
        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                titles.add(obj.getString("title"));
                uris.add(Uri.parse(obj.getString("uri")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveNotes() {
        JSONArray arr = new JSONArray();
        for (int i = 0; i < titles.size(); i++) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("title", titles.get(i));
                obj.put("uri", uris.get(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            arr.put(obj);
        }
        prefs.edit()
                .putString("notes_json", arr.toString())
                .apply();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
