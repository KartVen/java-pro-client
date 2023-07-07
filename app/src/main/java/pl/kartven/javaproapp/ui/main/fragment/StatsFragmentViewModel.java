package pl.kartven.javaproapp.ui.main.fragment;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.utils.utility.Resource;
import pl.kartven.javaproapp.utils.utility.SessionManager;

@HiltViewModel
public class StatsFragmentViewModel extends ViewModel {

    private final MainRepository mainRepository;
    private final SessionManager sessionManager;

    @Inject
    public StatsFragmentViewModel(MainRepository mainRepository, SessionManager sessionManager) {
        this.mainRepository = mainRepository;
        this.sessionManager = sessionManager;
    }

    public Resource<List<TopicDomain>> getMyTopics() {
        return mainRepository.getMyTopics();
    }
}
