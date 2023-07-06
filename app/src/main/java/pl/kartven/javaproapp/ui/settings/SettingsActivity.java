package pl.kartven.javaproapp.ui.settings;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import io.vavr.control.Option;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.databinding.ActivitySettingsBinding;
import pl.kartven.javaproapp.ui.auth.LoginActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.SessionManager;

public class SettingsActivity extends BaseActivity {
    private SettingsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel(SettingsViewModel.class);
        ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment(viewModel.getSessionManager()))
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

    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {
        private final SessionManager sessionManager;

        public SettingsFragment(SessionManager sessionManager) {
            this.sessionManager = sessionManager;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            Option.of(findPreference("logout_button"))
                    .map(Preference.class::cast)
                    .peek(o -> o.setOnPreferenceClickListener(this));
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (preference.getKey().equals("logout_button")) {
                sessionManager.clear();
                ActivityUtils.goToActivity(requireActivity(), LoginActivity.class);
                return true;
            }
            return false;
        }
    }
}