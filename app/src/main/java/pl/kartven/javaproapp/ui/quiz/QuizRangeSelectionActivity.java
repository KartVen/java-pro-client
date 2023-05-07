package pl.kartven.javaproapp.ui.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import dagger.hilt.android.AndroidEntryPoint;
import io.vavr.control.Option;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.databinding.ActivityQuizRangeSelectionBinding;

@AndroidEntryPoint
public class QuizRangeSelectionActivity extends AppCompatActivity {

    private ActivityQuizRangeSelectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizRangeSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar((Toolbar) binding.activityAppBar.getChildAt(0));
        Option.of(getSupportActionBar())
                .map(actionBar -> {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setTitle(R.string.select_range);
                    return null;
                });

    }
}