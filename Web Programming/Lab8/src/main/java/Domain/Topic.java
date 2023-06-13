package Domain;

import java.util.List;

public class Topic {
    private int id;
    private String content;
    private List<Comment> comments;

    public Topic(int id, String content, List<Comment> comments) {
        this.id = id;
        this.content = content;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Topic [id=" + id + ", content='" + content + '\'' + ", comments=" + comments + "]";
    }
}
