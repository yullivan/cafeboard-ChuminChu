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
    public CommentResponse createComment(@Valid @RequestBody CommentRequest commentRequest){
        return commentService.create(commentRequest);
    }

    @PutMapping("/comments/{commentId}")
    public CommentResponse putComment(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest){
        return commentService.put(commentId,commentRequest);
    }

    @DeleteMapping("/comments/{commentId}")
    public CommentResponse deleteComment(@PathVariable Long commentId){
        return commentService.delete(commentId);
    }
}
