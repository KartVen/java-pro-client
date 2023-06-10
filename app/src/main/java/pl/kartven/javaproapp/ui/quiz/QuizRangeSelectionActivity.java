package pl.kartven.javaproapp.ui.quiz;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Collections;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.vavr.control.Option;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.databinding.ActivityQuizRangeSelectionBinding;
import pl.kartven.javaproapp.ui.base.RangeSelectionAdapter;
import pl.kartven.javaproapp.ui.model.LectureListItemDetails;
import pl.kartven.javaproapp.ui.quiz.adapter.QuizRangeSelectionAdapter;
import pl.kartven.javaproapp.util.ActivityUtility;
import pl.kartven.javaproapp.util.Extra;
import pl.kartven.javaproapp.util.Resource;

@AndroidEntryPoint
public class QuizRangeSelectionActivity extends AppCompatActivity implements ActivityUtility, RangeSelectionAdapter.Builder<LectureListItemDetails> {

    private ActivityQuizRangeSelectionBinding binding;
    private QuizRangeSelectionViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizRangeSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(QuizRangeSelectionViewModel.class);

        setSupportActionBar((Toolbar) binding.activityAppBar.getChildAt(0));
        Option.of(getSupportActionBar())
                .map(actionBar -> {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setTitle(R.string.select_range);
                    return null;
                });
        initRecyclerView();
    }

    @Override
    public <T> void initRecyclerView(Class<T> targetActivityClass) {
        binding.rangeSelectionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RangeSelectionAdapter<LectureListItemDetails> rvAdapter = initAdapterWithData();
        binding.rangeSelectionRecyclerView.setAdapter(rvAdapter);

        rvAdapter.setItemClicked((model, position) -> {
            if (targetActivityClass == null) return;
            this.startActivity(new Intent(this, targetActivityClass)
                    .putExtra(Extra.LECTURE_ID, model.getId())
                    .putExtra(Extra.LECTURE_NAME, model.getTopic())
            );
        });
    }

    @Override
    public RangeSelectionAdapter<LectureListItemDetails> initAdapterWithData() {
        Resource<List<LectureListItemDetails>> rangeData = viewModel.getRangeData();
        if (rangeData.isSuccess()) {
            return new QuizRangeSelectionAdapter(rangeData.getData());
        } else {
            showToast(this, rangeData.getMessage());
            return new QuizRangeSelectionAdapter(Collections.emptyList());
        }
    }
}