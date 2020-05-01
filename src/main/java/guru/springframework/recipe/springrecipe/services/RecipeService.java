package guru.springframework.recipe.springrecipe.services;

import java.util.Set;

import guru.springframework.recipe.springrecipe.commands.RecipeCommand;
import guru.springframework.recipe.springrecipe.domain.Recipe;

public interface RecipeService {

    Recipe getRecipeById(Long id);

    Set<Recipe> getRecipes();

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findRecipeCommandById(Long valueOf);

    void deleteById(Long id);
}


