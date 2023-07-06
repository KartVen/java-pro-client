package pl.kartven.javaproapp.data.model.api.request;

import java.util.List;

public class QuizReqApi {
    private String name;
    private String description;
    private List<QuestionApi> questions;

    public QuizReqApi(String name, String description, List<QuestionApi> questions) {
        this.name = name;
        this.description = description;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionApi> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionApi> questions) {
        this.questions = questions;
    }

    public static class QuestionApi {
        private String text;
        private List<AnswerApi> answers;

        public QuestionApi(String text, List<AnswerApi> answers) {
            this.text = text;
            this.answers = answers;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<AnswerApi> getAnswers() {
            return answers;
        }

        public void setAnswers(List<AnswerApi> answers) {
            this.answers = answers;
        }

        public static class AnswerApi {
            private String text;
            private boolean isCorrect;

            public AnswerApi(String text, boolean isCorrect) {
                this.text = text;
                this.isCorrect = isCorrect;
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
        }
    }
}