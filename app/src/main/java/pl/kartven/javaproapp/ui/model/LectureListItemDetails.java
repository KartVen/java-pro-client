package pl.kartven.javaproapp.ui.model;

import java.util.List;
import java.util.stream.Collectors;

import pl.kartven.javaproapp.data.model.RangeApiDetails;

public class LectureListItemDetails {
    private Long id;
    private String topic;
    private String description;

    public LectureListItemDetails(Long id, String topic, String description) {
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

    public static List<LectureListItemDetails> map(List<RangeApiDetails> rangeApiDetailsList) {
        return rangeApiDetailsList.stream().map(LectureListItemDetails::map).collect(Collectors.toList());
    }

    private static LectureListItemDetails map(RangeApiDetails rangeApiDetails) {
        return new LectureListItemDetails(
                rangeApiDetails.getId(),
                rangeApiDetails.getTopic(),
                rangeApiDetails.getDescription()
        );
    }
}
