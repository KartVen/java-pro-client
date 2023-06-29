package pl.kartven.javaproapp.ui.stats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pl.kartven.javaproapp.databinding.ActivityStatsBinding;
import pl.kartven.javaproapp.utils.utility.BaseActivity;

public class StatsActivity extends BaseActivity {

    ActivityStatsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActions();
    }

    @Override
    protected void initActions() {
        super.initActions();
        binding.statsHeaderBackBtnIv.setOnClickListener(v -> onBackPressed());
    }
}