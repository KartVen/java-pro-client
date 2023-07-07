package pl.kartven.javaproapp.data.model.api;

import pl.kartven.javaproapp.data.model.domain.QuizDetailsDomain;

public class QuizDetailsApi extends QuizApi {
    private QuizDetailsDomain.Topic topic;
    private Long questions;
    private QuizDetailsDomain.UserApi creator;

    public QuizDetailsApi(Long id, String name, String description, QuizDetailsDomain.Topic topic, Long questions, QuizDetailsDomain.UserApi creator) {
        super(id, name, description);
        this.topic = topic;
        this.questions = questions;
        this.creator = creator;
    }

    public QuizDetailsDomain.Topic getTopic() {
        return topic;
    }

    public void setTopic(QuizDetailsDomain.Topic topic) {
        this.topic = topic;
    }

    public Long getQuestions() {
        return questions;
    }

    public void setQuestions(Long questions) {
        this.questions = questions;
    }

    public QuizDetailsDomain.UserApi getCreator() {
        return creator;
    }

    public void setCreator(QuizDetailsDomain.UserApi creator) {
        this.creator = creator;
    }

    public static class Topic {
        private Long id;
        private String name;

        public Topic(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class UserApi {
        private Long id;
        private String name;

        public UserApi(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
