package ru.mirea.khudyakovma.simplefragmentapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment1, fragment2;
    private boolean isDualPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isDualPane = findViewById(R.id.list) != null && findViewById(R.id.viewer) != null;

        if (!isDualPane) {
            fragment1 = new FirstFragment();
            fragment2 = new SecondFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();

            Button btnFirstFragment = findViewById(R.id.btnFirstFragment);
            btnFirstFragment.setOnClickListener(v ->
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, fragment1)
                            .commit()
            );

            Button btnSecondFragment = findViewById(R.id.btnSecondFragment);
            btnSecondFragment.setOnClickListener(v ->
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, fragment2)
                            .commit()
            );
        }
    }
}
