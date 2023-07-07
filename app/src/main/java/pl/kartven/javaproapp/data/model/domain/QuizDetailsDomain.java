package pl.kartven.javaproapp.data.model.domain;

import pl.kartven.javaproapp.data.model.api.QuizDetailsApi;

public class QuizDetailsDomain extends QuizDomain {
    private Topic topic;
    private Long questions;
    private UserApi creator;

    public QuizDetailsDomain(Long id, String name, String description, Topic topic, Long questions, UserApi creator) {
        super(id, name, description);
        this.topic = topic;
        this.questions = questions;
        this.creator = creator;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Long getQuestions() {
        return questions;
    }

    public void setQuestions(Long questions) {
        this.questions = questions;
    }

    public UserApi getCreator() {
        return creator;
    }

    public void setCreator(UserApi creator) {
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

    public static QuizDetailsDomain map(QuizDetailsApi quizDetailsApi) {
        return new QuizDetailsDomain(
                quizDetailsApi.getId(), quizDetailsApi.getName(), quizDetailsApi.getDescription(),
                quizDetailsApi.getTopic(), quizDetailsApi.getQuestions(), quizDetailsApi.getCreator()
        );
    }
}
