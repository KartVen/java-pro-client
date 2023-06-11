package pl.kartven.javaproapp.data.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import pl.kartven.javaproapp.data.model.api.SectionApi;

public class SectionDomain implements Serializable {
    private Long id;
    private String name;

    public SectionDomain(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static SectionDomain map(SectionApi sectionApi) {
        return new SectionDomain(sectionApi.getId(), sectionApi.getName());
    }

    public static List<SectionDomain> map(List<SectionApi> sectionApis) {
        return sectionApis.stream().map(SectionDomain::map).collect(Collectors.toList());
    }
    
}
