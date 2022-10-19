package com.abbasaskari.test.jitpay.userapi.service.dao;

import com.abbasaskari.test.jitpay.userapi.domain.entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * A repository for managing Location entity
 */

@Repository
public interface LocationDao extends JpaRepository<UserLocation, Long>{
    /**
     * Find user and latest Location by userId
     * @param userId
     * @return
     */
    @Query("SELECT userLocation FROM UserLocation userLocation WHERE userLocation.user.userId = :userId AND userLocation.createdOn IN " +
            "(SELECT MAX(userLocation.createdOn) FROM UserLocation userLocation WHERE userLocation.user.userId = :userId)")
    UserLocation findLatestLocationByUserId(@Param("userId") String userId);

    /**
     * Find user location by userId and date time range
     * @param userId
     * @param start
     * @param end
     * @return
     */
    @Query("SELECT userLocation FROM UserLocation userLocation WHERE userLocation.user.userId = :userId AND userLocation.createdOn BETWEEN :start AND :end")
    List<UserLocation> findByUserIdAndDateTimeRange(@Param("userId") String userId, @Param("start") Date start, @Param("end") Date end);
}
