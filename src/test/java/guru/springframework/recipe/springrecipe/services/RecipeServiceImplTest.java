package guru.springframework.recipe.springrecipe.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import guru.springframework.recipe.springrecipe.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import guru.springframework.recipe.springrecipe.commands.RecipeCommand;
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
    public void deleteRecipeById() {
        Long idOfRecipeToBeDeleted = 2l;
        recipeService.deleteById(2l);
        verify(recipeRepository, times(1)).deleteById(eq(idOfRecipeToBeDeleted));
    }

    @Test
    public void saveRecipeCommand() {
        Long   recipe_id = 1l;
        Recipe recipe    = new Recipe();
        recipe.setId(recipe_id);
        RecipeCommand recipeCommand = new RecipeCommand();

        recipeCommand.setId(recipe_id);
        when(recipeCommandToRecipe.convert(any())).thenReturn(recipe);
        when(recipeRepository.save(any())).thenReturn(recipe);
        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);
        assertNotNull(recipeService.saveRecipeCommand(recipeCommand));
    }

    @Test
    public void getRecipes() throws Exception {
        Recipe      recipe     = new Recipe();
        Set<Recipe> recipeData = new HashSet<>();

        recipeData.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipes = recipeService.getRecipes();

        System.out.println(recipes.size());
        assertNotNull(recipes);

        // To verify how many times a method is called use Mockito.verify and Mockito.times.
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void getRecipesByIdTest() throws Exception {
        Long   recipeId = 1l;
        Recipe recipe   = new Recipe();

        recipe.setId(recipeId);

        Set<Recipe> recipeData = new HashSet<>();

        recipeData.add(recipe);
        when(recipeRepository.findById(eq(recipeId))).thenReturn(Optional.ofNullable(recipe));

        Recipe savedRecipe = recipeService.getRecipeById(recipeId);


        System.out.println(savedRecipe);
        assertNotNull(savedRecipe);

        // To verify how many times a method is called use Mockito.verify and Mockito.times.
        verify(recipeRepository, times(1)).findById(eq(recipeId));
    }

    @Test
    public void testNotFoundException () {
        RuntimeException exception = Assertions.assertThrows(NotFoundException.class,
                () -> {
          Optional<Recipe> emptyOptional = Optional.empty();
          when(recipeRepository.findById(anyLong())).thenReturn(emptyOptional);
          RecipeCommand recipe = recipeService.findRecipeCommandById(1l);
        });

    }

    @BeforeEach
    public void setUp() throws Exception {}
}



