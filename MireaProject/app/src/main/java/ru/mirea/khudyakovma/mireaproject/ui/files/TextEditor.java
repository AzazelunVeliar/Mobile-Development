package ru.mirea.khudyakovma.mireaproject.ui.files;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.mirea.khudyakovma.mireaproject.databinding.FragmentTextEditorBinding;

public class TextEditor extends Fragment {
    private FragmentTextEditorBinding binding;
    private String filePath;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTextEditorBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            filePath = getArguments().getString("filePath");
            loadFile();
        }
        binding.btnSave.setOnClickListener(v -> saveFile());
        return binding.getRoot();
    }

    private void loadFile() {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buf = new byte[fis.available()];
            fis.read(buf);
            binding.etEditor.setText(new String(buf));
        } catch (IOException e) {
            binding.etEditor.setText("");
        }
    }

    private void saveFile() {
        String text = binding.etEditor.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(requireContext(),
                    "Нечего сохранять", Toast.LENGTH_SHORT).show();
            return;
        }
        try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            fos.write(text.getBytes());
            Toast.makeText(requireContext(),
                    "Сохранено", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(requireContext(),
                    "Ошибка сохранения", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
