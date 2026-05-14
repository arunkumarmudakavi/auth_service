package com.example.assignment.service;

import com.example.assignment.dto.UserDetailsReq;
import com.example.assignment.entity.User;
import com.example.assignment.entity.UserDetails;
import com.example.assignment.repository.UserDetailsRepository;
import com.example.assignment.repository.UserRepository;
import com.example.assignment.utils.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DetailsService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    public DetailsService(
            UserRepository userRepository,
            UserDetailsRepository userDetailsRepository
    ) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    // Get Details
    public UserDetails getDetailsByUserId(
            Long userId
    ) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found!"));

        return userDetailsRepository
                .findByUser(user)
                .orElseGet(() -> {
                    UserDetails details = new UserDetails();
                    details.setEmail(user.getEmail());
                    details.setUser(user);

                    return userDetailsRepository.save(details);
                });
    }

    // Update details
    public UserDetails updateDetails(
            Long userId,
            UserDetailsReq request
    ) {

        UserDetails details = getDetailsByUserId(userId);

        details.setName(request.getName());
        details.setEmail(request.getEmail());
        details.setCountryCode(request.getCountryCode());
        details.setPhone(request.getPhone());
        details.setAddress(request.getAddress());

        return userDetailsRepository.save(details);
    }

    // update pdf path
    public UserDetails updatePdfPath(
            Long userId,
            String pdfPath
    ) {

        UserDetails details = getDetailsByUserId(userId);

        details.setPdfPath(pdfPath);

        return userDetailsRepository.save(details);
    }

    // update video path
    public UserDetails updateVideoPath(
            Long userId,
            String videoPath
    ) {

        UserDetails details = getDetailsByUserId(userId);

        details.setVideoPath(videoPath);

        return userDetailsRepository.save(details);
    }
}
