package pl.kartven.javaproapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import io.vavr.control.Option;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.ActivityTopicDetailsBinding;
import pl.kartven.javaproapp.util.ActivityUtility;
import pl.kartven.javaproapp.util.Constants;

@AndroidEntryPoint
public class TopicDetailsActivity extends AppCompatActivity implements ActivityUtility {

    private ActivityTopicDetailsBinding binding;
    private FragmentManager fragmentManager;
    private ArrayList<FragmentInfo> fragments = new ArrayList<>();
    private TopicDomain topic;
    private Integer activeFragmentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopicDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        topic = Option.of(savedInstanceState)
                .map(bundle -> (TopicDomain) bundle.getSerializable(Constants.TOPIC_MODEL_NAME))
                .getOrElse(
                        Option.of(getIntent().getExtras())
                                .map(bundle -> (TopicDomain) bundle.getSerializable(Constants.TOPIC_MODEL_NAME))
                                .getOrNull()
                );

        activeFragmentPos = Option.of(savedInstanceState)
                .map(bundle -> bundle.getInt(Constants.TOPIC_DETAILS_ACTIVE_FRAGMENT_STATE))
                .getOrElse(
                        Option.of(getIntent().getExtras())
                                .map(bundle -> bundle.getInt(Constants.TOPIC_DETAILS_ACTIVE_FRAGMENT_STATE))
                                .getOrElse(0)
                );

        if (topic == null) {
            goToActivity(this, MainActivity.class);
            showToast(this, "Internal Error");
        }

        initActions();
        initFragments();

        binding.topicDetailsTextView2.setText(String.valueOf(topic.getName()));
        binding.topicDetailsTextView3.setText(String.valueOf(topic.getDescription()));
    }

    private void initActions() {
        binding.topicDetailsImageView1.setOnClickListener(v -> onBackPressed());
        initActionBar();
    }

    private void initActionBar() {
        BottomAppBar bottomAppBar = binding.topicDetailsBottomAppBar;
        BottomNavigationView bottomNavigationView = binding.topicDetailsBottomNavView1;
        FloatingActionButton fab = binding.topicDetailsFab;

        setSupportActionBar(bottomAppBar);

        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            showFragment(getFragment(item.getItemId()));
            return true;
        });
    }

    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        fragments.add(new FragmentInfo(R.id.type_slide, SlidesFragment.newInstance(topic)));
        fragments.add(new FragmentInfo(R.id.type_code, CodesFragment.newInstance(topic)));
        fragments.add(new FragmentInfo(R.id.type_quiz, QuizzesFragment.newInstance(topic)));
        fragments.add(new FragmentInfo(R.id.type_links, LinksFragment.newInstance(topic)));

        if (activeFragmentPos >= 0 && activeFragmentPos < fragments.size())
            showFragment(fragments.get(activeFragmentPos).fragment);
    }

    private void showFragment(Fragment fragment) {
        if (fragment == null) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(binding.topicDetailsFragment1.getId(), fragment);
        fragmentTransaction.commit();
    }

    private Fragment getFragment(int itemId) {
        for (int i = 0; i < fragments.size(); i++) {
            FragmentInfo fragmentInfo = fragments.get(i);
            if (fragmentInfo.id == itemId) {
                activeFragmentPos = i;
                return fragmentInfo.fragment;
            };
        }
        return null;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.TOPIC_MODEL_NAME, topic);
        outState.putInt(Constants.TOPIC_DETAILS_ACTIVE_FRAGMENT_STATE, activeFragmentPos);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        topic = (TopicDomain) savedInstanceState.getSerializable(Constants.TOPIC_MODEL_NAME);
        activeFragmentPos = savedInstanceState.getInt(Constants.TOPIC_DETAILS_ACTIVE_FRAGMENT_STATE);
    }

    private static class FragmentInfo {
        private final int id;
        private final Fragment fragment;

        public FragmentInfo(int id, Fragment fragment) {
            this.id = id;
            this.fragment = fragment;
        }
    }
}