package guru.springframework.recipe.springrecipe.services;

import guru.springframework.recipe.springrecipe.commands.IngredientCommand;
import guru.springframework.recipe.springrecipe.converters.IngredientCommandToIngredient;
import guru.springframework.recipe.springrecipe.converters.IngredientToIngredientCommand;
import guru.springframework.recipe.springrecipe.domain.Ingredient;
import guru.springframework.recipe.springrecipe.domain.Recipe;
import guru.springframework.recipe.springrecipe.repositories.IngredientRepository;
import guru.springframework.recipe.springrecipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientToIngredientCommand ingredientCommand;

    @Mock
    IngredientRepository ingredientRepository;

    @InjectMocks
    IngredientServiceImpl ingredientService;


    @Test
    public void findByRecipeIdAndReceipeIdHappyPath() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        IngredientCommand ingredientCommandObject = new IngredientCommand();
        ingredientCommandObject.setId(3l);
        ingredientCommandObject.setRecipeId(1l);


        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(ingredientCommand.convert(any())).thenReturn(ingredientCommandObject);


        //then
        IngredientCommand ingredientCommand = ingredientService.findIngredientByRecipeIdAndIngredientId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
    }

}
