package cafeboard.board;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Transactional
    public BoardResponse update(Long boardId, BoardRequest boardRequest) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않은 게시판입니다." + boardId));
        board.setBoardName(boardRequest.boardName());
        return new BoardResponse(board.getBoardName(), board.getId());
    }

    public void deleteById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않은 게시판입니다." + boardId));
        boardRepository.delete(board);
    }
}
