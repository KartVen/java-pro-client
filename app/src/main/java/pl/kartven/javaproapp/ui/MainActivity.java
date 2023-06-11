package pl.kartven.javaproapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

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
import pl.kartven.javaproapp.util.Constants;
import pl.kartven.javaproapp.util.Resource;
import pl.kartven.javaproapp.util.SessionManager;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements ActivityUtility {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    @Inject
    SessionManager sessionManager;
    private boolean isHidden = false;

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
        TextView textView2 = binding.mainTextView2;
        SearchView searchView1 = binding.mainSearchView1;
        ImageView imageView2 = binding.mainImageView2;

        setSupportActionBar(bottomAppBar);

        bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.profile) {
                goToActivity(this, ProfileActivity.class);
            }
            return true;
        });
        bottomNavigationView.setBackground(null);
//        bottomNavigationView.setOnApplyWindowInsetsListener(null);
//        bottomNavigationView.setPadding(0,0,0,0);

        fab.setOnClickListener(v -> {
        });

        imageView2.setOnClickListener(l -> {
            if (isHidden) {
                textView2.setVisibility(View.VISIBLE);
                searchView1.setVisibility(View.VISIBLE);
                imageView2.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);
            } else {
                textView2.setVisibility(View.GONE);
                searchView1.setVisibility(View.GONE);
                imageView2.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
            }
            isHidden = !isHidden;
        });

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = binding.mainRecyclerView1;
        TextView textView2 = binding.mainTextView2;
        SearchView searchView1 = binding.mainSearchView1;

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Resource<List<TopicDomain>> topics = viewModel.getTopics();

        TopicListAdapter adapter = new TopicListAdapter(getList(topics));
        adapter.setItemClicked((model, position) -> {
            Intent intent = new Intent(this, TopicDetailsActivity.class);
            intent.putExtra(Constants.TOPIC_MODEL_NAME, model);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    private List<TopicDomain> getList(Resource<List<TopicDomain>> topics) {
        if (topics == null) return Collections.emptyList();
        if (topics.isSuccess()) return topics.getData();
        showToast(this, topics.getMessage());
        return Collections.emptyList();
    }
}