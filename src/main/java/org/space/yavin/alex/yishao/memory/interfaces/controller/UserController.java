package org.space.yavin.alex.yishao.memory.interfaces.controller;

import lombok.RequiredArgsConstructor;
import org.space.yavin.alex.yishao.memory.domains.userinfo.dto.UserDto;
import org.space.yavin.alex.yishao.memory.domains.userinfo.entity.User;
import org.space.yavin.alex.yishao.memory.domains.userinfo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping("/{openId}")
    public ResponseEntity<User> getUserByOpenId(@PathVariable String openId) {
        return userService.getUserByOpenId(openId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}