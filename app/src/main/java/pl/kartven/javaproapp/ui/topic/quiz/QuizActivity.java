package pl.kartven.javaproapp.ui.topic.quiz;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;

import java.util.Objects;

import io.vavr.control.Option;
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
    private QuizDomain quizDomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initBundleVariable(savedInstanceState);
        viewModel = initViewModel(QuizViewModel.class);

        initActions();
        initContent();
    }

    @Override
    protected void initBundleVariable(@Nullable Bundle savedInstanceState) {
        super.initBundleVariable(savedInstanceState);
        quizDomain = getVariableFromBundle(savedInstanceState, bundle ->
                (QuizDomain) bundle.getSerializable(Constant.Extra.QUIZ_MODEL)
        );
        if (Objects.isNull(quizDomain)) handleError(false, this::onBackPressed);
    }

    @Override
    protected void initActions() {
        super.initActions();
        setSupportActionBar(binding.quizInclude.quizCoordinatorToolbar);
        Option.of(getSupportActionBar()).peek(bar -> bar.setDisplayHomeAsUpEnabled(true));
        binding.quizBtnStartQuiz.setOnClickListener(v ->
                ActivityUtils.goToActivity(this, QuestionActivity.class, intent -> {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constant.Extra.QUIZ_MODEL, quizDomain);
                })
        );
    }

    @Override
    protected void initContent() {
        super.initContent();
        Resource<QuizDetailsDomain> quizDetailsDomainResource = viewModel.getQuizDetails(quizDomain.getId());
        if (!quizDetailsDomainResource.isSuccess()) {
            handleError(false, this::onBackPressed);
        }
        QuizDetailsDomain quizDetailsDomain = quizDetailsDomainResource.getData();
        binding.quizTvTitle.setText(quizDetailsDomain.getName());
        binding.quizTvDesc.setText(quizDetailsDomain.getDescription());
        binding.quizTvQue.setText(String.valueOf(quizDetailsDomain.getQuestions()));
    }
}