package com.example.restfulwebservice.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
@ApiModel(description = "All details about user.")
public class Usertable {
    @Id @GeneratedValue
    @Column(name = "USERTABLE_ID")
    private Integer id;

    @OneToMany(mappedBy = "user")
    List<Post> posts= new ArrayList<>();

    @Size(min = 2, message = "이름은 2글자 이상 입력해 주세요.")
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요")
    private String name;

    @Past
    @ApiModelProperty(notes = "사용자의 등록일을 입력해 주세요")
    private Date joinDate;

    @ApiModelProperty(notes = "사용자의 패스워드를 입력해 주세요")
    private String password;

    @ApiModelProperty(notes = "사용자 주민등록번호 입력해 주세요")
    private String ssn;

    public Usertable(String name, Date joinDate, String password, String ssn) {
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }
}
