package guru.springframework.recipe.springrecipe.services;

import guru.springframework.recipe.springrecipe.commands.RecipeCommand;
import guru.springframework.recipe.springrecipe.converters.RecipeCommandToRecipe;
import guru.springframework.recipe.springrecipe.converters.RecipeToRecipeCommand;
import guru.springframework.recipe.springrecipe.domain.Recipe;
import guru.springframework.recipe.springrecipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class RecipeServiceIT {

  private static final String NEW_DESRIPTION = "New Description";

  @Autowired
  RecipeRepository recipeRepository;

  @Autowired
  RecipeCommandToRecipe recipeCommandToRecipe;

  @Autowired
  RecipeToRecipeCommand recipeToRecipeCommand;

  @Autowired
  RecipeService recipeService;


  @BeforeEach
  void setUp() {}

  @Test
  @Transactional
  void saveRecipe() {
    //Given
    Iterable<Recipe> recipes = recipeRepository.findAll();
    Recipe recipe = recipes.iterator().next();
    log.info("###################################################");
    log.info("Recipe Retrieved : {}", recipe);
    RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

    //When
    recipeCommand.setDescription(NEW_DESRIPTION);
    RecipeCommand savedRecipeCommand = recipeService.saveRecipe(recipeCommand);

    // Then
    log.info("###################################################");
    log.info("RecipeCommand saved : {}", savedRecipeCommand);
    Assertions.assertEquals(NEW_DESRIPTION, savedRecipeCommand.getDescription());
    Assertions.assertEquals(recipe.getId(), savedRecipeCommand.getId());
  }
}
