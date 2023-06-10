package pl.kartven.javaproapp.ui;

import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.databinding.ActivityMainBinding;
import pl.kartven.javaproapp.ui.adapter.TopicListAdapter;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.util.ActivityUtility;
import pl.kartven.javaproapp.util.RVItemClicked;
import pl.kartven.javaproapp.util.Resource;
import pl.kartven.javaproapp.util.SessionManager;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements ActivityUtility {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActionBar();

        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder().permitAll().build()
        );

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initRecyclerView();
    }

    private void initActionBar() {
        BottomAppBar bottomAppBar = binding.mainBottomAppBar;
        BottomNavigationView bottomNavigationView = binding.mainBottomNavView1;
        FloatingActionButton fab = binding.mainFab;

        setSupportActionBar(bottomAppBar);

        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.profile) {
                goToActivity(this, ProfileActivity.class);
            }
            return true;
        });
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        bottomNavigationView.setOnApplyWindowInsetsListener(null);
        bottomNavigationView.setPadding(0,0,0,0);

        fab.setOnClickListener(v -> {});
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = binding.mainRecyclerView1;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Resource<List<TopicDomain>> topics = viewModel.getTopics();

        TopicListAdapter adapter = new TopicListAdapter(getList(topics));
        adapter.setItemClicked((model, position) -> showToast(this, model.getId().toString()));

        recyclerView.setAdapter(adapter);
    }

    private List<TopicDomain> getList(Resource<List<TopicDomain>> topics) {
        return topics.isSuccess() ? topics.getData() : Collections.emptyList();
    }
}