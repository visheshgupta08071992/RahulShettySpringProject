package RahulShettySpring.RahulShettySpringProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import RahulShettySpring.RahulShettySpringProject.Controller.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library,String>,LibraryRepositoryCustom {
}
