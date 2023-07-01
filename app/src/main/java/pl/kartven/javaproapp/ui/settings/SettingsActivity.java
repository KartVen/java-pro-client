package pl.kartven.javaproapp.ui.settings;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.preference.PreferenceFragmentCompat;

import io.vavr.control.Option;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.databinding.ActivitySettingsBinding;
import pl.kartven.javaproapp.utils.utility.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pl.kartven.javaproapp.databinding.ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }

        initActions();
    }

    @Override
    protected void initActions() {
        super.initActions();
        Option.of(getSupportActionBar()).peek(bar -> bar.setDisplayHomeAsUpEnabled(true));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}