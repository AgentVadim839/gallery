package com.task.webpics.controller;

import com.task.webpics.entity.Images;
import com.task.webpics.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/")
    public String index(Model model, @RequestParam(value = "tag", required = false) String tag) {
        List<Images> images;

        if(tag != null && !tag.isEmpty()) {
            images = imageService.searchByTag(tag);
            model.addAttribute("tag", tag);
        } else {
            images = imageService.findAll();
        }

        model.addAttribute("images", images);
        return "index";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            imageService.uploadAndAnalyze(file);
            redirectAttributes.addFlashAttribute("message", "Success! Image analyzed.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error: " + e.getMessage());
        }
        return "redirect:/"; // Reloading the page to clear form
    }
}
