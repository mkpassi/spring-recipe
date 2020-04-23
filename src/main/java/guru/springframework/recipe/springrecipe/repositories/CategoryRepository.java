package guru.springframework.recipe.springrecipe.repositories;

import guru.springframework.recipe.springrecipe.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long> {

    Optional<Category> findByDescription(String description);
}
