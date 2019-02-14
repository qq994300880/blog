package com.yd.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yd.blog.bean.Admin;
import com.yd.blog.bean.Blog;
import com.yd.blog.bean.Topic;
import com.yd.blog.mapper.BlogMapper;
import com.yd.blog.mapper.TopicMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @Author YoungDream
 * @date 2019/2/11 15:36
 */
@Slf4j
@Controller
public class ManagerController {

    @Value("${blog.root.username}")
    private String rootUsername;

    @Value("${blog.root.password}")
    private String rootPassword;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private BlogMapper blogMapper;

    //主页
    @GetMapping("manager/index")
    public String home() {
        return "manager_home";
    }

    //简易的登录登出
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:/login";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

//    @PostMapping("validate")
//    public boolean validateUsername(@RequestParam("name") String name) {
//        if (!StringUtils.isEmpty(name)) {
//            log.info(name);
////            String name = StringUtils.deleteAny(name, " ");
//        }
//        if (name.equals("root")) {
//            return true;
//        }
//        return false;
//    }

    @PostMapping("login")
    public String login(Admin admin, HttpSession session, Model model) {
        log.info(admin.toString());
        if (!StringUtils.isEmpty(admin.getUsername()) && !StringUtils.isEmpty(admin.getPassword())) {
            String username = admin.getUsername();
            String password = admin.getPassword();
            username = StringUtils.deleteAny(username, " ");
            password = StringUtils.deleteAny(password, " ");
            log.info(username);
            log.info(password);
            if (!username.equals(rootUsername)) {
                model.addAttribute("oldUsername", username);
                model.addAttribute("usernameMsg", true);
                return "login";
            }
            if (!password.equals(rootPassword)) {
                model.addAttribute("oldUsername", username);
                model.addAttribute("passwordMsg", true);
                return "login";
            }
            session.setAttribute("admin", username);
            return "redirect:/manager/index";
        }
        return "login";
    }

    //文章管理
    @GetMapping("manager/article")
    public String intoAddArticle(Model model) {
        model.addAttribute("topics", topicMapper.getAllTopic());
        return "addArticle";
    }

    @PostMapping("manager/article")
    public String saveArticle(Blog blog) {
        //不知为何，使用PUT和DELETE不行，暂时使用这种方式
//        log.info("进入方法:" + blog.toString());
        if (null != blog.getId() && 0 < blog.getId()) {
//            log.info("执行了更新方法:" + blog.toString());
            blogMapper.updateBlog(blog);
        } else {
            blog.setReadingCount(0);
//            log.info("执行了新增方法:" + blog.toString());
            blogMapper.addBlog(blog);
        }
        return "redirect:/manager/artics";
    }

    @GetMapping("manager/artics")
    public String getAll(Model model) {
        model.addAttribute("topics", topicMapper.getAllTopic());
//        使用分页插件,目前还没有使用
//        PageHelper.startPage(1,10);
        List<Blog> blogs = blogMapper.getAllBlogNoHaveContent();
//        PageInfo<Blog> pageInfo=new PageInfo<>(blogs);
        model.addAttribute("blogs", blogs);
        return "articleList";
    }

    @GetMapping("manager/article/{id}")
    public String changeArt(Model model, @PathVariable("id") Integer id) {
        if (null != id && 0 < id) {
            model.addAttribute("thisBlog", blogMapper.getBlogById(id));
        }
        return "forward:/manager/article";
    }

    @PostMapping("manager/article/{id}")
    public String deleteArticle(@PathVariable("id") Integer id) {
        blogMapper.deleteBlogById(id);
        return "redirect:/manager/artics";
    }

    //主题管理
    @GetMapping("manager/topic")
    public String intoAddTopic() {
        return "addTopic";
    }

    @PostMapping("manager/topic")
    public String saveTopic(Topic topic) {
        if (null != topic.getId() && 0 < topic.getId()) {
            topicMapper.updateTopic(topic);
        } else {
            topicMapper.addTopic(topic);
        }
        return "redirect:/manager/topis";
    }

    @GetMapping("manager/topis")
    public String getAllTopic(Model model) {
        model.addAttribute("topics", topicMapper.getAllTopic());
        return "topicList";
    }

    @GetMapping("manager/topic/{id}")
    public String updateTopic(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("thisTopic", topicMapper.getTopicById(id));
        return "addTopic";
    }

    @PostMapping("manager/topic/{id}")
    public String deleteTopic(@PathVariable("id") Integer id) {
        //判断当前主题下有没有文章,如果有先不进行删除，因为外键的存在，数据库会报错
        List<Blog> blogs = blogMapper.getBlogsByTopicId(id);
//        log.info(blogs.toString());
        if (blogs.isEmpty()) {
            topicMapper.deleteTopicById(id);
        }
        return "redirect:/manager/topis";
    }

}
