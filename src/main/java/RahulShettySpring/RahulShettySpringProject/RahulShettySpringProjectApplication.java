package RahulShettySpring.RahulShettySpringProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import RahulShettySpring.RahulShettySpringProject.Repository.LibraryRepository;

@SpringBootApplication
public class RahulShettySpringProjectApplication {

	@Autowired
	LibraryRepository libraryRepository;

	public static void main(String[] args) {

		SpringApplication.run(RahulShettySpringProjectApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//
//		// To retrieve single record
//		Library lib=libraryRepository.findById("fdsefr343").get();
//		System.out.println(lib.getAuthor());
//		Library lib2=new Library();
//		lib2.setAuthor("Vishesh");
//		lib2.setAisle(123);
//		lib2.setBookName("JavaBook");
//		lib2.setIsbn("hero");
//		lib2.setId("hero123");
//
//		//To Insert record within DB
//		libraryRepository.save(lib2);
//
//		//To retrieve all the records
//		List<Library> records=libraryRepository.findAll();
//		for(Library l: records){
//			System.out.println(l.getAuthor());
//
//		}
//
//		libraryRepository.delete(lib2);
//	}
}
