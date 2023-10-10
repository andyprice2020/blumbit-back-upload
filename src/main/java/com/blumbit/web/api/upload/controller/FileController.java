package com.blumbit.web.api.upload.controller;

import com.blumbit.web.api.upload.entity.FileDB;
import com.blumbit.web.api.upload.message.ResponseFile;
import com.blumbit.web.api.upload.message.ResponseMessage;
import com.blumbit.web.api.upload.service.IFileStorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FileController {

    private final IFileStorageService fileStorageService;

    // Sube una imagen
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@Valid @RequestParam("file")MultipartFile file) {

        try {
            // Guarde el archivo
            fileStorageService.store(file);
            // Indicamos que se ha subido exitosamente el archivo
            var message = "Uploaded the file successfully!: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }
        catch (Exception e) {
            var message = "Could not Upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    // Listar las imagenes almacenadas en la BBDD
    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getAllFiles() {
        // Devolvemos la lista de iamgenes mediante un stream
        List<ResponseFile> files = fileStorageService.getAllFiles().map(dbFile->{
            var fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(String.valueOf(dbFile.getId()))
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    // Obtener un archivo de imagen mediante su ID
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFileByID(@Valid @PathVariable UUID id) {

        Optional<FileDB> fileDB = fileStorageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + fileDB.get().getName() + "\"")
                .body(fileDB.get().getData());
    }
}
