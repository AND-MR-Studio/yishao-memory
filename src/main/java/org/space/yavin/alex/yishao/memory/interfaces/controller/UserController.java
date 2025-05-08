package org.space.yavin.alex.yishao.memory.interfaces.controller;

import lombok.RequiredArgsConstructor;
import org.space.yavin.alex.yishao.memory.domains.userinfo.dto.UserDto;
import org.space.yavin.alex.yishao.memory.domains.userinfo.entity.UserInfo;
import org.space.yavin.alex.yishao.memory.domains.userinfo.repository.UserInfoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserInfoRepository userRepository;

    @PostMapping
    public ResponseEntity<UserInfo> createUser(@RequestBody UserDto userDto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userDto.getOpenId());  // 由于UserDto中使用openId字段
        userInfo.setNickname(userDto.getNickName());
        userInfo.setAvatarUrl(userDto.getAvatarUrl());
        return ResponseEntity.ok(userRepository.save(userInfo));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfo> getUserByOpenId(@PathVariable String userId) {
        return userRepository.findByUserId(userId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}