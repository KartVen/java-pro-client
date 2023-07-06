package pl.kartven.javaproapp.data.model.api.request;

public class LinkReqApi {
    private String name;
    private String url;
    private SectionApi section;

    public LinkReqApi(String name, String url, SectionApi section) {
        this.name = name;
        this.url = url;
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
