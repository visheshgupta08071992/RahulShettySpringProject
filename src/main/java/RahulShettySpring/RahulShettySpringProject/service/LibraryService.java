package RahulShettySpring.RahulShettySpringProject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import RahulShettySpring.RahulShettySpringProject.Controller.Library;
import RahulShettySpring.RahulShettySpringProject.Repository.LibraryRepository;

@Service
public class LibraryService {

	@Autowired
	LibraryRepository libraryRepository;

	public String buildID(String isbn,int aisle){

		if(isbn.startsWith("z")){
			return "OLD" + isbn+aisle;
		}
		return isbn+aisle;
	}

	public boolean checkBookAlreadyExists(String id){

		Optional<Library> lib=libraryRepository.findById(id);
		if(lib.isPresent()){
			return true;
		}
		else{
			return false;
		}
	}
}
