package pl.kartven.javaproapp.ui;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import pl.kartven.javaproapp.data.MainRepository;

public class SlidesFragmentViewModel extends ViewModel {
    @Inject
    public MainRepository mainRepository;
}
