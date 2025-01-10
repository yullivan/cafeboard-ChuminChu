package cafeboard.post;

public record PostResponse(
        Long id,
        String title,
        String content
) {
}
