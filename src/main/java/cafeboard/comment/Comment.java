package cafeboard.comment;

import cafeboard.BaseEntity;
import cafeboard.post.Post;
import jakarta.persistence.*;

@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Post post;

    protected Comment() {
    }

    public Comment(String content, String name, Post post) {
        this.content = content;
        this.writer = name;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public Post getPost() {
        return post;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
