package cafeboard.board;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String boardName;

    public Board(String boardName) {
        this.boardName = boardName;
    }

    public Long getId() {
        return id;
    }

    public String getBoardName() {
        return boardName;
    }
}
