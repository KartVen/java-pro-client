package pl.kartven.javaproapp.ui.topic.quiz;

import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.vavr.control.Option;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.QuizDetailsDomain;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.databinding.ActivityQuizBinding;
import pl.kartven.javaproapp.ui.topic.quiz.question.QuestionActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.Resource;

public class QuizActivity extends BaseActivity {

    private ActivityQuizBinding binding;
    private QuizViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel(QuizViewModel.class);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initBundleVariable(savedInstanceState);

        initActions();
        initContent();
    }

    @Override
    protected void initBundleVariable(@Nullable Bundle savedInstanceState) {
        super.initBundleVariable(savedInstanceState);
        viewModel.setQuizDomain((QuizDomain) getVariableFromBundle(savedInstanceState, bundle ->
                bundle.getSerializable(Constant.Extra.QUIZ_MODEL)
        ));
        if (Objects.isNull(viewModel.getQuizDomain())) handleError(true, this::onBackPressed);
    }

    @Override
    protected void initActions() {
        super.initActions();
        setSupportActionBar(binding.quizToolbar);
        Option.of(getSupportActionBar()).peek(bar -> bar.setDisplayHomeAsUpEnabled(true));
        binding.quizBtnStartQuiz.setOnClickListener(v -> {
            ActivityUtils.goToActivity(this, QuestionActivity.class, intent -> {
                intent.putExtra(Constant.Extra.QUIZ_MODEL, viewModel.getQuizDomain());
            });
            finish();
        });
    }

    @Override
    protected void initContent() {
        super.initContent();
        Executors.newSingleThreadExecutor().execute(() -> {
            Resource<QuizDetailsDomain> resource = viewModel.getQuizDetails(viewModel.getQuizDomain().getId());
            this.runOnUiThread(() -> {
                QuizDetailsDomain quizDetailsDomain = resource.getData();
                if (!resource.isSuccess()) handleError(false, this::onBackPressed);
                binding.quizTvTitle.setText(quizDetailsDomain.getName());
                binding.quizTvDesc.setText(quizDetailsDomain.getDescription());
                binding.quizTvQue.setText(String.valueOf(quizDetailsDomain.getQuestions()));
                if (quizDetailsDomain.getQuestions() < 1) {
                    binding.quizBtnStartQuiz.setClickable(false);
                    binding.quizBtnStartQuiz.setBackgroundColor(getColor(R.color.darker_grey));
                };
            });
        });
    }
}