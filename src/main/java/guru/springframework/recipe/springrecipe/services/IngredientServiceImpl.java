package guru.springframework.recipe.springrecipe.services;

import guru.springframework.recipe.springrecipe.commands.IngredientCommand;
import guru.springframework.recipe.springrecipe.converters.IngredientCommandToIngredient;
import guru.springframework.recipe.springrecipe.converters.IngredientToIngredientCommand;
import guru.springframework.recipe.springrecipe.domain.Ingredient;
import guru.springframework.recipe.springrecipe.domain.Recipe;
import guru.springframework.recipe.springrecipe.repositories.IngredientRepository;
import guru.springframework.recipe.springrecipe.repositories.RecipeRepository;
import guru.springframework.recipe.springrecipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

  private final IngredientRepository ingredientRepository;

  private final IngredientToIngredientCommand ingredientCommand;

  private final IngredientCommandToIngredient ingredientCommandToIngredient;

  private final RecipeRepository recipeRepository;

  private final UnitOfMeasureRepository unitOfMeasureRepository;

  public IngredientServiceImpl(
      IngredientRepository ingredientRepository,
      IngredientToIngredientCommand ingredientCommand,
      IngredientCommandToIngredient ingredientCommandToIngredient,
      RecipeRepository recipeRepository,
      UnitOfMeasureRepository unitOfMeasureRepository) {

    this.ingredientRepository = ingredientRepository;
    this.ingredientCommand = ingredientCommand;
    this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    this.recipeRepository = recipeRepository;

    this.unitOfMeasureRepository = unitOfMeasureRepository;
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
            .map(ingredient -> {
              IngredientCommand convertedIngredeintCommandObject = ingredientCommand.convert(ingredient);
              log.debug("Converted IngredientCommand Object : {}", convertedIngredeintCommandObject);
              return convertedIngredeintCommandObject;
            }).findFirst();

    if (!ingredientCommandOptional.isPresent()) {
      log.debug("Ingredient not found for id :{}", ingredientId);
      throw new RuntimeException("Ingredient not found for Id : {}" + ingredientId);
    }

    return ingredientCommandOptional.get();
  }

  @Override
  @Transactional
  public IngredientCommand saveIngredientCommand(IngredientCommand command) {
    Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

    if (!recipeOptional.isPresent()) {

      // todo toss error if not found!
      log.error("Recipe not found for id: " + command.getRecipeId());
      return new IngredientCommand();
    } else {
      Recipe recipe = recipeOptional.get();

      Optional<Ingredient> ingredientOptional =
          recipe.getIngredients().stream()
              .filter(ingredient -> ingredient.getId().equals(command.getId()))
              .findFirst();

      if (ingredientOptional.isPresent()) {
        Ingredient ingredientFound = ingredientOptional.get();
        ingredientFound.setDescription(command.getDescription());
        ingredientFound.setAmount(command.getAmount());
        ingredientFound.setUnitOfMeasure(
            unitOfMeasureRepository
                .findById(command.getUnitOfMeasure().getId())
                .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); // todo address this
      } else {
        // add new Ingredient
        Ingredient ingredient = ingredientCommandToIngredient.convert(command);
        ingredient.setRecipe(recipe);
        recipe.addIngredient(ingredient);
      }

      Recipe savedRecipe = recipeRepository.save(recipe);

      Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
              .filter(recipeIngredient -> recipeIngredient.getId().equals(command.getId()))
              .findFirst();

      if(!savedIngredientOptional.isPresent()){
        savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(recipeIngredient -> recipeIngredient.getDescription().equals(command.getDescription()))
                .filter(recipeIngredient -> recipeIngredient.getAmount().equals(command.getAmount()))
                .filter(recipeIngredient -> recipeIngredient.getUnitOfMeasure().getId().equals(command.getUnitOfMeasure().getId()))
                .findFirst();
      }

      // to do check for fail
      return ingredientCommand.convert(savedIngredientOptional.get());

    }
  }

  @Override
  public void deleteById(Long recipeId, Long ingredientId) {
    Optional<Recipe> recipeByIdOptional = recipeRepository.findById(recipeId);

    if (!recipeByIdOptional.isPresent()) {
      throw new RuntimeException(
          "Exception Caught while retrieveng Recipe By Id with Id :" + recipeByIdOptional);
    }
    Recipe retrievedRecipe = recipeByIdOptional.get();
    log.debug("Recipe Found");
    Optional<Ingredient> ingredientOptional =
        retrievedRecipe.getIngredients().stream()
            .filter(ingredient -> ingredient.getId().equals(ingredientId))
            .findFirst();

    if(!ingredientOptional.isPresent()){
      log.debug("Ingredient Not Present");
      throw new RuntimeException("Ingredient Not Present");
    }

    log.debug("Ingredient Found");
    Ingredient ingredientToBeDeleted = ingredientOptional.get();
    ingredientToBeDeleted.setRecipe(null);
    retrievedRecipe.getIngredients().remove(ingredientToBeDeleted);
    recipeRepository.save(retrievedRecipe);
  }
}
