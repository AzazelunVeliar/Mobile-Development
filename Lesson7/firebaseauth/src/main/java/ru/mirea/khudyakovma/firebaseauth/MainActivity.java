package ru.mirea.khudyakovma.firebaseauth;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import ru.mirea.khudyakovma.firebaseauth.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();

        binding.btnRegister.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String pass = binding.etPassword.getText().toString().trim();
            if (!email.isEmpty() && !pass.isEmpty()) {
                auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                if (user != null) {
                                    user.sendEmailVerification();
                                }
                                showProfile(user);
                            } else {
                                binding.tvMessage.setText("Ошибка регистрации");
                                binding.tvMessage.setVisibility(View.VISIBLE);
                            }
                        });
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String pass = binding.etPassword.getText().toString().trim();
            if (!email.isEmpty() && !pass.isEmpty()) {
                auth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                showProfile(auth.getCurrentUser());
                            } else {
                                binding.tvMessage.setText("Ошибка входа");
                                binding.tvMessage.setVisibility(View.VISIBLE);
                            }
                        });
            }
        });

        binding.btnVerify.setOnClickListener(v -> {
            FirebaseUser user = auth.getCurrentUser();
            if (user != null && !user.isEmailVerified()) {
                user.sendEmailVerification();
            }
        });

        binding.btnSignOut.setOnClickListener(v -> {
            auth.signOut();
            showLoginScreen();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            showProfile(user);
        } else {
            showLoginScreen();
        }
    }

    private void showLoginScreen() {
        binding.etEmail.setVisibility(View.VISIBLE);
        binding.etPassword.setVisibility(View.VISIBLE);
        binding.btnRegister.setVisibility(View.VISIBLE);
        binding.btnLogin.setVisibility(View.VISIBLE);
        binding.tvMessage.setVisibility(View.GONE);

        binding.tvEmailInfo.setVisibility(View.GONE);
        binding.tvUidInfo.setVisibility(View.GONE);
        binding.btnVerify.setVisibility(View.GONE);
        binding.btnSignOut.setVisibility(View.GONE);
    }

    private void showProfile(FirebaseUser user) {
        if (user == null) return;

        binding.etEmail.setVisibility(View.GONE);
        binding.etPassword.setVisibility(View.GONE);
        binding.btnRegister.setVisibility(View.GONE);
        binding.btnLogin.setVisibility(View.GONE);
        binding.tvMessage.setVisibility(View.GONE);

        binding.tvEmailInfo.setVisibility(View.VISIBLE);
        binding.tvUidInfo.setVisibility(View.VISIBLE);
        binding.btnVerify.setVisibility(View.VISIBLE);
        binding.btnSignOut.setVisibility(View.VISIBLE);

        String email = user.getEmail() != null ? user.getEmail() : "--";
        String verified = user.isEmailVerified() ? "true" : "false";
        binding.tvEmailInfo.setText("Email User: " + email + " (verified: " + verified + ")");
        binding.tvUidInfo.setText("Firebase UID: " + user.getUid());
    }
}
