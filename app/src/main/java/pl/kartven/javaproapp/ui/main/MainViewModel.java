package pl.kartven.javaproapp.ui.main;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.utils.utility.SessionManager;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final SessionManager sessionManager;

    @Inject
    public MainViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void logout() {
        sessionManager.clear();
    }
}