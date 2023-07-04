package pl.kartven.javaproapp.ui.creator;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CreatorViewModel extends ViewModel {

    private boolean isExtendedCreating = false;

    @Inject
    public CreatorViewModel() {
    }

    public boolean isExtendedCreating() {
        return isExtendedCreating;
    }

    public void setExtendedCreating(boolean extendedCreating) {
        isExtendedCreating = extendedCreating;
    }
}
