package pl.kartven.javaproapp.ui;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.util.SessionManager;

@HiltViewModel
public class ProfileViewModel extends ViewModel {
    private final SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void logout() {
        sessionManager.clear();
    }
}
