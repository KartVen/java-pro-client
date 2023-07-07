package pl.kartven.javaproapp.data.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import pl.kartven.javaproapp.data.model.api.QuestionApi;

public class QuestionDomain implements Serializable {
    private Long id;
    private String text;
    private List<Answer> answers;

    public QuestionDomain(Long id, String text, List<Answer> answers) {
        this.id = id;
        this.text = text;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public static class Answer {
        private Long id;
        private String text;
        private boolean isCorrect;

        public Answer(Long id, String text, boolean isCorrect) {
            this.id = id;
            this.text = text;
            this.isCorrect = isCorrect;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isCorrect() {
            return isCorrect;
        }

        public void setCorrect(boolean correct) {
            isCorrect = correct;
        }

        public static Answer map(QuestionApi.Answer answer) {
            return new Answer(answer.getId(), answer.getText(), answer.isCorrect());
        }

        public static List<Answer> map(List<QuestionApi.Answer> answers) {
            return answers.stream().map(Answer::map).collect(Collectors.toList());
        }
    }

    public static QuestionDomain map(QuestionApi questionApi) {
        return new QuestionDomain(questionApi.getId(),
                questionApi.getText(), Answer.map(questionApi.getAnswers()));
    }

    public static List<QuestionDomain> map(List<QuestionApi> questionApis) {
        return questionApis.stream().map(QuestionDomain::map).collect(Collectors.toList());
    }

}
