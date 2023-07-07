package pl.kartven.javaproapp.ui.topic.quiz.question;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.vavr.collection.Stream;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.QuestionDomain;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.databinding.ActivityQuestionBinding;
import pl.kartven.javaproapp.ui.main.MainActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.Resource;

public class QuestionActivity extends BaseActivity {

    private ActivityQuestionBinding binding;
    private QuestionViewModel viewModel;
    private List<CheckBox> checkBoxes;
    private int currentQuestionIndex = 0;
    private int btnState = 1;
    private boolean currentIsCorrect = true;
    private int allCorrectAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel(QuestionViewModel.class);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
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
    }

    @Override
    protected void initActions() {
        super.initActions();
        setSupportActionBar(binding.questionToolbar);
        AppCompatButton questionBtnNext = binding.questionBtnNext;
        questionBtnNext.setOnClickListener(v -> {
            switch (btnState) {
                case 1:
                    handleValidate();
                    handleQuestion();
                    break;
                case 2:
                    handleValidate();
                    handleEnd();
                    btnState = 3;
                    break;
                default:
                    ActivityUtils.goToActivity(this, MainActivity.class, intent -> {
                    });
            }
        });
    }

    @Override
    protected void initContent() {
        super.initContent();
        checkBoxes = IntStream.range(0, binding.questionCheckboxWrapper.getChildCount())
                .mapToObj(binding.questionCheckboxWrapper::getChildAt)
                .map(CheckBox.class::cast)
                .collect(Collectors.toList());

        Executors.newSingleThreadExecutor().execute(() -> {
            Resource<List<QuestionDomain>> listResource = viewModel.getQuestionsOfTopic(viewModel.getQuizDomain().getId());
            this.runOnUiThread(() -> {
                if (!listResource.isSuccess()) {
                    handleError(true, this::onBackPressed);
                    return;
                }
                System.out.println(listResource.getData().toString());
                viewModel.setQuestionsList(listResource.getData());
                handleQuestion();
                binding.questionLinearAnswer.setVisibility(View.VISIBLE);
            });
        });
    }

    private void handleQuestion() {
        QuestionDomain questionDomain = viewModel.getQuestionsList().get(currentQuestionIndex);
        binding.questionTvQuestion.setText(questionDomain.getText());
        List<QuestionDomain.Answer> answers = questionDomain.getAnswers();
        Stream.ofAll(checkBoxes)
                .zipWithIndex()
                .forEach(tuple -> {
                    if (Objects.nonNull(answers) && tuple._2 < answers.size()) {
                        tuple._1.setText(answers.get(tuple._2).getText());
                    } else tuple._1.setVisibility(View.GONE);
                });
        if (++currentQuestionIndex < viewModel.getQuestionsList().size()) return;
        btnState++;
        binding.questionBtnNext.setText(getString(R.string.label_finish));
    }

    private void handleValidate() {
        currentIsCorrect = true;
        List<QuestionDomain.Answer> answers = viewModel.getQuestionsList().get(currentQuestionIndex - 1).getAnswers();
        Stream.ofAll(checkBoxes)
                .zipWithIndex()
                .forEach(tuple -> {
                    if (currentIsCorrect && Objects.nonNull(answers) && tuple._2 < answers.size()) {
                        if (tuple._1.isChecked() != answers.get(tuple._2).isCorrect()) {
                            currentIsCorrect = false;
                        }
                    }
                    tuple._1.setChecked(false);
                });
        if (currentIsCorrect) allCorrectAmount++;
    }

    private void handleEnd() {
        int questionAmount = viewModel.getQuestionsList().size();
        if (allCorrectAmount < questionAmount) {
            binding.questionTvEndHead.setText(getString(R.string.label_im_sorry));
            binding.questionIvTrophy.setImageResource(R.drawable.computer);
        }
        binding.questionTvEndCorrectQue.setText(
                MessageFormat.format("{0} {1}", getString(R.string.label_correct_que), allCorrectAmount)
        );
        binding.questionTvEndFailQue.setText(
                MessageFormat.format("{0} {1}", getString(R.string.label_fail_que), questionAmount - allCorrectAmount)
        );
        binding.questionTvEndAllQue.setText(
                MessageFormat.format("{0} {1}", getString(R.string.label_all_que), questionAmount)
        );
        binding.questionLinearAnswer.setVisibility(View.GONE);
        binding.questionLinearEnd.setVisibility(View.VISIBLE);
    }
}