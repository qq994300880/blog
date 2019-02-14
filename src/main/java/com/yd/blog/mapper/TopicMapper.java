package com.yd.blog.mapper;

import com.yd.blog.bean.Topic;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author YoungDream
 * @date 2019/2/9 22:42
 */
//一个操作数据库的Mapper
//@Mapper
public interface TopicMapper {

    int addTopic(Topic topic);

    int deleteTopicById(Integer id);

    int updateTopic(Topic topic);

    Topic getTopicById(Integer id);

    List<Topic> getAllTopic();

}
