package com.example.assignment.controller;

import com.example.assignment.dto.UserDetailsReq;
import com.example.assignment.entity.UserDetails;
import com.example.assignment.service.DetailsService;
import com.example.assignment.service.FileUploadService;
import com.example.assignment.utils.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/details")
public class UserDetailsController {
    private final DetailsService detailsService;
    private final FileUploadService fileUploadService;

    public UserDetailsController(
            DetailsService detailsService,
            FileUploadService fileUploadService
    ) {
        this.detailsService = detailsService;
        this.fileUploadService = fileUploadService;
    }

    // Get Details
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDetails>> getDetails(
            @PathVariable Long userId
    ) {

        UserDetails details = detailsService.getDetailsByUserId(userId);
        ApiResponse<UserDetails> response = new ApiResponse<>(
                true,
                "Details Fetched Successfully",
                details
        );
        return ResponseEntity.ok(response);
    }

    // update details
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDetails>> updateDetails(
            @PathVariable Long userId,
            @RequestBody UserDetailsReq request
    ) {

        UserDetails updatedDetails = detailsService.updateDetails(
                userId,
                request
        );

        ApiResponse<UserDetails> response =
                new ApiResponse<>(
                        true,
                        "Details updated successfully...",
                        updatedDetails
                );

        return ResponseEntity.ok(response);
    }

    // PDF Upload
    @PostMapping("/{userId}/upload/pdf")
    public UserDetails uploadPdf(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        String pdfPath =
                fileUploadService.uploadPdf(file);

        return detailsService
                .updatePdfPath(userId, pdfPath);
    }

    // Video Upload
    @PostMapping("/{userId}/upload/video")
    public UserDetails uploadVideo(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        String videoPath =
                fileUploadService.uploadVideo(file);

        return detailsService
                .updateVideoPath(userId, videoPath);
    }


}
