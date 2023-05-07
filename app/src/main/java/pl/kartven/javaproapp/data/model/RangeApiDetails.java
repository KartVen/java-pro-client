package pl.kartven.javaproapp.data.model;

public class RangeApiDetails {
    Long id;
    String topic;
    String description;

    public RangeApiDetails(Long id, String topic, String description) {
        this.id = id;
        this.topic = topic;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getDescription() {
        return description;
    }
}