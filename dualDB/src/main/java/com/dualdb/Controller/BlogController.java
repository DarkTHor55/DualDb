package com.dualdb.Controller;


import com.dualdb.Entity.Postgres.Blog;
import com.dualdb.Service.BlogService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    private final BlogService service;

    public BlogController(BlogService service) {
        this.service = service;
    }

    @GetMapping
    public List<Blog> getAllBlogs() {
        return service.getAllBlogs();
    }

    @GetMapping("/{id}")
    public Blog getBlog(@PathVariable Long id) {
        return service.getBlogById(id);
    }

    @PostMapping
    public Blog addBlog(@RequestBody Blog blog) {
        return service.saveBlog(blog);
    }

    @DeleteMapping("/{id}")
    public String deleteBlog(@PathVariable Long id) {
        service.deleteBlog(id);
        return "Blog deleted";
    }
}
