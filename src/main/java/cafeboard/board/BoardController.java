package cafeboard.board;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/boards")
    public BoardResponse createBoard(@RequestBody BoardRequest boardRequest) {
        return boardService.save(boardRequest);
    }

    @GetMapping("/boards")
    public List<BoardResponse> getBoard() {
        return boardService.findAll();
    }
}
