package com.imgstorer.service;

import com.imgstorer.domain.Image;
import com.imgstorer.domain.User;
import com.imgstorer.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IMGService {

    private final ImageRepository imageRepository;

    public Optional<Image> getImage(Long id) {
        return imageRepository.findById(id);
    }

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    public List<Image> getAlbum(User user) {
        return imageRepository.findByUser(user);
    }

    public void delete(Image image) {
        imageRepository.delete(image);
    }

}
