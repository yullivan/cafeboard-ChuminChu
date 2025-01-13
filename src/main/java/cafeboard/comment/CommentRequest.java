package cafeboard.comment;

import jakarta.validation.constraints.Size;

public record CommentRequest(
        @Size(min = 1, max = 300) String content,
        @Size(min = 1, max = 10) String name,
        Long postId
) {
}
