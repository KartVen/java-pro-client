package pl.kartven.javaproapp.ui.topic.quiz;

import android.os.Bundle;

import io.vavr.control.Option;
import pl.kartven.javaproapp.data.model.domain.QuizDetailsDomain;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.databinding.ActivityQuizDetailsBinding;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.Resource;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.Constant;

public class QuizDetailsActivity extends BaseActivity {

    private ActivityQuizDetailsBinding binding;
    private QuizDetailsViewModel viewModel;
    private QuizDomain quizDomainState;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        quizDomainState = Option.of(savedInstanceState)
                .map(bundle -> (QuizDomain) bundle.getSerializable(Constant.Extra.QUIZ_MODEL))
                .getOrElse(
                        Option.of(getIntent().getExtras())
                                .map(bundle -> (QuizDomain) bundle.getSerializable(Constant.Extra.QUIZ_MODEL))
                                .getOrNull()
                );

        if (quizDomainState == null) handleError(false, this::onBackPressed);

        viewModel = initViewModel(this, QuizDetailsViewModel.class);

        initActions();
        initContent();
    }

    @Override
    protected void initActions() {
        super.initActions();
        binding.quizDHeaderBackBtnIv.setOnClickListener(v -> onBackPressed());
        binding.quizDBtnStartQuiz.setOnClickListener(v ->
                ActivityUtils.goToActivity(this, null, intent -> {

                })
        );
    }

    @Override
    protected void initContent() {
        super.initContent();
        Resource<QuizDetailsDomain> resource = viewModel.getQuizDetails(quizDomainState.getId());
        if (!resource.isSuccess()) handleError(false, this::onBackPressed);
        QuizDetailsDomain quizDetailsDomain = resource.getData();
        binding.quizDTvTitle.setText(quizDetailsDomain.getName());
        binding.quizDTvDesc.setText(quizDetailsDomain.getDescription());
        binding.quizDTvQue.setText(String.valueOf(quizDetailsDomain.getQuestions()));
    }
}