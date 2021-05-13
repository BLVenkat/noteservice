package com.bridgelabz.noteservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.noteservice.entity.NoteImage;

@Repository
public interface NoteImageRespository extends JpaRepository<NoteImage, Long> {

}
