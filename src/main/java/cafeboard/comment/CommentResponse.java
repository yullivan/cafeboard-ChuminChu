package cafeboard.comment;

public record CommentResponse(
        String content,
        String name,
        Long id
) {
}
