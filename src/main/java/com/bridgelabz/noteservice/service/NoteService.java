package com.bridgelabz.noteservice.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.noteservice.dto.NoteDto;
import com.bridgelabz.noteservice.entity.Note;

public interface NoteService {

public Note createNote(String token,NoteDto noteDto);
	
	public List<Note> getAllNotes(String token);
	
	public Note getNote(String token,Long noteId);
	
	public Note	 changeColour(String token,Long noteId,String colour);
	
	public Note pinNote(String token,Long noteId);
	
	public Note archiveNote(String token,Long noteId);
	
	public Note trashNote(String token,Long noteId);

	public void deleteNote(String token,Long noteId);
	
	public String addImage(String  token,Long noteId,MultipartFile file);

}
