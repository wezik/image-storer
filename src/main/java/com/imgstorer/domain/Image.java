package com.imgstorer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NamedQuery(
        name= "Image.findByUser",
        query = "FROM images WHERE owner = :user"
)

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "images")
public class Image {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToOne
    @JoinColumn(name = "owner")
    private User owner;

    @Column
    private Long width;

    @Column
    private Long height;

    @Column
    private String path;

    @Column
    private LocalDate uploadDate;

}
