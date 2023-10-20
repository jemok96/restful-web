package com.example.restfulwebservice.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 보안에 민감한 data값을 그대로 JSON으로 뿌리면 문제 가능성 O
 *  @JsonIgnore : 개별
 *  @JsonIgnoreProperties :일괄
 *  HATEOAS 사용할 경우 UserController에서 @JsonFilter Exception
 */
@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value = {"password","ssn"}) //개별 @JsonIgnore를 한번에 처리
@JsonFilter("UserInfo")
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
