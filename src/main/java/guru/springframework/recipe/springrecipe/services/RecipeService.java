package guru.springframework.recipe.springrecipe.services;

import java.util.Set;

import guru.springframework.recipe.springrecipe.commands.RecipeCommand;
import guru.springframework.recipe.springrecipe.domain.Recipe;

public interface RecipeService {

    Recipe getRecipeById(Long id);

    Set<Recipe> getRecipes();

    RecipeCommand saveRecipe(RecipeCommand recipeCommand);
}


//~ Formatted by Jindent --- http://www.jindent.com
