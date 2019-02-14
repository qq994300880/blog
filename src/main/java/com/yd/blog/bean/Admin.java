package com.yd.blog.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author YoungDream
 * @date 2019/2/12 3:43
 */
@Data
public class Admin implements Serializable {
    private String username;
    private String password;
}
