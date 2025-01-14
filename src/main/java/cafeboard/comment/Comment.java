package cafeboard.comment;

import cafeboard.BaseEntity;
import cafeboard.post.Post;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Locale;

@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Post post;

    public Comment() {
    }

    public Comment(String content, String name, Post post) {
        this.content = content;
        this.name = name;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public Post getPost() {
        return post;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
