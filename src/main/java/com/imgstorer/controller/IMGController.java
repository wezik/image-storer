package com.imgstorer.controller;

import com.imgstorer.config.IMGConfig;
import com.imgstorer.domain.Image;
import com.imgstorer.domain.User;
import com.imgstorer.exception.ImageNotFoundException;
import com.imgstorer.exception.UserNotFoundException;
import com.imgstorer.service.IMGService;
import com.imgstorer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("v1")
public class IMGController {

    private final IMGConfig imgConfig;
    private final IMGService imgService;
    private final UserService userService;

    @GetMapping(value = "img/{user_id}/{img_id}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable Long user_id, @PathVariable Long img_id) throws IOException, ImageNotFoundException {
        Image image = imgService.getImage(img_id).orElseThrow(ImageNotFoundException::new);
        File file = new File(imgConfig.getDirectory()+'/'+user_id+'/'+image.getPath());
        return IOUtils.toByteArray(new FileInputStream(file));
    }

    @GetMapping(value = "img/{user_id}/urls")
    public List<String> getImagesURLForUser(@PathVariable Long user_id) throws UserNotFoundException {
        String baseUrl = "img/"+user_id+'/';
        List<String> urls = new ArrayList<>();
        User user = userService.getUser(user_id).orElseThrow(UserNotFoundException::new);
        List<Image> images = imgService.getAlbum(user);
        for(Image image: images) {
            urls.add(baseUrl+image.getId());
        }
        return urls;
    }

    @GetMapping(value = "img/{user_id}")
    public List<Image> getImagesForUser(@PathVariable Long user_id) throws UserNotFoundException {
        User user = userService.getUser(user_id).orElseThrow(UserNotFoundException::new);
        return imgService.getAlbum(user);
    }

    @PostMapping(value = "img/{owner_id}/upload/{title}")
    public void uploadImageWithTitle(
            @PathVariable Long owner_id,
            @RequestParam("file") MultipartFile file,
            @PathVariable String title
    ) throws UserNotFoundException {
        uploadImage(owner_id,file,title);
    }

    @PostMapping(value = "img/{owner_id}/upload")
    public void uploadImageSimple(
            @PathVariable Long owner_id,
            @RequestParam("file") MultipartFile file
    ) throws UserNotFoundException {
        uploadImage(owner_id,file,file.getOriginalFilename());
    }

    private void uploadImage(Long owner_id, MultipartFile file, String title) throws UserNotFoundException {
        Path fileNameAndPath = Paths.get(imgConfig.getDirectory(),owner_id.toString(),file.getOriginalFilename());
        User user = userService.getUser(owner_id).orElseThrow(UserNotFoundException::new);
        try {
            try {
                Files.createDirectory(Paths.get(imgConfig.getDirectory(),owner_id.toString()));
            } catch (FileAlreadyExistsException e) {
                //
            }
            InputStream is = new ByteArrayInputStream(file.getBytes());
            BufferedImage bi = ImageIO.read(is);
            Files.write(fileNameAndPath, file.getBytes());
            imgService.saveImage(
                    new Image(
                            null,
                            title,
                            user,
                            (long) bi.getWidth(),
                            (long) bi.getHeight(),
                            file.getOriginalFilename(),
                            LocalDate.now())
            );
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @DeleteMapping(value = "img/{user_id}/{img_id}")
    public boolean deleteImage(@PathVariable Long user_id, @PathVariable Long img_id) throws ImageNotFoundException {
        Image image = imgService.getImage(img_id).orElseThrow(ImageNotFoundException::new);
        File file = new File(imgConfig.getDirectory()+user_id+'/'+image.getPath());
        if (file.delete()) {
            imgService.delete(image);
            return true;
        }
        return false;
    }

}
