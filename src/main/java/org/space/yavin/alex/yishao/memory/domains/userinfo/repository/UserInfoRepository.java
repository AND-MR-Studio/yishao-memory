package org.space.yavin.alex.yishao.memory.domains.userinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.space.yavin.alex.yishao.memory.domains.userinfo.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    
    /**
     * 根据用户ID查找用户信息
     * @param userId 用户ID
     * @return 用户信息（可能不存在）
     */
    Optional<UserInfo> findByUserId(String userId);

}