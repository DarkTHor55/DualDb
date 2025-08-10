package com.dualdb.Service;

import com.dualdb.Entity.Postgres.Blog;
import com.dualdb.Repository.Postgres.BlogRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepo;

    public BlogService(BlogRepository blogRepo) {
        this.blogRepo = blogRepo;
    }

    public List<Blog> getAllBlogs() {
        return blogRepo.findAll();
    }

    public Blog getBlogById(Long id) {
        return blogRepo.findById(id).orElse(null);
    }

    public Blog saveBlog(Blog blog) {
        return blogRepo.save(blog);
    }

    public void deleteBlog(Long id) {
        blogRepo.deleteById(id);
    }
}
