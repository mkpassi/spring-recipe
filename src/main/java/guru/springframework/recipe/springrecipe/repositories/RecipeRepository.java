package guru.springframework.recipe.springrecipe.repositories;

import guru.springframework.recipe.springrecipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
