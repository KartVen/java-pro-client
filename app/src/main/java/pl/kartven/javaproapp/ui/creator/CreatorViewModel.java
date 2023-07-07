package pl.kartven.javaproapp.ui.creator;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;

@HiltViewModel
public class CreatorViewModel extends ViewModel {

    private TopicDomain topicDomain;
    private boolean isExtendedCreating = false;
    private boolean isSaveStepCorrect = true;

    @Inject
    public CreatorViewModel() {
    }

    public TopicDomain getTopicDomain() {
        return topicDomain;
    }

    public void setTopicDomain(TopicDomain topicDomain) {
        this.topicDomain = topicDomain;
    }

    public boolean isExtendedCreating() {
        return isExtendedCreating;
    }

    public void setExtendedCreating(boolean extendedCreating) {
        isExtendedCreating = extendedCreating;
    }

    public boolean isSaveStepCorrect() {
        return isSaveStepCorrect;
    }

    public void setSaveStepCorrect(boolean saveStepCorrect) {
        isSaveStepCorrect = saveStepCorrect;
    }
}
