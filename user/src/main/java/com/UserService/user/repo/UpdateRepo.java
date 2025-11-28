package com.UserService.user.repo;

import com.UserService.user.model.UserUpdateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateRepo extends JpaRepository<UserUpdateRequest, Long> {

}
