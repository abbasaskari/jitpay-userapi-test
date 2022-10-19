package com.abbasaskari.test.jitpay.userapi.service.dao;

import com.abbasaskari.test.jitpay.userapi.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * A repository for managing User entity
 */

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    /**
     * @param userId
     * @return
     */
    @Query("SELECT user FROM User user WHERE user.userId = :userId")
    User findByUserId(@Param("userId") String userId);

    /**
     * @param email
     * @return
     */
    @Query("SELECT user FROM User user WHERE user.email = :email")
    User findByEmail(@Param("email") String email);

}
