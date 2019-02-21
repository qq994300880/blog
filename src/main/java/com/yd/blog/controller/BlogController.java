package com.yd.blog.controller;


import com.yd.blog.mapper.BlogMapper;
import com.yd.blog.mapper.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @Author YoungDream
 * @date 2019/2/8 21:47
 */
@Controller
public class BlogController {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private BlogMapper blogMapper;

    @GetMapping("/index")
    public String home(Model model) {
        getNecessaryInfo(model);
        setBlogList(model, null);
        return "blogs";
    }

    @GetMapping("/index/{id}")
    public String topic(@PathVariable("id") Integer id, Model model) {
        getNecessaryInfo(model);
        setBlogList(model, id);
        return "blogs";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id") Integer id, Model model) {
        getNecessaryInfo(model);
        model.addAttribute("thisBlog", blogMapper.getBlogById(id));
        blogMapper.updateBlogUpOne(id);
        return "blog";
    }

    private void getNecessaryInfo(@NotNull Model model) {
        model.addAttribute("topics", topicMapper.getAllTopic());
        model.addAttribute("newBlogs", blogMapper.getNewBlog());
        model.addAttribute("hcBlogs", blogMapper.getHighestClick());
    }

    private void setBlogList(@NotNull Model model, Integer topicId) {
        if (topicId == null) {
            model.addAttribute("notice", "ALL");
            model.addAttribute("blogList", blogMapper.getAllBlog());
        } else {
            model.addAttribute("notice", topicMapper.getTopicById(topicId).getName());
            model.addAttribute("blogList", blogMapper.getBlogsByTopicId(topicId));
        }
    }

    //测试PUT和Delete
    @ResponseBody
    @PutMapping("put")
    public Object put() {
        return blogMapper.getAllBlog();
    }

    @ResponseBody
    @DeleteMapping("delete")
    public String delete() {
        return "测试DELETE请求成功...";
    }

}
