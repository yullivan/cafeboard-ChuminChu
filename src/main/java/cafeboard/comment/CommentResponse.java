package cafeboard.comment;

public record CommentResponse(
        String content,
        String name,
        Long id
) {
    public CommentResponse(Comment comment) {
        this(comment.getContent(), comment.getWriter(), comment.getId());
    }
}
