package com.ssafy.deathnotelive.service;

import com.ssafy.deathnotelive.common.exception.AlreadyRegisteredUserException;
import com.ssafy.deathnotelive.common.exception.UserNotFoundException;
import com.ssafy.deathnotelive.config.jwt.JwtUtils;
import com.ssafy.deathnotelive.dto.UserDto;
import com.ssafy.deathnotelive.entity.User;
import com.ssafy.deathnotelive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

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

        if (userRepository.findByUserId(userId) != null) {
            throw new AlreadyRegisteredUserException();
        }
        return userRepository.save(User.builder()
                .userId(userId)
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority("ROLE_USER")
                .build());
    }

    public User findByUseId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public String logIn(String userId, String password) throws Exception{
        User user = userRepository.findByUserId(userId);
        if(user!= null){
            if(passwordEncoder.matches(password,user.getPassword())){
                return JwtUtils.createToken(user);
            }
        }
        throw new UserNotFoundException();
    }
}
