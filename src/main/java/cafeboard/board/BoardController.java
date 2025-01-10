package cafeboard.board;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/boards")
    public BoardResponse createBoard(@RequestBody BoardRequest boardRequest){
        return boardService.createBoard(boardRequest);
    }
}
