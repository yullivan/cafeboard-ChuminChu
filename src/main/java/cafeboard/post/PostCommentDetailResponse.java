package cafeboard.post;

import cafeboard.comment.Comment;
import cafeboard.comment.CommentResponse;

import java.util.List;

public record PostCommentDetailResponse(
        Long id,
        String title,
        String content,
        List<CommentResponse> comments
) {
}
