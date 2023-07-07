package pl.kartven.javaproapp.ui.topic;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;

@HiltViewModel
public class TopicViewModel extends ViewModel {
    private TopicDomain topicDomain;

    @Inject
    public TopicViewModel() {
    }

    public TopicDomain getTopicDomain() {
        return topicDomain;
    }

    public void setTopicDomain(TopicDomain topicDomain) {
        this.topicDomain = topicDomain;
    }
}
