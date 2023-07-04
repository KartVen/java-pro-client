package pl.kartven.javaproapp.ui.creator.fragment;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;

@HiltViewModel
public class CreatorLinkViewModel extends ViewModel {
    private final MainRepository mainRepository;

    @Inject
    public CreatorLinkViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }
}
