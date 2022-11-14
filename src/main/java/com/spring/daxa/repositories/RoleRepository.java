package com.spring.daxa.repositories;

import com.spring.daxa.entity.Role;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @NonNull
    Role getById(@NonNull Long id);
}