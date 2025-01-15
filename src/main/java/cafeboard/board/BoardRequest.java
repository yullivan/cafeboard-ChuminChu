package cafeboard.board;

import jakarta.validation.constraints.Size;

public record BoardRequest(
        @Size(min = 1, max = 15) String boardName
) {
}
