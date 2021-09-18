package com.manager.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private int userId;
    private String userName;
    private Integer gender;
    private Long phone;
    private String email;
    private Date createTime;
    private Date updateTime;


//    public User() {
//        super();
//    }
//
//    public User(Long userId,String userName, Integer gender, Long phone, String email, Date createTime,Date updateTime) {
//        this.userId = userId;
//        this.userName = userName;
//        this.gender = gender;
//        this.phone = phone;
//        this.email = email;
//        this.createTime = createTime;
//        this.updateTime=updateTime;
//    }
}
