package cafeboard.post;

import cafeboard.BaseEntity;
import cafeboard.board.Board;
import cafeboard.comment.Comment;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Board board;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    private int viewCount;

    protected Post() {
    }

    public Post(String title, String content, Board board) {
        this.title = title;
        this.content = content;
        this.board = board;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public Board getBoard() {
        return board;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void countView() {
        viewCount = viewCount+1;
    }
}
