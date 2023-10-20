package com.example.restfulwebservice.controller;

import com.example.restfulwebservice.domain.User;
import com.example.restfulwebservice.domain.UserV2;
import com.example.restfulwebservice.exception.UserNotFoundException;
import com.example.restfulwebservice.service.UserService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Version 관리
 * URI Versionong(V1,V2), Request Parameter versioning(V3) : 일반 브라우저에서 실행 가능
 * Headers versioning(V4) : 일반 브라우저에서 실행 불가 => Postman사용
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Controller에서  Filter사용해서  JSON을 제어하는 방법
     * "UserInfo" : Dto or Entity  @JsonFilter("UserInfo")
     * filterOutAllExcept : 보여줄 value
     */
    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){
        List<User> users = userService.findAll();

        return UtilMethod.getMappingJacksonValue(users,"UserInfo","id","name","joinDate");
    }


    @GetMapping("/v1/users/{id}")
    public MappingJacksonValue findOneUserV1(@PathVariable("id")Integer id){
        User user = userService.findOne(id);
        if(user == null){
            throw new UserNotFoundException("user not found");
        }

        return UtilMethod.getMappingJacksonValueUser(user,"UserInfo","id","name","joinDate","ssn");
    }

    @GetMapping("/v2/users/{id}")
    public MappingJacksonValue findOneUserV2(@PathVariable("id")Integer id){
        User user = userService.findOne(id);
        if(user == null){
            throw new UserNotFoundException("user not found");
        }
        //User -> User2
        UserV2 userV2 = getUserV2(user);

        return UtilMethod.getMappingJacksonValueUser(userV2,"UserInfoV2","id","name","joinDate","ssn","grade");
    }


    /**
     * Request Parameter를 통한 Version 관리
     * http://localhost:8080/admin/users/1/?version=3
     */
    @GetMapping(value = "/users/{id}/",params = "version=3")
    public MappingJacksonValue findOneUserV3(@PathVariable("id")Integer id){
        User user = userService.findOne(id);
        if(user == null){
            throw new UserNotFoundException("user not found");
        }
        //User -> User2
        UserV2 userV2 = getUserV2(user);
        return UtilMethod.getMappingJacksonValueUser(userV2,"UserInfoV2","id","name","joinDate","grade");
    }

    /**
     * Headers를 통한 Version 관리
     * ~/admin/users/1
     * PostMan에서 Key = X-API-VERSION, Value = 4
     */
    @GetMapping(value = "/users/{id}",headers = "X-API-VERSION=4")
    public MappingJacksonValue findOneUserV4(@PathVariable("id")Integer id){
        User user = userService.findOne(id);
        if(user == null){
            throw new UserNotFoundException("user not found");
        }
        //User -> User2
        UserV2 userV2 = getUserV2(user);

        return UtilMethod.getMappingJacksonValueUser(userV2,"UserInfoV2","id","name","joinDate","grade");
    }

    private static UserV2 getUserV2(User user) {
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2);
        userV2.setGrade("VIP");
        return userV2;
    }


}
