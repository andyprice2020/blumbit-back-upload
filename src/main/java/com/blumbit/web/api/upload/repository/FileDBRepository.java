package com.blumbit.web.api.upload.repository;

import com.blumbit.web.api.upload.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileDBRepository extends JpaRepository<FileDB, UUID> {
}
