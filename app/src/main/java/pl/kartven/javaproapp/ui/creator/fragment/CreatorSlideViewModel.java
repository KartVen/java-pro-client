package pl.kartven.javaproapp.ui.creator.fragment;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.utils.utility.Resource;

@HiltViewModel
public class CreatorSlideViewModel extends ViewModel {
    private final MainRepository mainRepository;
    private List<Uri> imageUris = Collections.emptyList();

    @Inject
    public CreatorSlideViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public List<Uri> getImageUris() {
        return imageUris;
    }

    public void setImageUris(List<Uri> imageUris) {
        this.imageUris = imageUris;
    }

    public Resource<Void> postSlidesOfTopic(Long id, @NonNull List<Uri> imageUris) {
        return mainRepository.postSlidesOfTopic(id, processImageUris(imageUris));
    }

    private List<MultipartBody.Part> processImageUris(@NonNull List<Uri> imageUris) {
        return imageUris.stream()
                .map(uri -> {
                    File file = new File(uri.getPath());
                    RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/*"));
                    return MultipartBody.Part.createFormData("slides", file.getName(), requestFile);
                })
                .collect(Collectors.toList());
    }
}
