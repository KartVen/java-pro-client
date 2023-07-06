package pl.kartven.javaproapp.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.AppBarConfiguration.OnNavigateUpListener;
import androidx.navigation.ui.NavigationUI;

import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.AuthDomain;
import pl.kartven.javaproapp.databinding.ActivityMainBinding;
import pl.kartven.javaproapp.ui.creator.CreatorActivity;
import pl.kartven.javaproapp.utils.listener.NavActiveFragmentListener;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseActivity;

public class MainActivity extends BaseActivity implements NavActiveFragmentListener, OnNavigateUpListener {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel(MainViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActions();
        initContent();
    }

    @Override
    protected void initActions() {
        super.initActions();
        setSupportActionBar(binding.mainInclude.mainCoordinatorToolbar);
        initNavDrawer();
        binding.mainInclude.mainCoordinatorFab.setOnClickListener(
                v -> ActivityUtils.goToActivity(this, CreatorActivity.class)
        );
    }

    @Override
    protected void initContent() {
        super.initContent();
        AuthDomain user = viewModel.getLoggedUser().getData();
        View headerView = binding.mainNavView.getHeaderView(0);
        ((TextView) headerView.findViewById(R.id.main_d_h_tv_nickname)).setText(user.getNickname());
        ((TextView) headerView.findViewById(R.id.main_d_h_tv_email)).setText(user.getEmail());
    }

    private void initNavDrawer() {
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.main_nav_home,
                R.id.main_nav_profile,
                R.id.main_nav_stats,
                R.id.main_nav_settings
        )
                .setOpenableLayout(binding.main)
                .build();

        navController = Navigation.findNavController(this, R.id.main_content_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.mainNavView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.main_content_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
    }
}