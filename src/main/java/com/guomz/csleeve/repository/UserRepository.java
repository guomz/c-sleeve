package com.guomz.csleeve.repository;

import com.guomz.csleeve.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByOpenid(String openId);

    User findUserById(Long id);
}
