package cafeboard.comment;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments")
    public CommentResponse save(@Valid @RequestBody CommentRequest commentRequest){
        return commentService.save(commentRequest);
    }

    @PutMapping("/comments/{commentId}")
    public CommentResponse update(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest){
        return commentService.update(commentId,commentRequest);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteById(@PathVariable Long commentId){
        commentService.deleteById(commentId);
    }
}
