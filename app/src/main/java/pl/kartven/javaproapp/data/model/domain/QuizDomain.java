package pl.kartven.javaproapp.data.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import pl.kartven.javaproapp.data.model.api.QuizApi;

public class QuizDomain implements Serializable {
    private Long id;
    private String name;
    private String description;

    public QuizDomain(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static QuizDomain map(QuizApi quizApi) {
        return new QuizDomain(quizApi.getId(), quizApi.getName(), quizApi.getDescription());
    }
    public static List<QuizDomain> map(List<QuizApi> quizApis) {
        return quizApis.stream().map(QuizDomain::map).collect(Collectors.toList());
    }
}
