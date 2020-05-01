package guru.springframework.recipe.springrecipe.services;

import guru.springframework.recipe.springrecipe.commands.IngredientCommand;
import guru.springframework.recipe.springrecipe.converters.IngredientToIngredientCommand;
import guru.springframework.recipe.springrecipe.domain.Recipe;
import guru.springframework.recipe.springrecipe.repositories.IngredientRepository;
import guru.springframework.recipe.springrecipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

  private final IngredientRepository ingredientRepository;

  private final IngredientToIngredientCommand ingredientCommand;

  private final RecipeRepository recipeRepository;

  public IngredientServiceImpl(
      IngredientRepository ingredientRepository,
      IngredientToIngredientCommand ingredientCommand,
      RecipeRepository recipeRepository) {

    this.ingredientRepository = ingredientRepository;
    this.ingredientCommand = ingredientCommand;
    this.recipeRepository = recipeRepository;

  }

  @Override
  public IngredientCommand findIngredientByRecipeIdAndIngredientId(
      Long recipeId, Long ingredientId) {

    Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

    if (!optionalRecipe.isPresent()) {
      log.debug("Recipe with Id : {} not found", recipeId);
      throw new RuntimeException("Recipe not found with Id : {}" + recipeId);
    }

    Recipe recipe = optionalRecipe.get();

    Optional<IngredientCommand> ingredientCommandOptional =
        recipe.getIngredients().stream()
            .filter(ingredient -> ingredient.getId().equals(ingredientId))
            .map(ingredient -> ingredientCommand.convert(ingredient))
            .findFirst();

    if (!ingredientCommandOptional.isPresent()) {
      log.debug("Ingredient not found for id :{}", ingredientId);
      throw new RuntimeException("Ingredient not found for Id : {}" + ingredientId);
    }

    return ingredientCommandOptional.get();
  }
}
