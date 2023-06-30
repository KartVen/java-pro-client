package pl.kartven.javaproapp.ui.main;

import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.AppBarConfiguration.OnNavigateUpListener;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.databinding.ActivityMainBinding;
import pl.kartven.javaproapp.ui.auth.LoginActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseActivity;

public class MainActivity extends BaseActivity implements OnNavigateUpListener {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = initViewModel(MainViewModel.class);

        initActions();
        initContent();
    }

    @Override
    protected void initActions() {
        super.initActions();
        initNav();
        binding.mainInclude.mainCoordinatorFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void initContent() {
        super.initContent();
    }

    private void initNav() {
        setSupportActionBar(binding.mainInclude.mainCoordinatorToolbar);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.main_nav_home,
                R.id.main_nav_profile,
                R.id.main_nav_stats,
                R.id.main_nav_settings,
                R.id.main_nav_logout
        )
                .setOpenableLayout(binding.main)
                .build();

        navController = Navigation.findNavController(this, R.id.main_content_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.mainNavView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.main_nav_logout) {
                viewModel.logout();
                handleError(false, () -> ActivityUtils.goToActivity(this, LoginActivity.class));
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.main_content_fragment);
        if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.main_nav_logout) {
            viewModel.logout();
            handleError(false, () -> ActivityUtils.goToActivity(this, LoginActivity.class));
            return true;
        }
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }
}