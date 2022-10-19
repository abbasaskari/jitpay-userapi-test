package com.abbasaskari.test.jitpay.userapi.api.webservice;

import com.abbasaskari.test.jitpay.userapi.common.exception.BaseException;
import com.abbasaskari.test.jitpay.userapi.common.log.LogUtil;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserRequestModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserResponseModel;
import com.abbasaskari.test.jitpay.userapi.service.business.UserBusinessService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class is a controller for user Services
 */

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger LOG = LogUtil.getDefaultLogger(UserController.class);

    private final UserBusinessService userBusinessService;

    /**
     * Constructor
     * @param userBusinessService
     */
    @Autowired
    public UserController(UserBusinessService userBusinessService) {
        this.userBusinessService = userBusinessService;
    }

    /**
     * Insert user information
     * @param userRequestModel
     * @return
     * @throws BaseException
     */
    @PostMapping(value = "/insert")
    public ResponseEntity<UserResponseModel> insert(@Valid @RequestBody UserRequestModel userRequestModel) throws BaseException {
        LOG.info("insert start");
        UserResponseModel userResponseModel = userBusinessService.insert(userRequestModel);
        return new ResponseEntity<>(userResponseModel, HttpStatus.OK);
    }

    /**
     * Update user information
     * @param userId
     * @param userRequestModel
     * @throws BaseException
     */
    @PutMapping(value = "/update")
    public void update(@RequestParam String userId, @Valid @RequestBody UserRequestModel userRequestModel) throws BaseException {
        LOG.info("update start");
        userBusinessService.update(userId, userRequestModel);
    }

    /**
     * It handle business exception
     * @param exception
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<String> handle(BaseException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * It handle validation exception
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}