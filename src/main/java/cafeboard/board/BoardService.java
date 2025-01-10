package cafeboard.board;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardResponse save(BoardRequest boardRequest) {
        Board board = boardRepository.save(new Board(boardRequest.boardName()));
        return new BoardResponse(board.getBoardName(), board.getId());
    }

    public List<BoardResponse> findAll() {
        return boardRepository.findAll()
                .stream()
                .map(board -> new BoardResponse(
                        board.getBoardName(),
                        board.getId()
        )).toList();
    }
}
