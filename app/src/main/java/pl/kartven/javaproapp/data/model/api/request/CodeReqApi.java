package pl.kartven.javaproapp.data.model.api.request;

public class CodeReqApi {
    private String name;
    private String content;
    private SectionApi section;

    public CodeReqApi(String name, String content, SectionApi section) {
        this.name = name;
        this.content = content;
        this.section = section;
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

    public SectionApi getSection() {
        return section;
    }

    public void setSection(SectionApi section) {
        this.section = section;
    }

    public static class SectionApi {
        private Long id;
        private String name;

        public SectionApi(Long id, String name) {
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
    }
}
