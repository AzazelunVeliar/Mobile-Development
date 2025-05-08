package ru.mirea.khudyakovma.mireaproject.ui.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import ru.mirea.khudyakovma.mireaproject.databinding.FragmentMediaPlayerBinding;

public class MediaPlayer extends Fragment {
    private FragmentMediaPlayerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMediaPlayerBinding.inflate(inflater, container, false);

        binding.buttonReinRaus.setOnClickListener(v -> playTrack(0, "Rammstein - Rein Raus"));
        binding.buttonBenzin.setOnClickListener(v -> playTrack(1, "Rammstein - Benzin"));
        binding.buttonGiftig.setOnClickListener(v -> playTrack(2, "Rammstein - Giftig"));
        binding.buttonMeinTeil.setOnClickListener(v -> playTrack(3, "Rammstein - Mein Teil"));

        binding.buttonPause.setOnClickListener(v -> {
            Intent pauseIntent = new Intent(getContext(), MyPlayer.class);
            pauseIntent.setAction(MyPlayer.ACTION_PAUSE);
            ContextCompat.startForegroundService(requireContext(), pauseIntent);
            binding.statusText.setText("Статус: Пауза");
        });

        binding.buttonResume.setOnClickListener(v -> {
            Intent resumeIntent = new Intent(getContext(), MyPlayer.class);
            resumeIntent.setAction(MyPlayer.ACTION_RESUME);
            ContextCompat.startForegroundService(requireContext(), resumeIntent);
            binding.statusText.setText("Статус: Воспроизведение");
        });

        binding.buttonStop.setOnClickListener(v -> {
            requireActivity().stopService(new Intent(getContext(), MyPlayer.class));
            binding.statusText.setText("Статус: Остановлено");
        });

        return binding.getRoot();
    }

    private void playTrack(int index, String title) {
        Intent intent = new Intent(getContext(), MyPlayer.class);
        intent.putExtra(MyPlayer.EXTRA_TRACK_INDEX, index);
        ContextCompat.startForegroundService(requireContext(), intent);
        binding.statusText.setText("Статус: Играет — " + title);
    }
}
