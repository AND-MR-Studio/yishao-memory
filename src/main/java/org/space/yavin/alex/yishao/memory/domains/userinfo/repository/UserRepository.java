package org.space.yavin.alex.yishao.memory.domains.userinfo.repository;

import org.space.yavin.alex.yishao.memory.domains.userinfo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByOpenId(String openId);

}