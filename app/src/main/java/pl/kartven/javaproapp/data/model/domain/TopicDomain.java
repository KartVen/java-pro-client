package pl.kartven.javaproapp.data.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import pl.kartven.javaproapp.data.model.api.TopicApi;

public class TopicDomain implements Serializable {
    private Long id;
    private String name;
    private String description;

    public TopicDomain(Long id, String name, String description) {
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

    public static TopicDomain map(TopicApi topicApi) {
        return new TopicDomain(topicApi.getId(), topicApi.getName(), topicApi.getDescription());
    }

    public static List<TopicDomain> map(List<TopicApi> topicApis) {
        return topicApis.stream().map(TopicDomain::map).collect(Collectors.toList());
    }
}
