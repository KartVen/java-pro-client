package pl.kartven.javaproapp.ui.topic.quiz;

import android.os.Bundle;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.databinding.ActivityQuizDetailsBinding;
import pl.kartven.javaproapp.utils.base.BaseActivity;

@AndroidEntryPoint
public class QuizDetailsActivity extends BaseActivity {

    private ActivityQuizDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}