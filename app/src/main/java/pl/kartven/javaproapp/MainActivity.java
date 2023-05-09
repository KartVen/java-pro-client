package pl.kartven.javaproapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.vavr.control.Option;
import pl.kartven.javaproapp.databinding.ActivityMainBinding;
import pl.kartven.javaproapp.ui.auth.AuthLoginActivity;
import pl.kartven.javaproapp.ui.laboratory.LaboratoryRangeSelectionActivity;
import pl.kartven.javaproapp.ui.lecture.LectureRangeSelectionActivity;
import pl.kartven.javaproapp.ui.quiz.QuizRangeSelectionActivity;
import pl.kartven.javaproapp.util.SessionManager;
import pl.kartven.javaproapp.util.ActivityUtility;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements ActivityUtility {

    private ActivityMainBinding binding;
    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder().permitAll().build()
        );

        if (!sessionManager.isLoggedIn()) {
            goToActivity(this, AuthLoginActivity.class);
        }

        setSupportActionBar((Toolbar) binding.activityAppBar.getChildAt(0));
        Option.of(getSupportActionBar())
                .map(actionBar -> {
                    actionBar.setTitle(null);
                    return null;
                });

        binding.section0Card.setOnClickListener(l -> goToActivity(this, LectureRangeSelectionActivity.class));
        binding.section1Card.setOnClickListener(l -> goToActivity(this, LaboratoryRangeSelectionActivity.class));
        binding.section2Card.setOnClickListener(l -> goToActivity(this, QuizRangeSelectionActivity.class));
        binding.section3Card.setOnClickListener(l -> {});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_main_action_settings) {
            //goToActivity(this, MainActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}