package pl.kartven.javaproapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private Fragment slidesFragment;
    private Fragment codesFragment;
    private Fragment quizzesFragment;
    private Fragment externalLinksFragment;
    private TopicDomain topic;

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
        slidesFragment = SlidesFragment.newInstance(topic);
        codesFragment = CodesFragment.newInstance(topic);
        quizzesFragment = QuizzesFragment.newInstance(topic);
        externalLinksFragment = LinksFragment.newInstance(topic);

        showFragment(slidesFragment);
    }

    private void showFragment(Fragment fragment) {
        if (fragment == null) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(binding.topicDetailsFragment1.getId(), fragment);
        fragmentTransaction.commit();
    }

    private Fragment getFragment(int itemId) {
        if (itemId == R.id.type_slide) return slidesFragment;
        if (itemId == R.id.type_code) return codesFragment;
        if (itemId == R.id.type_quiz) return quizzesFragment;
        if (itemId == R.id.type_links) return externalLinksFragment;
        return null;
    }
}