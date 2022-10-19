package com.abbasaskari.test.jitpay.userapi.service.business;

import com.abbasaskari.test.jitpay.userapi.common.exception.BaseException;
import com.abbasaskari.test.jitpay.userapi.common.log.LogUtil;
import com.abbasaskari.test.jitpay.userapi.domain.entity.User;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserRequestModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserResponseModel;
import com.abbasaskari.test.jitpay.userapi.service.dao.UserDao;
import com.abbasaskari.test.jitpay.userapi.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * Implementation of UserBusinessService interface that represent User services
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class UserBusinessServiceImpl implements UserBusinessService {
    private final UserDao userDao;

    private static final Logger LOG = LogUtil.getDefaultLogger(UserBusinessServiceImpl.class);

    @Autowired
    public UserBusinessServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserResponseModel insert(UserRequestModel userRequestModel) throws BaseException {
        LOG.info("insert start");

        User userEmail = userDao.findByEmail(userRequestModel.getEmail());

        if (userEmail != null)
            throw new BaseException("Email is duplicate.");

        String userId = "";
        while (userId.equals(""))
        {
            userId = generateUserId();
            if (userDao.findByUserId(userId) != null)
                userId = "";
        }

        UserMapper userMapper = new UserMapper();
        User user = userMapper.userRequestModelToUser(userRequestModel);
        user.setUserId(userId);
        user.setCreatedOn(new Date());

        user = userDao.save(user);

        return userMapper.userToUserResponseModel(user);
    }

    @Override
    public void update(String userId, UserRequestModel userRequestModel) throws BaseException{
        LOG.info("update start");

        User user = userDao.findByUserId(userId);

        if (user == null)
            throw new BaseException("User not found.");

        if (!user.getEmail().equals(userRequestModel.getEmail()))
        {
            User userEmail = userDao.findByEmail(userRequestModel.getEmail());

            if (userEmail != null)
                throw new BaseException("Email is duplicate.");
        }
        UserMapper userMapper = new UserMapper();
        user = userMapper.userRequestModelToUser(userRequestModel, user);

        userDao.save(user);
    }

    private String generateUserId()
    {
        return UUID.randomUUID().toString();
    }
}
