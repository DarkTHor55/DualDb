package com.dualdb.Repository.Mysql;

import com.dualdb.Entity.Mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
