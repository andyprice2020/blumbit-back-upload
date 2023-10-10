package com.blumbit.web.api.upload.service.impl;

import com.blumbit.web.api.upload.entity.FileDB;
import com.blumbit.web.api.upload.repository.FileDBRepository;
import com.blumbit.web.api.upload.service.IFileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class FileStorageServiceImpl implements IFileStorageService {

    private final FileDBRepository fileRepository;
    @Override
    @Transactional
    public FileDB store(MultipartFile file) throws IOException {
        //Obtenemos el nombre del archivo
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        //Crear un objeto de tipo FileDB
        FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
        return fileRepository.save(fileDB);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileDB> getFile(UUID id) {
        return fileRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<FileDB> getAllFiles() {
        return fileRepository.findAll().stream();
    }
}
