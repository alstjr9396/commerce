package me.minseok.member_service.service;

import me.minseok.member_service.entity.UserEntity;
import me.minseok.member_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserEntity registerUser(String loginId, String userName) {
        UserEntity user = new UserEntity(loginId, userName);
        return userRepository.save(user);
    }

    public UserEntity modifyUser(Long userId, String userName) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        user.setUserName(userName);
        return userRepository.save(user);
    }

    public UserEntity getUser(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow();
    }
}
