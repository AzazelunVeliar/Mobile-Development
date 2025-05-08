package ru.mirea.khudyakovma.mireaproject.ui.data;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.mirea.khudyakovma.mireaproject.databinding.FragmentDataBinding;

public class DataFragment extends Fragment {
    private FragmentDataBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDataBinding.inflate(inflater, container, false);
        binding.textData.setText(
                "3D-моделирование в современной индустрии\n\n" +
                        "3D-моделирование — это процесс создания трёхмерной цифровой модели объекта или поверхности. " +
                        "Оно активно применяется в архитектуре, игровой индустрии, кино, инженерии и медицине. " +
                        "С помощью программных средств, таких как Blender, Autodesk Maya и SolidWorks, дизайнеры и инженеры " +
                        "могут визуализировать идеи, проектировать конструкции и создавать анимации. " +
                        "Современное 3D-моделирование позволяет достичь высокой точности и реализма, " +
                        "а также ускоряет разработку и снижает затраты на производство физических прототипов."
        );
        return binding.getRoot();
    }
}
