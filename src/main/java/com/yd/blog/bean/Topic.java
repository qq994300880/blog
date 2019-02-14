package com.yd.blog.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author YoungDream
 * @date 2019/2/9 22:00
 */
@Data
public class Topic implements Serializable {
    private Integer id;
    private String name;
    private String picSrc;
}
