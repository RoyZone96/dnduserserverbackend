package com.dndbackendlayer.dndbackend.repo;

import com.dndbackendlayer.dndbackend.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;


public interface BookmarkRepo extends JpaRepository<Bookmark, Long>{
    List<Bookmark> findAll();
    List<Bookmark> findByUrl(String url);
    List<Bookmark> findById(long id);
    List<Bookmark> findByCreateDate(Date createDate);
    List<Bookmark> findByUserId(long user_id);
}
