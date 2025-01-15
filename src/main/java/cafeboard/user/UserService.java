package cafeboard.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse save(UserRequest userRequest) {
        UserInfo user = userRepository.save(new UserInfo(
                userRequest.userName(),
                userRequest.userInfoId(),
                userRequest.userPassword(),
                userRequest.userEmail()));

        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getUserId(),
                user.getPassWord(),
                user.getEmail());
    }

    public UserResponse findByUserProfileDetail(Long userId) {
        UserInfo user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("찾는 사용자가 없습니다."));
        return new UserResponse(
                userId,
                user.getUserName(),
                user.getUserId(),
                user.getPassWord(),
                user.getEmail()
        );
    }

    public UserProfileResponse findByProfile(UserProfileRequest userProfileRequest) {
        userRepository.findById(userProfileRequest.id())
                .orElseThrow(() -> new NoSuchElementException("찾는 사용자가 없습니다."));
        return new UserProfileResponse(userProfileRequest.id(), userProfileRequest.userName());
    }

    @Transactional
    public UserResponse update(Long userId, UserRequest userRequest) {
        UserInfo user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("찾는 사용자가 없습니다"));
        user.setUserId(userRequest.userInfoId());
        user.setUserName(userRequest.userName());
        user.setPassWord(userRequest.userPassword());
        user.setEmail(userRequest.userEmail());
        return new UserResponse(
                userId,
                user.getUserName(),
                user.getUserId(),
                user.getPassWord(),
                user.getEmail());
    }

    public void deleteById(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(()->new NoSuchElementException("찾는 사용자가 없습니다."));
        userRepository.deleteById(userId);
    }
}
