package com.blumbit.web.api.upload.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "files")
public class FileDB {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, unique = true)
    private UUID id;

    private String name;
    private String type;

    @Lob
    private byte[] data;

    // Custom Constructor
    public FileDB(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
