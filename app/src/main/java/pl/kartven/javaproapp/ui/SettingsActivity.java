package pl.kartven.javaproapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.databinding.ActivitySettingsBinding;

@AndroidEntryPoint
public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}