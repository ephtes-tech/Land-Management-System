package com.UserService.user.repo;

import com.UserService.user.model.UserUpdateRequest;
import com.UserService.user.status.UpdateRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpdateRepo extends JpaRepository<UserUpdateRequest, Long> {
    List<UserUpdateRequest> findByStatus(UpdateRequestStatus status);
}
