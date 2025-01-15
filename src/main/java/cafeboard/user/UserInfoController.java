package cafeboard.user;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {

    private final UserService userService;

    public UserInfoController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/infos")
    public UserResponse save(@Valid @RequestBody UserRequest userRequest){
        return userService.save(userRequest);
    }

    //사용자의 프로필 - 이름만 보이는 상태
    @GetMapping("/users")
    public UserProfileResponse findById(@RequestBody UserProfileRequest userProfileRequest){
        return userService.findByProfile(userProfileRequest);
    }

    //사용자의 프로필 조회 - 아이디/비번
    @GetMapping("/users/profiles")
    public UserResponse findByUserProfile(@RequestParam Long userId){
        return userService.findByUserProfileDetail(userId);
    }

    @PutMapping("/users/{userId}")
    public UserResponse update(@PathVariable Long userId, @RequestBody UserRequest userRequest){
        return userService.update(userId, userRequest);
    }

    @DeleteMapping("/users/{userId}")
    public void delete(@PathVariable Long userId){
        userService.deleteById(userId);
    }
}
