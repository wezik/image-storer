package com.imgstorer.mapper;

import com.imgstorer.domain.Image;
import com.imgstorer.domain.dto.ImageDto;
import org.springframework.stereotype.Service;

@Service
public class ImageMapper {

    public ImageDto mapToImageDto(Image image) {
        return new ImageDto(
                image.getId(),
                image.getName(),
                image.getOwner(),
                image.getWidth(),
                image.getHeight(),
                image.getPath(),
                image.getUploadDate()
        );
    }

}
