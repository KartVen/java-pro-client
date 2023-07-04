package pl.kartven.javaproapp.ui.creator;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;
import java.util.Optional;

import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.databinding.ActivityCreatorBinding;
import pl.kartven.javaproapp.utils.listener.NavActiveFragmentListener;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.Constant;

public class CreatorActivity extends BaseActivity implements NavActiveFragmentListener {
    private ActivityCreatorBinding binding;
    private CreatorViewModel viewModel;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel(CreatorViewModel.class);
        binding = ActivityCreatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initActions();
        initContent();
    }

    @Override
    protected void initActions() {
        super.initActions();
        setSupportActionBar(binding.creatorToolbar);
        Optional.ofNullable(getSupportActionBar()).ifPresent(bar -> bar.setDisplayHomeAsUpEnabled(true));
        initNavController();
    }

    @Override
    protected void initContent() {
        super.initContent();
        binding.creatorAppcbtnSave.setOnClickListener(v -> {
            if (viewModel.isExtendedCreating()) {
                NavDestination currentDestination = navController.getCurrentDestination();
                if (currentDestination != null) {
                    navigate(currentDestination.getId());
                }
            } else {
                getActiveFragment(this, R.id.creator_fragment)
                        .filter(f -> f instanceof CreatorEventListener)
                        .map(CreatorEventListener.class::cast)
                        .peek(f -> f.handleSave(CreatorEventListener.Mode.NEW))
                        .onEmpty(() ->
                                ActivityUtils.showToast(this, Constant.Expression.INTERNAL_ERROR)
                        );
            }
        });
        binding.creatorAppcbtnCancel.setOnClickListener(v -> super.onBackPressed());
    }

    @SuppressLint("NonConstantResourceId")
    private void navigate(int currentDestinationId) {
        switch (currentDestinationId) {
            case R.id.creator_nav_topic:
                navController.navigate(R.id.creator_page_slide);
                break;
            case R.id.creator_nav_slide:
                navController.navigate(R.id.creator_page_code);
                break;
            case R.id.creator_nav_code:
                navController.navigate(R.id.creator_page_link);
                break;
            case R.id.creator_nav_link:
                break;
        }
    }

    private void initNavController() {
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.creator_nav_topic,
                R.id.creator_nav_slide,
                R.id.creator_nav_code
        )
                .build();

        navController = Navigation.findNavController(this, R.id.creator_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onSupportNavigateUp() {
        return (Objects.nonNull(navController) && navController.navigateUp()) || super.onSupportNavigateUp();
    }

    public interface CreatorEventListener {
        void handleSave(Mode saveMode);

        enum Mode {
            NEW, EDIT
        }
    }
}