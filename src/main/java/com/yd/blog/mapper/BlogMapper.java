package com.yd.blog.mapper;

import com.yd.blog.bean.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author YoungDream
 * @date 2019/2/10 1:09
 */
//@Mapper
public interface BlogMapper {

    @Insert("INSERT INTO blog(title, info, content, rel_time,reading_count,topic_id) " +
            "VALUES (#{title},#{info},#{content},NOW(),0,#{topicId})")
    int addBlog(Blog blog);

    @Delete("DELETE FROM blog WHERE id=#{id}")
    int deleteBlogById(Integer id);

    @Update("UPDATE blog SET title=#{title},info=#{info},content=#{content},topic_id=#{topicId} WHERE id=#{id}")
    int updateBlog(Blog blog);

    @Select("SELECT * FROM blog WHERE id=#{id}")
    Blog getBlogById(Integer id);

    @Select("SELECT id,title,info,rel_time,topic_id FROM blog ORDER BY rel_time DESC ")
    List<Blog> getAllBlog();

    @Select("SELECT id,title,info,rel_time,reading_count,topic_id FROM blog ORDER BY rel_time DESC ")
    List<Blog> getAllBlogNoHaveContent();

    @Select("SELECT id,title,info,rel_time FROM blog WHERE topic_id=#{id} ORDER BY rel_time DESC")
    List<Blog> getBlogsByTopicId(Integer id);

    @Select("SELECT id,title,info,rel_time FROM blog ORDER BY rel_time DESC LIMIT 5")
    List<Blog> getNewBlog();

    @Select("SELECT id,title FROM blog ORDER BY reading_count DESC LIMIT 5")
    List<Blog> getHighestClick();

    //阅读加一
    @Update("UPDATE blog SET reading_count=reading_count+1 WHERE id=#{id}")
    int updateBlogUpOne(Integer id);
}
