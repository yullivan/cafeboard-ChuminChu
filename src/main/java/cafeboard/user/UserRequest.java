package cafeboard.user;

public record UserRequest(
        String userName,
        String userInfoId,
        String userPassword,
        String userEmail
) {
}
