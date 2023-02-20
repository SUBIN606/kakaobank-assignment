package pojo;

/** 댓글 */
public class Comment {

    private String originalText;

    private String text;

    public Comment(final String originalText, final String text) {
        this.originalText = originalText;
        this.text = text;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "originalText='" + originalText + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
