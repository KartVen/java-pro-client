package pl.kartven.javaproapp.ui.topic.quiz;

import android.os.Bundle;

import androidx.annotation.Nullable;

import io.vavr.control.Option;
import io.vavr.control.Try;
import pl.kartven.javaproapp.data.model.domain.QuizDetailsDomain;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.databinding.ActivityQuizDetailsBinding;
import pl.kartven.javaproapp.ui.topic.quiz.answer.AnswerActivity;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.Resource;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.Constant;

public class QuizDetailsActivity extends BaseActivity {

    private ActivityQuizDetailsBinding binding;
    private QuizDetailsViewModel viewModel;
    private QuizDomain quizDomainState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBundle(savedInstanceState);

        if (quizDomainState == null) handleError(false, this::onBackPressed);

        viewModel = initViewModel(QuizDetailsViewModel.class);

        initActions();
        initContent();
    }

    @Override
    protected void initBundle(@Nullable Bundle savedInstanceState) {
        super.initBundle(savedInstanceState);
        quizDomainState = Option.of(savedInstanceState)
                .map(bundle -> (QuizDomain) bundle.getSerializable(Constant.Extra.QUIZ_MODEL))
                .getOrElse(
                        Option.of(getIntent().getExtras())
                                .map(bundle -> (QuizDomain) bundle.getSerializable(Constant.Extra.QUIZ_MODEL))
                                .getOrNull()
                );
    }

    @Override
    protected void initActions() {
        super.initActions();
        binding.quizDHeaderBackBtnIv.setOnClickListener(v -> onBackPressed());
        binding.quizDBtnStartQuiz.setOnClickListener(v ->
                ActivityUtils.goToActivity(this, AnswerActivity.class, intent -> {

                })
        );
    }

    @Override
    protected void initContent() {
        super.initContent();
        Resource<QuizDetailsDomain> quizDetailsDomainResource = viewModel.getQuizDetails(quizDomainState.getId());
        if (!quizDetailsDomainResource.isSuccess()){
            handleError(false, this::onBackPressed);
        }
        QuizDetailsDomain quizDetailsDomain = quizDetailsDomainResource.getData();
        binding.quizDTvTitle.setText(quizDetailsDomain.getName());
        binding.quizDTvDesc.setText(quizDetailsDomain.getDescription());
        binding.quizDTvQue.setText(String.valueOf(quizDetailsDomain.getQuestions()));
    }
}