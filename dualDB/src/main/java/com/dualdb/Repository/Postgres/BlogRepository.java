package com.dualdb.Repository.Postgres;



import com.dualdb.Entity.Postgres.Blog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlogRepository extends JpaRepository<Blog, Long> {
}
