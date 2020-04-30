package guru.springframework.recipe.springrecipe.services;

import guru.springframework.recipe.springrecipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe getRecipeById(Long id);
}
