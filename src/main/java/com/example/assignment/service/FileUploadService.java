package com.example.assignment.service;

import com.example.assignment.utils.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadService {
    private static final String PDF_DIR = "uploads/pdfs/";
    private static final String VIDEO_DIR = "uploads/videos/";

    // upload pdf
    public String uploadPdf(
            MultipartFile file
    ) throws IOException {
        return uploadFile(file, PDF_DIR);
    }

    // upload video
    public String uploadVideo(
            MultipartFile file
    ) throws IOException {
        return uploadFile(file, VIDEO_DIR);
    }

    // Upload logic
    private String uploadFile(
            MultipartFile file,
            String uploadDir
    ) throws IOException {

        if (file.isEmpty()) {
            throw new ResourceNotFoundException("File is empty");
        }

        // create folder if not exist
        Files.createDirectories(
                Paths.get(uploadDir)
        );

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isBlank()) {
            throw new ResourceNotFoundException("Invalid filename");
        }

        // Generate unique filename
        String fileName = UUID.randomUUID() + "_" + originalFilename;

        Path filePath = Paths.get(uploadDir, fileName);

        // save file
        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return filePath.toString();
    }
}
