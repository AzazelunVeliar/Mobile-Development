package ru.mirea.khudyakovma.mireaproject.ui.files;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import ru.mirea.khudyakovma.mireaproject.R;
import ru.mirea.khudyakovma.mireaproject.databinding.DialogCreateFileBinding;
import ru.mirea.khudyakovma.mireaproject.databinding.FragmentFilesBinding;

public class Files extends Fragment {
    private FragmentFilesBinding binding;
    private final ArrayList<String> files = new ArrayList<>();
    private FileListAdapter adapter;
    private File dir;
    private ActivityResultLauncher<String> writePerm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFilesBinding.inflate(inflater, container, false);

        File docs = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
        dir = new File(docs, "Doc");
        if (!dir.exists()) dir.mkdirs();

        writePerm = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), granted -> {
                    if (!granted) {
                        Toast.makeText(requireContext(),
                                "Нужно разрешение на запись", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q &&
                ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            writePerm.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        adapter = new FileListAdapter(files, this::openEditor);
        binding.rvFiles.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvFiles.setAdapter(adapter);

        loadList();

        binding.fabAdd.setOnClickListener(v -> showCreateDialog());

        return binding.getRoot();
    }

    private void loadList() {
        files.clear();
        File[] list = dir.listFiles((d,name)-> name.endsWith(".txt"));
        if (list!=null) for (File f: list) files.add(f.getName());
        adapter.notifyDataSetChanged();
    }

    private void showCreateDialog() {
        DialogCreateFileBinding dB = DialogCreateFileBinding.inflate(getLayoutInflater());
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Новый файл")
                .setView(dB.getRoot())
                .setPositiveButton("OK", (dlg, w) -> {
                    String name = dB.etFileName.getText().toString().trim();
                    String content = dB.etContent.getText().toString();
                    if (TextUtils.isEmpty(name)) {
                        Toast.makeText(requireContext(),
                                "Имя не задано", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    File file = new File(dir, name + ".txt");
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(content.getBytes());
                        Toast.makeText(requireContext(),
                                "Сохранено", Toast.LENGTH_SHORT).show();
                        loadList();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void openEditor(String filename) {
        Bundle args = new Bundle();
        args.putString("filePath", new File(dir, filename).getAbsolutePath());
        NavHostFragment.findNavController(this)
                .navigate(R.id.nav_files_editor, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
