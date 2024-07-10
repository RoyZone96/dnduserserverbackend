package com.dndbackendlayer.dndbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dndbackendlayer.dndbackend.model.Bookmark;
import com.dndbackendlayer.dndbackend.repo.BookmarkRepo;

import jakarta.transaction.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class BookmarksService {

    @Autowired
    BookmarkRepo bookmarkRepo;

    public Bookmark saveBookmark(Bookmark bookmark) {
      bookmarkRepo.save(bookmark);
        return bookmark;
    }

    public String deleteBookmark(Long id) {
        if (bookmarkRepo.existsById(id)) {
            bookmarkRepo.deleteById(id);
        } else {
            return "Bookmark not found!";
        }
        return "Bookmark deleted successfully!";
    }


    public String getBookmarksById(Long id) {
        return bookmarkRepo.findById(id).toString();
    }

    public List<Bookmark> getBookmarksByCreateDate(Date createDate) {
        System.out.println("The method did get called, yo");
        return bookmarkRepo.findByCreateDate(createDate); 
    }

    public List<Bookmark> getBookmarksByUser(Long user_id) {
        return bookmarkRepo.findByUserId(user_id);
    }
    
}
