package com.imgstorer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class IMGConfig {

    @Value("${images.directory}")
    private String directory;

}
