package guru.springframework.recipe.springrecipe.services;

import guru.springframework.recipe.springrecipe.commands.IngredientCommand;

public interface IngredientService {

    public IngredientCommand findIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
