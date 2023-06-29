package pl.kartven.javaproapp.ui.topic.quiz.answer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pl.kartven.javaproapp.databinding.ActivityAnswerBinding;
import pl.kartven.javaproapp.utils.utility.BaseActivity;

public class AnswerActivity extends BaseActivity {

    ActivityAnswerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnswerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}