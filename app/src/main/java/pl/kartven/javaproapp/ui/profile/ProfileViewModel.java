package pl.kartven.javaproapp.ui.profile;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.utils.utility.SessionManager;

@HiltViewModel
public class ProfileViewModel extends ViewModel {
    @Inject
    public ProfileViewModel() {
    }
}
