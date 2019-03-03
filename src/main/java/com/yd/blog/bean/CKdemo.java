package com.yd.blog.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author YoungDream
 * @date 2019/2/28 14:08
 */
@ToString
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CKdemo {
    private Integer uploaded;
    private String url;
}
