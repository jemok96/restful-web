package com.example.restfulwebservice.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value = {"password","ssn"}) //개별 @JsonIgnore를 한번에 처리
//@JsonFilter("UserInfo")
public class User {
    private Integer id;

    @Size(min = 2,message = "이름은 2글자 이상 입력해 주세요.")
    private String name;
    @Past
    private Date joinDate;
//    @JsonIgnore
    private String password;
//    @JsonIgnore
    private String ssn;

    public User() {
    }
}
