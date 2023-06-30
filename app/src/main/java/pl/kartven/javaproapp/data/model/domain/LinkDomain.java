package pl.kartven.javaproapp.data.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import pl.kartven.javaproapp.data.model.api.LinkApi;

public class LinkDomain implements Serializable {
    private Long id;
    private String name;
    private String content;

    public LinkDomain(Long id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static LinkDomain map(LinkApi codeApi) {
        return new LinkDomain(codeApi.getId(), codeApi.getName(), codeApi.getContent());
    }

    public static List<LinkDomain> map(List<LinkApi> codeApis) {
        return codeApis.stream().map(LinkDomain::map).collect(Collectors.toList());
    }
}
