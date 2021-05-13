package com.bridgelabz.noteservice.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.noteservice.dto.NoteDto;
import com.bridgelabz.noteservice.entity.Note;
import com.bridgelabz.noteservice.entity.NoteImage;
import com.bridgelabz.noteservice.exception.FundooException;
import com.bridgelabz.noteservice.repository.NoteImageRespository;
import com.bridgelabz.noteservice.repository.NoteRepository;
import com.bridgelabz.noteservice.utils.S3Service;
import com.bridgelabz.noteservice.utils.TokenService;

@Service
public class NoteServiceImp implements NoteService {

	@Autowired
	private NoteRepository noteRepo;

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private NoteImageRespository noteImageRepo;

	
	@Override
	public Note createNote(String token, NoteDto noteDto) {
		Long userId = tokenService.decodeToken(token);
		Note note = new Note();
		// can use modelmapper also
		BeanUtils.copyProperties(noteDto, note);
		note.setUserId(userId);
		return noteRepo.save(note);
		
	}
	

	@Override
	public List<Note> getAllNotes(String token) {
		Long userId = tokenService.decodeToken(token);
		
		return noteRepo.findByUserId(userId);
	}

	@Override
	public Note getNote(String token, Long noteId) {
		Long userId = tokenService.decodeToken(token);
		Note note =  noteRepo.findByUserIdAndId(userId, noteId);
		if(note == null)
			throw new FundooException(HttpStatus.NOT_FOUND.value(),"Note not Found");
		return note;
	}

	@Override
	public Note changeColour(String token, Long noteId, String colour) {
		Long userId = tokenService.decodeToken(token);
		Note note =  noteRepo.findByUserIdAndId(userId, noteId);
		if(note == null)
			throw new FundooException(HttpStatus.NOT_FOUND.value(),"Note not Found");
		
			note.setColour(colour);
		return noteRepo.save(note);
	}

	@Override
	public Note pinNote(String token, Long noteId) {
		Long userId = tokenService.decodeToken(token);
		Note note =  noteRepo.findByUserIdAndId(userId, noteId);
		if(note == null)
			throw new FundooException(HttpStatus.NOT_FOUND.value(),"Note not Found");
		
		if(note.getPin().equals(true))
			note.setPin(false);
		else
			note.setPin(true);
		return noteRepo.save(note);	}

	@Override
	public Note archiveNote(String token, Long noteId) {
		Long userId = tokenService.decodeToken(token);
		Note note =  noteRepo.findByUserIdAndId(userId, noteId);
		if(note == null)
			throw new FundooException(HttpStatus.NOT_FOUND.value(),"Note not Found");
		
		if(note.getArchive().equals(true))
			note.setArchive(false);
		else
			note.setArchive(true);
		return noteRepo.save(note);
	}

	@Override
	public Note trashNote(String token, Long noteId) {
		
		Long userId = tokenService.decodeToken(token);
		Note note =  noteRepo.findByUserIdAndId(userId, noteId);
		if(note == null)
			throw new FundooException(HttpStatus.NOT_FOUND.value(),"Note not Found");
		
		if(note.getTrash().equals(true))
			note.setTrash(false);
		else
			note.setTrash(true);
		return noteRepo.save(note);
	}

	@Override
	public void deleteNote(String token, Long noteId) {
		Long userId = tokenService.decodeToken(token);
		Note note =  noteRepo.findByUserIdAndId(userId, noteId);
		if(note == null)
			throw new FundooException(HttpStatus.NOT_FOUND.value(),"Note not Found");
		noteRepo.deleteById(noteId);
	}

	@Override
	public String addImage(String token, Long noteId, MultipartFile file) {
		Long userId = tokenService.decodeToken(token);
		Note note =  noteRepo.findByUserIdAndId(userId, noteId);
		if(note == null)
			throw new FundooException(HttpStatus.NOT_FOUND.value(),"Note not Found");
		
		String key = s3Service.fileUpload(file, "note_images", note.getId().toString());
		NoteImage noteImage = new NoteImage();
		noteImage.setUrl(key);
		NoteImage savedNoteImage = noteImageRepo.save(noteImage);
		note.getImages().add(savedNoteImage);
		noteRepo.save(note);
		return key;	
		
	}

}
