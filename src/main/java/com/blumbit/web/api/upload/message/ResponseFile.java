package com.blumbit.web.api.upload.message;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ResponseFile {
    private String name;
    private String uri;
    private String type;
    private long size;
}
