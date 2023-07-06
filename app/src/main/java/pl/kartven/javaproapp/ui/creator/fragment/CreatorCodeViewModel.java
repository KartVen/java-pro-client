package pl.kartven.javaproapp.ui.creator.fragment;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.api.request.CodeReqApi;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.utils.utility.Resource;

@HiltViewModel
public class CreatorCodeViewModel extends ViewModel {
    private final MainRepository mainRepository;
    private SectionDomain sectionDomain;
    private int selectSectionMode;

    @Inject
    public CreatorCodeViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public Resource<List<SectionDomain>> getSectionsOfTopic(Long id) {
        return mainRepository.getSectionsOfTopic(id);
    }

    public void setSectionDomain(SectionDomain sectionDomain) {
        this.sectionDomain = sectionDomain;
    }

    public void setSelectSectionMode(int selectSectionMode) {
        this.selectSectionMode = selectSectionMode;
    }

    public int getSelectSectionMode() {
        return selectSectionMode;
    }

    public Resource<Void> postCodeOfTopic(Long id, String name, String code, String section) {
        return mainRepository.postCodeOfTopic(id, new CodeReqApi(name, code, selectSectionMode == 0 ?
                new CodeReqApi.SectionApi(sectionDomain.getId(), null) : new CodeReqApi.SectionApi(null, section)
        ));
    }
}
