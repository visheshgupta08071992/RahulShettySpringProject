package RahulShettySpring.RahulShettySpringProject.Repository;

import java.util.List;

import RahulShettySpring.RahulShettySpringProject.Controller.Library;

public interface LibraryRepositoryCustom {

	public List<Library> findAllByAuthor(String authorName);
	public Library updateBookById(String id,Library updateRequest);
}
