package cafeboard.post;

public record PostCommentResponse(
        Long id,
        String title,
        String content,
        int viewCount
) {
}
