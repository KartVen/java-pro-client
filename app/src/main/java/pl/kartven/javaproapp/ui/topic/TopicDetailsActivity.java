package pl.kartven.javaproapp.ui.topic;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dagger.hilt.android.AndroidEntryPoint;
import io.vavr.control.Option;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.ActivityTopicDetailsBinding;
import pl.kartven.javaproapp.ui.main.MainActivity;
import pl.kartven.javaproapp.ui.topic.fragment.CodesFragment;
import pl.kartven.javaproapp.ui.topic.fragment.LinksFragment;
import pl.kartven.javaproapp.ui.topic.fragment.QuizzesFragment;
import pl.kartven.javaproapp.ui.topic.fragment.SlidesFragment;
import pl.kartven.javaproapp.utils.base.BaseActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.State;

@AndroidEntryPoint
public class TopicDetailsActivity extends BaseActivity {

    private ActivityTopicDetailsBinding binding;
    private FragmentManager fragmentManager;
    private final State<TopicDomain> topicDomainState = State.getState();
    private SlidesFragment slidesFragment;
    private CodesFragment codesFragment;
    private QuizzesFragment quizzesFragment;
    private LinksFragment linksFragment;

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopicDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        topicDomainState.setData(Option.of(savedInstanceState)
                .map(bundle -> (TopicDomain) bundle.getSerializable(Constant.Extra.TOPIC_MODEL))
                .getOrElse(
                        Option.of(getIntent().getExtras())
                                .map(bundle -> (TopicDomain) bundle.getSerializable(Constant.Extra.TOPIC_MODEL))
                                .getOrNull()
                )
        );

        if (!topicDomainState.isExists()) {
            ActivityUtils.goToActivity(this, MainActivity.class);
            ActivityUtils.showToast(this, "Internal Error");
        }

        initContent();
        initActions();
        initFragments();
    }

    @Override
    protected void initContent() {
        super.initContent();
        String name = topicDomainState.getData().getName();
        binding.topicDetailsTvHeaderName.setText(name != null ? name : "");
        String description = topicDomainState.getData().getDescription();
        binding.topicDetailsTvHeaderDesc.setText(description != null ? description : "");
    }

    @Override
    protected void initActions() {
        super.initActions();
        binding.topicDetailsHeaderBackBtnIv.setOnClickListener(v -> onBackPressed());

        BottomAppBar bottomAppBar = binding.bottomAppBar;
        BottomNavigationView bottomNavigationView = binding.bottomNavView;
        FloatingActionButton fab = binding.bottomFloatingButton;
        setSupportActionBar(bottomAppBar);

        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            showFragment(getFragment(item.getItemId()));
            return true;
        });
    }

    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        slidesFragment = SlidesFragment.newInstance();
        codesFragment = CodesFragment.newInstance();
        quizzesFragment = QuizzesFragment.newInstance();
        linksFragment = LinksFragment.newInstance();

        showFragment(slidesFragment);
    }

    @SuppressLint("NonConstantResourceId")
    private Fragment getFragment(int itemId) {
        switch (itemId) {
            case R.id.topic_menu_slide:
                return slidesFragment;
            case R.id.topic_menu_lab:
                return codesFragment;
            case R.id.topic_menu_quiz:
                return quizzesFragment;
            case R.id.topic_menu_links:
                return linksFragment;
        }
        throw new RuntimeException("Internal Error");
    }

    private void showFragment(Fragment fragment) {
        if (fragment == null) return;
        fragmentManager
                .beginTransaction()
                .replace(binding.topicDetailsFrame.getId(), fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        topicDomainState.clear();
    }
}