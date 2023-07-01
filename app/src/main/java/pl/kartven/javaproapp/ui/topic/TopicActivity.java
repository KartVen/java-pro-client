package pl.kartven.javaproapp.ui.topic;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import io.vavr.control.Option;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.ActivityTopicBinding;
import pl.kartven.javaproapp.ui.main.MainActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.State;

public class TopicActivity extends BaseActivity {

    private ActivityTopicBinding binding;
    private final State<TopicDomain> topicDomainState = State.getState();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBundleVariable(savedInstanceState);
        initActions();
        initContent();
    }

    @Override
    protected void initBundleVariable(@Nullable Bundle savedInstanceState) {
        super.initBundleVariable(savedInstanceState);
        topicDomainState.setData(getVariableFromBundle(savedInstanceState, bundle ->
                (TopicDomain) bundle.getSerializable(Constant.Extra.TOPIC_MODEL)
        ));

        if (!topicDomainState.isExists()) {
            handleError(() -> ActivityUtils.goToActivity(this, MainActivity.class));
        }
    }

    @Override
    protected void initActions() {
        super.initActions();
        setSupportActionBar(binding.topicInclude.topicCoordinatorToolbar);
        Option.of(getSupportActionBar()).peek(bar -> bar.setDisplayHomeAsUpEnabled(true));
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
        /*String name = topicDomainState.getData().getName();
        binding.topicDetailsTvHeaderName.setText(name != null ? name : "");
        String description = topicDomainState.getData().getDescription();
        binding.topicDetailsTvHeaderDesc.setText(description != null ? description : "");*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        topicDomainState.clear();
    }
}