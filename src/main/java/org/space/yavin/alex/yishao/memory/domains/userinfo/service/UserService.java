package org.space.yavin.alex.yishao.memory.domains.userinfo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.space.yavin.alex.yishao.memory.domains.userinfo.dto.UserDto;
import org.space.yavin.alex.yishao.memory.domains.userinfo.entity.User;
import org.space.yavin.alex.yishao.memory.domains.userinfo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(UserDto userDto) {
        User user = new User(null, userDto.getOpenId(), userDto.getNickName(), userDto.getAvatarUrl(),
                null, null, null);
        return userRepository.save(user);
    }

    @Transactional()
    public Optional<User> getUserByOpenId(String openId) {
        return userRepository.findByOpenId(openId);
    }

}