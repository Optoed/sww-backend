package com.ece.bot.service;

import com.ece.bot.dto.file.FileDto;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.model.CardFile;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {
    public BotResponse<FileDto> load(MultipartFile file);
    public CardFile getById(Long id);
    public CardFile getByGuid();
}
