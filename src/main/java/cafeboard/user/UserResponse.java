package cafeboard.user;

public record UserResponse(
        Long id,
        String userName,
        String userId,
        String userPassword,
        String userEmail
) {

}
