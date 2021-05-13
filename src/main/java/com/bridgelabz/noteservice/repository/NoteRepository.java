package com.bridgelabz.noteservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.noteservice.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

	public List<Note> findByUserId(Long userId);
	
	public Note findByUserIdAndId(Long userId,Long Id);
}
