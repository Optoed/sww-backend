package com.ece.bot.web.rest.admin;


import com.ece.bot.dto.file.FileDto;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/file")
@CrossOrigin
public class FileRest {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/load")
    public BotResponse<FileDto> add(@RequestParam("file") MultipartFile file) {
        return fileService.load(file);
    }
}
