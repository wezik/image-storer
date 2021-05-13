package com.imgstorer.repository;

import com.imgstorer.domain.Image;
import com.imgstorer.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends CrudRepository<Image, Long> {
    Optional<Image> findById(Long id);
    @Query
    List<Image> findByUser(@Param("user") User user);
}
