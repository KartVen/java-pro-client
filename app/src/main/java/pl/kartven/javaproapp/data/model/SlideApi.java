
package pl.kartven.javaproapp.data.model;

public class SlideApi {
    private Long id;
    private byte[] content;

    public SlideApi(Long id, byte[] content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public byte[] getContent() {
        return content;
    }
}
