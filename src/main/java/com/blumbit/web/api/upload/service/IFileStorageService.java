package com.blumbit.web.api.upload.service;

import com.blumbit.web.api.upload.entity.FileDB;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface IFileStorageService {

    // Almacenar el archivo de imagen
    FileDB store(MultipartFile file) throws IOException;

    // Obtener el archivo de imagen mediante ID
    Optional<FileDB> getFile(UUID id);

    // Obtener todos los archivos de imagen
    Stream<FileDB> getAllFiles();
}
