
package pl.kartven.javaproapp.data.model;

import android.util.Base64;

public class SlideApi {
    private Long id;
    private String content;

    public SlideApi(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public byte[] getContent() {
        return Base64.decode(content, Base64.DEFAULT);
    }
}
