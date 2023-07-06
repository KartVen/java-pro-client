package pl.kartven.javaproapp.ui.creator.fragment;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.api.request.LinkReqApi;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.utils.utility.Resource;

@HiltViewModel
public class CreatorLinkViewModel extends ViewModel {
    private final MainRepository mainRepository;
    private SectionDomain sectionDomain;
    private int selectSectionMode;

    @Inject
    public CreatorLinkViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public Resource<List<SectionDomain>> getSectionsOfTopic(Long id) {
        return mainRepository.getSectionsOfTopic(id);
    }


    public void setSectionDomain(SectionDomain sectionDomain) {
        this.sectionDomain = sectionDomain;
    }

    public SectionDomain getSectionDomain() {
        return sectionDomain;
    }

    public int getSelectSectionMode() {
        return selectSectionMode;
    }

    public void setSelectSectionMode(int selectSectionMode) {
        this.selectSectionMode = selectSectionMode;
    }

    public Resource<Void> postLinkOfTopic(Long id, String name, String link, String section) {
        return mainRepository.postLinkOfTopic(id, new LinkReqApi(name, link, selectSectionMode == 0 ?
                new LinkReqApi.SectionApi(sectionDomain.getId(), null) : new LinkReqApi.SectionApi(null, section)
        ));
    }
}
