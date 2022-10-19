package com.abbasaskari.test.jitpay.userapi.api.webservice;

import com.abbasaskari.test.jitpay.userapi.common.exception.BaseException;
import com.abbasaskari.test.jitpay.userapi.common.log.LogUtil;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserLocationRequestModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserLocationResponseModel;
import com.abbasaskari.test.jitpay.userapi.domain.model.UserLocationsResponseModel;
import com.abbasaskari.test.jitpay.userapi.service.business.LocationBusinessService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Abbas Askari
 * on 15/10/2022
 *
 * This class is a controller for Location Services
 */

@RestController
@RequestMapping("/api/location")
public class LocationController {
    private static final Logger LOG = LogUtil.getDefaultLogger(LocationController.class);

    private final LocationBusinessService locationBusinessService;

    /**
     * Constructor
     * @param locationBusinessService
     */
    @Autowired
    public LocationController(LocationBusinessService locationBusinessService) {
        this.locationBusinessService = locationBusinessService;
    }

    /**
     * Insert locations for user
     * @param userLocationRequestModel
     * @throws BaseException
     */
    @PostMapping(value = "/insert")
    public void insert(@Valid @RequestBody UserLocationRequestModel userLocationRequestModel) throws BaseException
    {
        LOG.info("insert start");
        locationBusinessService.insert(userLocationRequestModel);
    }

    /**
     * It responds latest location for user by getting userId
     * @param userId
     * @return UserLocationResponseModel
     * @throws BaseException
     */
    @GetMapping(value = "/get-last-location-by-user-id")
    public ResponseEntity<UserLocationResponseModel> getLastLocationByUserId(@RequestParam String userId) throws BaseException
    {
        LOG.info("getLastLocationByUserId start");
        UserLocationResponseModel userLocationResponseModel = locationBusinessService.getLastLocationByUserId(userId);
        return new ResponseEntity<>(userLocationResponseModel, HttpStatus.OK);
    }

    /**
     * It responds user locations between two time by getting userId and time range
     * start and end time format must be : "yyyy-MM-dd'T'HH:mm:ss.SSS"
     * @param userId
     * @param startTime
     * @param endTime
     * @return UserLocationsResponseModel
     * @throws BaseException
     */
    @GetMapping(value = "/get-locations-by-date-time-range")
    public ResponseEntity<UserLocationsResponseModel> getLocationsByDateTimeRange(@RequestParam String userId, @RequestParam String startTime, @RequestParam String endTime) throws BaseException, ParseException {
        LOG.info("getLocationsByDateTimeRange start");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");

        UserLocationsResponseModel userLocationsResponseModel = locationBusinessService.getLocationsByDateTimeRange(userId, simpleDateFormat.parse(startTime), simpleDateFormat.parse(endTime));
        return new ResponseEntity<>(userLocationsResponseModel, HttpStatus.OK);
    }

    /**
     * It handle business exception
     * @param exception
     * @return ErrorMessage
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<String> handle(BaseException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * It handle validation exception
     * @param exception
     * @return First ErrorMessage
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
