package pl.kartven.javaproapp.data.model.domain;

import java.util.List;
import java.util.stream.Collectors;

import pl.kartven.javaproapp.data.model.api.CodeApi;

public class CodeDomain {
    private Long id;
    private String name;
    private String content;

    public CodeDomain(Long id, String name, String content) {
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

    public static CodeDomain map(CodeApi codeApi) {
        return new CodeDomain(codeApi.getId(), codeApi.getName(), codeApi.getContent());
    }

    public static List<CodeDomain> map(List<CodeApi> codeApis) {
        return codeApis.stream().map(CodeDomain::map).collect(Collectors.toList());
    }
}
