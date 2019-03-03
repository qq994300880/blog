package com.yd.blog.controller;

import com.yd.blog.bean.Admin;
import com.yd.blog.bean.Blog;
import com.yd.blog.bean.CKdemo;
import com.yd.blog.bean.Topic;
import com.yd.blog.mapper.BlogMapper;
import com.yd.blog.mapper.TopicMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        //测试session过期时间
//        testSessionTimeout(session);
        return "manager_home";
    }

    //测试session过期时间
//    private void testSessionTimeout(HttpSession session) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        long creationTime = session.getCreationTime();
//        Date date = new Date(creationTime);
//        log.info("creationTime:" + simpleDateFormat.format(date));
//        long lastAccessedTime = session.getLastAccessedTime();
//        date = new Date(lastAccessedTime);
//        log.info("lastAccessedTime:" + simpleDateFormat.format(date));
//        int i = session.getMaxInactiveInterval();
//        log.info("MaxInactiveInterval:" + i);
//    }


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
//        log.info(admin.toString());
        if (!StringUtils.isEmpty(admin.getUsername()) && !StringUtils.isEmpty(admin.getPassword())) {
            String username = admin.getUsername();
            String password = admin.getPassword();
//            log.info(username);
//            log.info(password);
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
            session.setAttribute("admin", admin);
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
//        PageHelper.startPage(1, 10);
        List<Blog> blogs = blogMapper.getAllBlogNoHaveContent();
//        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
//        log.info(pageInfo.toString());
        model.addAttribute("blogs", blogs);
        return "articleList";
    }

//    @ResponseBody
//    @GetMapping("manager/artics/json")
//    public PageInfo<Blog> get() {
//        //测试分页插件,目前还没有使用
//        PageHelper.startPage(1, 10);
//        List<Blog> blogs = blogMapper.getAllBlogNoHaveContent();
//        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
//        return pageInfo;
//    }

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
        return "forward:/manager/topic";
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

    //CKEditor图片上传测试
    @ResponseBody
    @PostMapping("/ck/upload")
    public CKdemo ckUpload(MultipartFile upload, String ckCsrfToken, HttpServletRequest request) {
        if (!upload.isEmpty()) {
            log.info("有文件传过来了...");
        }
        CKdemo c = new CKdemo();
        c.setUploaded(1).setUrl(request.getContextPath() + "/upload/20190216/703dd179fe6a4e7fa8164b056b1afddc.jpg");
        log.info(c.toString());
        return c;
    }

    //图片上传
    @ResponseBody
    @PostMapping("manager/upload")
    public String upload(MultipartFile file) {
        if (!ObjectUtils.isEmpty(file)) {
            //上传时的文件名
            String originalFilename = file.getOriginalFilename();
            //文件类型
            String contentType = file.getContentType();
            //文件大小
//            long size = file.getSize();
//            //前端input中的name
//            String name = file.getName();
            //由于在前端判断过了，所以我们就不影响性能了,等出现问题再加验证模块
            //获取扩展名
            String ext;
            if (originalFilename.contains(".")) {
                ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            } else {
                ext = "." + contentType.substring(contentType.lastIndexOf("/") + 1);
            }
            //根据日期来分文件夹保存
            String dirPath = new SimpleDateFormat("yyyyMMdd").format(new Date());
            //使用UUID生成本地文件名
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String localFileName = uuid + ext;
            String filePath = this.getClass().getResource("/").getPath().substring(1);
            filePath = filePath.substring(0, filePath.indexOf("/")) + "/upload/" + dirPath;
            File saveFile = new File(filePath, localFileName);
            if (!saveFile.getParentFile().exists()) {
                //源码注释:创建此抽象路径名所指定的目录，包括any
                // 必要但不存在的父目录。
                // 请注意，如果这样操作失败它可能已经成功创建了一些必要的父目录
                saveFile.mkdirs();
            } else {
                //如果文件已存在，我们就删掉它
                saveFile.delete();
            }
            //开始保存文件
            try {
                file.transferTo(saveFile);
                String url = "/upload/" + dirPath + "/" + localFileName;
                return url;
            } catch (IOException e1) {
                try {
                    file.transferTo(saveFile);
                    String url = "/upload/" + dirPath + "/" + localFileName;
                    return url;
                } catch (IOException e2) {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

}
