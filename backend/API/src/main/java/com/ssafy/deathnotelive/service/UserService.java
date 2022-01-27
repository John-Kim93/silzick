package com.ssafy.deathnotelive.service;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.ssafy.deathnotelive.config.jwt.JwtUtils;
import com.ssafy.deathnotelive.dto.UserDto;
import com.ssafy.deathnotelive.entity.User;
import com.ssafy.deathnotelive.error.exception.EmailDuplicationException;
import com.ssafy.deathnotelive.error.exception.UserIdDuplicationException;
import com.ssafy.deathnotelive.error.exception.UserNotFoundException;
import com.ssafy.deathnotelive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 등록
     *
     * @return 유저 권한을 가지고 있는 유저
     */
    public User signup(UserDto.UserRegist registerInfo) {
        String userId = registerInfo.getUserId();
        String password = registerInfo.getPassword();
        String email = registerInfo.getEmail();

        if (userRepository.getByUserId(userId) != null) {
            throw new UserIdDuplicationException("ERROR");
        } else if (userRepository.getByEmail(email) != null) {
            throw new EmailDuplicationException("ERROR");
        }
        return userRepository.save(User.builder()
                .userId(userId)
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority("ROLE_USER")
                .build());
    }


    public User getByUserId(String userId) {
        User user = userRepository.getByUserId(userId);
        if (user == null) throw new UserNotFoundException("Error");
        else {
            return user;
        }
    }

    public String logIn(String userId, String password) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("Error"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return JwtUtils.createToken(user);
        }
        throw new UserNotFoundException("Error");
    }

    public UserDto.UserInfo getUserInfo(String userId) {
        User user = userRepository.getByUserId(userId);
        return UserDto.UserInfo.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .build();
    }

    public void modifyUser(UserDto.ModifyUserInfo modifyUserInfo) {
        User user = userRepository.findByUserId(modifyUserInfo.getUserId()).orElseThrow(() -> new UserNotFoundException("EORROR"));
        user.setUserId(modifyUserInfo.getUserId());
        user.setEmail(modifyUserInfo.getEmail());
        userRepository.save(user);
    }

    public void deleteUser(String userId) {
        User user = userRepository.getByUserId(userId);
        userRepository.delete(user);
    }
}
