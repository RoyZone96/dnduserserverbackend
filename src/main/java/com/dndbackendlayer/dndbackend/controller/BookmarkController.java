package com.dndbackendlayer.dndbackend.controller;



import com.dndbackendlayer.dndbackend.repo.BookmarkRepo;
import com.dndbackendlayer.dndbackend.model.Bookmark;
import com.dndbackendlayer.dndbackend.service.BookmarksService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;  
import java.util.Collections;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/bookmarks") public class BookmarkController {

    @Autowired
    private BookmarksService bookmarkService;

    @Autowired
    private BookmarkRepo bookmarkRepo;
    

    // Endpoint to create a new bookmark
    @PostMapping("/createBookmark")
    public Bookmark createBookmark(@RequestBody Bookmark bookmark) {
        return bookmarkService.saveBookmark(bookmark);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bookmark> findBookmarkById(@PathVariable Long id) {
        Optional<Bookmark> bookmark = bookmarkRepo.findById(id);
        if (bookmark.isPresent()) {
            return ResponseEntity.ok(bookmark.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Bookmark>> getBookmarksByUser(@PathVariable Long user_id) {
        List<Bookmark> bookmarks = bookmarkService.getBookmarksByUser(user_id);
        if (bookmarks == null || bookmarks.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(bookmarks);
    }


    // New endpoint to retrieve bookmarks by creation date
    @GetMapping("/date")
    public List<Bookmark> getBookmarksByDate(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date createDate) {
        // Convert java.util.Date to java.sql.Date
        java.sql.Date sqlCreateDate = new java.sql.Date(createDate.getTime());
        return bookmarkService.getBookmarksByCreateDate(sqlCreateDate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookmark(@PathVariable Long id) {
        try {
            bookmarkService.deleteBookmark(id);
            return ResponseEntity.ok().body("Bookmark deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting bookmark");
        }
    }
}