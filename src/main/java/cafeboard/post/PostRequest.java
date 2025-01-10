package cafeboard.post;

import jakarta.validation.constraints.Size;

public record PostRequest(
        @Size(min = 1) String content,
        @Size(min = 1, max = 100) String title,
        Long boardId

) {
}