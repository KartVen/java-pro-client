package pl.kartven.javaproapp.ui.stats;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.utils.utility.SessionManager;

@HiltViewModel
public class StatsViewModel extends ViewModel {

    private final SessionManager sessionManager;

    @Inject
    public StatsViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void logout() {
        sessionManager.clear();
    }
}
