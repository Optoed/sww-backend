package com.ece.bot.service.impl;

import com.ece.bot.common.error.ResponseError;
import com.ece.bot.dto.file.FileDto;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.model.CardFile;
import com.ece.bot.repository.FileRepository;
import com.ece.bot.service.FileService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Value("${app.conf.image.folder.path}")
    private String path;

    @Value("${app.conf.testing-mode}")
    private Boolean testingMode;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public BotResponse<FileDto> load(MultipartFile file) {
        BotResponse<FileDto> response = new BotResponse<>();
        if (file == null) {
            response.addError(ResponseError.FILE_IS_EMPTY);
            return response;
        }
        File uploadDir = new File(path);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String fileUuid = UUID.randomUUID().toString();
        String[] fileNameParts = file.getOriginalFilename().split("\\.");
        String fileName = path + "/" + fileUuid + "." + fileNameParts[fileNameParts.length - 1];
        try {
            file.transferTo(new File(fileName));
            CardFile img = new CardFile();

            img.setFileName(fileName);
            img = fileRepository.save(img);
            FileDto fileDto = new FileDto(img.getId().toString(),img.getFileName());
            response.setData(fileDto);
            return response;
        } catch (IOException e) {
            response.addError(ResponseError.ERROR_WHILE_SAVING_FILE);
            return response;
        }
    }

    @PostConstruct
    public void init() {
        if(testingMode != null && testingMode){
            List<CardFile> files = fileRepository.findAll();
            if(CollectionUtils.isEmpty(files)){
                CardFile img = new CardFile();
                img.setFileName(UUID.randomUUID().toString());
                img = fileRepository.save(img);
            }
        }
    }

    @Override
    public CardFile getById(Long id) {
        Optional<CardFile> file = fileRepository.findById(id);
        return file.orElse(null);
    }

    @Override
    public CardFile getByGuid() {
        return null;
    }
}
