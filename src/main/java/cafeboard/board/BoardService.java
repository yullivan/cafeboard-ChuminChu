package cafeboard.board;

import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardResponse createBoard(BoardRequest boardRequest) {
        Board board = boardRepository.save(new Board(boardRequest.boardName()));
        return new BoardResponse(board.getBoardName(), board.getId());
    }
}
