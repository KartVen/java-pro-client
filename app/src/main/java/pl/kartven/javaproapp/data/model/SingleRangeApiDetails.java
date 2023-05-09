package pl.kartven.javaproapp.data.model;

public class SingleRangeApiDetails {
    Long id;
    String topic;
    String description;

    public SingleRangeApiDetails(Long id, String topic, String description) {
        this.id = id;
        this.topic = topic;
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}