package cafeboard.post;

public record PostRequest(
        String content,
        String title,
        Long boardId

) {
}
