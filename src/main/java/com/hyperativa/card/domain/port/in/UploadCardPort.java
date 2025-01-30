package com.hyperativa.card.domain.port.in;

import org.springframework.web.multipart.MultipartFile;

public interface UploadCardPort {

    void execute(MultipartFile file);

}