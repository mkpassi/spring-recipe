package guru.springframework.recipe.springrecipe.services;

import guru.springframework.recipe.springrecipe.domain.Recipe;
import guru.springframework.recipe.springrecipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

import static org.junit.Assert.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getRecipes() throws Exception{
        Recipe recipe = new Recipe();
        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);

        Mockito.when(recipeRepository.findAll()).thenReturn(recipeData);
        Set<Recipe> recipes = recipeService.getRecipes();
        System.out.println(recipes.size());
        assertNotNull(recipes);
       //To verify how many times a method is called use Mockito.verify and Mockito.times.
        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getRecipesByIdTest() throws Exception{
        Long recipeId = 1l;
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);

        Mockito.when(recipeRepository.findById(Mockito.eq(recipeId))).thenReturn(Optional.ofNullable(recipe));
        Recipe savedRecipe = recipeService.getRecipeById(recipeId);
        System.out.println(savedRecipe);
        assertNotNull(savedRecipe);
        //To verify how many times a method is called use Mockito.verify and Mockito.times.
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.eq(recipeId));
    }
}
