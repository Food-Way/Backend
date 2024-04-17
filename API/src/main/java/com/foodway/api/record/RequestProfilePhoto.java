package com.foodway.api.record;

import com.foodway.api.model.Enums.ETypeUser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record RequestProfilePhoto(
        MultipartFile file,
        String path,
        UUID idUser,
        ETypeUser typeUser
) {
}
