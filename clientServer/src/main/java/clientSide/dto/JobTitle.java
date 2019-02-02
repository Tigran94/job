package clientSide.dto;

import lombok.Getter;

@Getter
public class JobTitle {
    private long id;
    private String title;

    public JobTitle(long id, String title) {
        this.id = id;
        this.title = title;
    }
}
