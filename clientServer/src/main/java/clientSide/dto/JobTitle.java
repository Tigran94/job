package clientSide.dto;

public class JobTitle {
    private long id;
    private String title;

    public JobTitle(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
