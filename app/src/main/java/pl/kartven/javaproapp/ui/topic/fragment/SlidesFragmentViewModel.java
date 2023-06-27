package pl.kartven.javaproapp.ui.topic.fragment;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;

@HiltViewModel
public class SlidesFragmentViewModel extends ViewModel {
    private MainRepository mainRepository;

    @Inject
    public SlidesFragmentViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }
}
