package RahulShettySpring.RahulShettySpringProject.Repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import RahulShettySpring.RahulShettySpringProject.Controller.Library;

public class LibraryRepositoryImpl implements LibraryRepositoryCustom {

	@Autowired
	LibraryRepository libraryRepository;

	@Override
	public List<Library> findAllByAuthor(String authorName) {

		List<Library> allRecords=libraryRepository.findAll();
		List<Library> recordsWithAuthorName=new ArrayList<>();
		for(Library lib: allRecords){

			if(lib.getAuthor().equals(authorName)){
				recordsWithAuthorName.add(lib);
			}
		}

		return recordsWithAuthorName;
	}

	@Override
	public Library updateBookById(String id,Library updateRequest) {
		Library lib=libraryRepository.findById(id).get();
		lib=updateRequest;
		libraryRepository.save(lib);
		return lib;

	}
}
