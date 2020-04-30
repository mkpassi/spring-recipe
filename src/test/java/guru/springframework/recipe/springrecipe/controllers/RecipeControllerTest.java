package guru.springframework.recipe.springrecipe.controllers;

import guru.springframework.recipe.springrecipe.domain.Recipe;
import guru.springframework.recipe.springrecipe.repositories.RecipeRepository;
import guru.springframework.recipe.springrecipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeService recipeService;

    @InjectMocks
    RecipeController recipeController;

    Long recipeId = 1l;

    Recipe recipe ;

    MockMvc mvc;

    @BeforeEach
    public void setup(){
        recipe = new Recipe();
        recipe.setId(recipeId);
        mvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void testGetRecipe() throws Exception {
       Mockito.when(recipeService.getRecipeById(recipeId)).thenReturn(recipe);

       mvc.perform(MockMvcRequestBuilders.get("/recipe/show/1"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("recipe/show"));
    }
}
