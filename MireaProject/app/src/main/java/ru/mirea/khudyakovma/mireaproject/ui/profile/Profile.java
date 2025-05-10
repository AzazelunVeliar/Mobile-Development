package ru.mirea.khudyakovma.mireaproject.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.mirea.khudyakovma.mireaproject.databinding.FragmentProfileBinding;
public class Profile extends Fragment {
    private FragmentProfileBinding binding;
    private static final String PREFS = "profile_prefs";
    private static final String KEY_NAME = "key_name";
    private static final String KEY_EMAIL = "key_email";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        SharedPreferences prefs = requireContext()
                .getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        binding.etName.setText(prefs.getString(KEY_NAME, ""));
        binding.etEmail.setText(prefs.getString(KEY_EMAIL, ""));

        binding.btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor e = prefs.edit();
            e.putString(KEY_NAME, binding.etName.getText().toString());
            e.putString(KEY_EMAIL, binding.etEmail.getText().toString());
            e.apply();
            Toast.makeText(requireContext(), "Параметры сохранены", Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}