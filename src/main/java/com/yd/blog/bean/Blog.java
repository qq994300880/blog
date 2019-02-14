package com.yd.blog.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author YoungDream
 * @date 2019/2/9 21:33
 */
@Data
public class Blog implements Serializable {
    private Integer id;
    private String title;
    private String info;
    private String content;
    private Date relTime;
    private Integer readingCount;
    private Integer topicId;
}
