package pl.kartven.javaproapp.ui.topic;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;
import java.util.Optional;

import io.vavr.control.Option;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.ActivityTopicBinding;
import pl.kartven.javaproapp.ui.main.MainActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.Constant;

public class TopicActivity extends BaseActivity {
    private ActivityTopicBinding binding;
    private TopicViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel(TopicViewModel.class);
        initBundleVariable(savedInstanceState);
        binding = ActivityTopicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initActions();
        initContent();
    }

    @Override
    protected void initBundleVariable(@Nullable Bundle savedInstanceState) {
        super.initBundleVariable(savedInstanceState);
        viewModel.setTopicDomain((TopicDomain) getVariableFromBundle(savedInstanceState, bundle ->
                bundle.getSerializable(Constant.Extra.TOPIC_MODEL)
        ));
        if (Objects.isNull(viewModel.getTopicDomain())) {
            handleError(() -> ActivityUtils.goToActivity(this, MainActivity.class));
        }
    }

    @Override
    protected void initActions() {
        super.initActions();
        setSupportActionBar(binding.topicToolbar);
        Optional.ofNullable(getSupportActionBar()).ifPresent(bar -> {
            bar.setElevation(0);
            bar.setDisplayHomeAsUpEnabled(true);
        });
        initBottomBar();
    }

    private void initBottomBar() {
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.topic_nav_slides,
                R.id.topic_nav_codes,
                R.id.topic_nav_quizzes,
                R.id.topic_nav_links
        )
                .build();
        NavController navController = Navigation.findNavController(this, R.id.topic_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.topicNavView, navController);
    }

    @Override
    protected void initContent() {
        super.initContent();
        binding.topicTvHeaderName.setText(viewModel.getTopicDomain().getName());
        binding.topicTvHeaderDesc.setText(viewModel.getTopicDomain().getDescription());
    }
}