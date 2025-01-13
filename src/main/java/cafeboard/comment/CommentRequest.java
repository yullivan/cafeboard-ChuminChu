package cafeboard.comment;

public record CommentRequest(
        String content,
        String name,
        Long postId
) {
}
