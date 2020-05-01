package guru.springframework.recipe.springrecipe.repositories;

import guru.springframework.recipe.springrecipe.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
