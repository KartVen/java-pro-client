package pl.kartven.javaproapp.ui.topic.quiz.question;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.vavr.collection.Stream;
import io.vavr.control.Option;
import pl.kartven.javaproapp.data.model.domain.QuestionDomain;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.databinding.ActivityQuestionBinding;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.Resource;

public class QuestionActivity extends BaseActivity {
    private ActivityQuestionBinding binding;
    private QuestionViewModel viewModel;
    private QuizDomain quizDomain;
    private List<CheckBox> checkBoxAnswers;
    private Integer questionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initBundleVariable(savedInstanceState);
        viewModel = initViewModel(QuestionViewModel.class);

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
        setSupportActionBar(binding.questionInclude.answerCoordinatorToolbar);
    }

    @Override
    protected void initContent() {
        super.initContent();
        checkBoxAnswers = Stream.range(0, binding.questionCheckboxWrapper.getChildCount())
                .map(binding.questionCheckboxWrapper::getChildAt)
                .map(CheckBox.class::cast)
                .collect(Collectors.toList());

        Resource<List<QuestionDomain>> questionsOfQuizResource = viewModel.getQuestionsOfQuiz(quizDomain.getId());
        if (questionsOfQuizResource.isSuccess()) handleError(false, super::onBackPressed);
        List<QuestionDomain> questions = questionsOfQuizResource.getData();
        handleQuestion(questions.get(questionIndex++));
    }

    private void handleQuestion(@NonNull QuestionDomain question) {
        List<QuestionDomain.Answer> answers = question.getAnswers();
        Stream.ofAll(checkBoxAnswers)
                .zipWithIndex()
                .forEach(tuple -> {
                    if (Objects.nonNull(answers) && tuple._2 < answers.size()) {
                        tuple._1.setText(answers.get(tuple._2).getText());
                    } else tuple._1.setVisibility(View.GONE);
                });
    }

    @Override
    public void onBackPressed() {
    }
}