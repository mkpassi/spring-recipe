package guru.springframework.recipe.springrecipe.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import guru.springframework.recipe.springrecipe.commands.RecipeCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.*;

import guru.springframework.recipe.springrecipe.converters.RecipeCommandToRecipe;
import guru.springframework.recipe.springrecipe.converters.RecipeToRecipeCommand;
import guru.springframework.recipe.springrecipe.domain.Recipe;
import guru.springframework.recipe.springrecipe.repositories.RecipeRepository;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {
    @Mock
    RecipeRepository      recipeRepository;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @InjectMocks
    RecipeServiceImpl     recipeService;

    @Test
    public void saveRecipeCommand() {
      Long recipe_id = 1l;
      Recipe recipe = new Recipe();
      recipe.setId(recipe_id);

      RecipeCommand recipeCommand = new RecipeCommand();
      recipeCommand.setId(recipe_id);

      Mockito.when(recipeCommandToRecipe.convert(Mockito.any())).thenReturn(recipe);
      Mockito.when(recipeRepository.save(Mockito.any())).thenReturn(recipe);
      Mockito.when(recipeToRecipeCommand.convert(Mockito.any())).thenReturn(recipeCommand);

      assertNotNull(recipeService.saveRecipe(recipeCommand));

    }

    @Test
    public void getRecipes() throws Exception {
        Recipe      recipe     = new Recipe();
        Set<Recipe> recipeData = new HashSet<>();

        recipeData.add(recipe);
        Mockito.when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipes = recipeService.getRecipes();

        System.out.println(recipes.size());
        assertNotNull(recipes);

        // To verify how many times a method is called use Mockito.verify and Mockito.times.
        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getRecipesByIdTest() throws Exception {
        Long   recipeId = 1l;
        Recipe recipe   = new Recipe();

        recipe.setId(recipeId);

        Set<Recipe> recipeData = new HashSet<>();

        recipeData.add(recipe);
        Mockito.when(recipeRepository.findById(Mockito.eq(recipeId))).thenReturn(Optional.ofNullable(recipe));

        Recipe savedRecipe = recipeService.getRecipeById(recipeId);

        System.out.println(savedRecipe);
        assertNotNull(savedRecipe);

        // To verify how many times a method is called use Mockito.verify and Mockito.times.
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.eq(recipeId));
    }

    @BeforeEach
    public void setUp() throws Exception {}
}


