package cafeboard.board;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/boards")
    public BoardResponse save(@Valid @RequestBody BoardRequest boardRequest) {
        return boardService.save(boardRequest);
    }

    @GetMapping("/boards")
    public List<BoardResponse> findAll() {
        return boardService.findAll();
    }

    @PutMapping("/boards/{boardId}")
    public BoardResponse update(@PathVariable Long boardId, @Valid @RequestBody BoardRequest boardRequest){
        return boardService.update(boardId, boardRequest);
    }

    @DeleteMapping("/boards/{boardId}")
    public void deleteById(@PathVariable Long boardId){
        boardService.deleteById(boardId);
    }

}
