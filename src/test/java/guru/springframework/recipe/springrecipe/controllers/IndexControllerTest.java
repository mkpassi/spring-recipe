package guru.springframework.recipe.springrecipe.controllers;

import guru.springframework.recipe.springrecipe.domain.Recipe;
import guru.springframework.recipe.springrecipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MockMvcResultHandlersDsl;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@SpringBootTest
public class IndexControllerTest {

    IndexController indexController;

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController  = new IndexController(recipeService);
    }

    @Test
    public void testIndexPage() {
        Long recipeId = 4l;
        Set<Recipe> recipeSet = new HashSet<>();
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipeSet.add(recipe);
        Mockito.when(recipeService.getRecipes()).thenReturn(recipeSet);
        model.addAttribute(recipeSet);
        Mockito.when(model.addAttribute(Mockito.anyString(),Mockito.anyString())).thenReturn(model);
        String indexPage = indexController.getIndexPage(model);
        assertNotNull(model);
        assertNotNull(indexPage);
        assertEquals("index", indexPage);
        Mockito.verify(model,Mockito.times(1)).addAttribute(Mockito.eq("recipes"),Mockito.anySet());
        Mockito.verify(recipeService,Mockito.times((1))).getRecipes();
    }

    @Test
    public void testArguemntCapture(){
        //Given
        Long recipeId = 4l;
        Long recipe2Id = 5l;
        Set<Recipe> recipeSet = new HashSet<>();
        Recipe r1 = new Recipe();
        r1.setId(recipeId);
        Recipe r2 = new Recipe();
        r2.setId(recipe2Id);
        recipeSet.add(r1);
        recipeSet.add(r2);
        Mockito.when(recipeService.getRecipes()).thenReturn(recipeSet);

        //ArguementCaptor can be used to capture the variables.
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String index = indexController.getIndexPage(model);

        //then
        assertEquals("index", index);
        Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("recipes"), argumentCaptor.capture());
        Mockito.verify(recipeService, Mockito.times((1))).getRecipes();
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }


    //Controller testing using MockMVc
    @Test
    public void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("index"));
    }
}
