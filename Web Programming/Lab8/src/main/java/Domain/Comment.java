package Domain;

public class Comment {
    private int id;
    private String commentText;
    private User user;

    public Comment(int id, String commentText, User user) {
        this.id = id;
        this.commentText = commentText;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getCommentText() {
        return commentText;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment [id=" + id + ", commentText='" + commentText + '\'' + ", user=" + user + "]";
    }
}
