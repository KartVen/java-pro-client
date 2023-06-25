package pl.kartven.javaproapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.ActivityMainBinding;
import pl.kartven.javaproapp.ui.main.adapter.TopicListAdapter;
import pl.kartven.javaproapp.ui.profile.ProfileActivity;
import pl.kartven.javaproapp.ui.settings.SettingsActivity;
import pl.kartven.javaproapp.ui.topic.TopicDetailsActivity;
import pl.kartven.javaproapp.utils.resource.Resource;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.base.BaseActivity;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.SessionManager;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {

    private MainViewModel viewModel;
    @Inject
    SessionManager sessionManager;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder().permitAll().build()
        );

        viewModel = initViewModel(MainViewModel.class);

        initContent();
        initActions();
    }

    @Override
    protected void initContent() {
        super.initContent();
        initRecyclerView();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void initActions() {
        super.initActions();
        BottomAppBar bottomAppBar = binding.bottomAppBar;
        BottomNavigationView bottomNavView = binding.bottomNavView;
        FloatingActionButton bottomFloatingButton = binding.bottomFloatingButton;

        setSupportActionBar(bottomAppBar);

        bottomNavView.getMenu().getItem(2).setEnabled(false);
        bottomNavView.setBackground(null);
        bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.main_menu_profile:
                    ActivityUtils.goToActivity(this, ProfileActivity.class);
                    break;
                case R.id.main_menu_stats:
                    break;
                case R.id.main_menu_settings:
                    ActivityUtils.goToActivity(this, SettingsActivity.class);
                    break;
            }
            return true;
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerViewTopic = binding.mainRv;
        recyclerViewTopic.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
        setAdapter(recyclerViewTopic, viewModel.getTopics());

        RecyclerView recyclerViewLastAddedByYou = binding.mainNewTopicRv;
        recyclerViewLastAddedByYou.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
        setAdapter(recyclerViewLastAddedByYou, viewModel.getMyTopics());
    }

    private void setAdapter(RecyclerView recyclerView, Resource<List<TopicDomain>> topics) {
        TopicListAdapter adapter = new TopicListAdapter(ListUtils.extractList(topics, this));
        adapter.setItemClicked((model, position) ->
                ActivityUtils.goToActivity(this, TopicDetailsActivity.class, intent -> intent.putExtra(Constant.Extra.TOPIC_MODEL, model))
        );
        recyclerView.setAdapter(adapter);
    }
}