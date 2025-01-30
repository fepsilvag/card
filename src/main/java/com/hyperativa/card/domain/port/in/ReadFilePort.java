package com.hyperativa.card.domain.port.in;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReadFilePort {

    List<String[]> execute(MultipartFile file);

}