package com.imgstorer.domain.dto;

import com.imgstorer.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ImageDto {
    private final Long id;
    private final String name;
    private final User owner;
    private final Long width;
    private final Long height;
    private final String path;
    private final LocalDate uploadDate;
}
