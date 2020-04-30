package guru.springframework.recipe.springrecipe.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import guru.springframework.recipe.springrecipe.commands.RecipeCommand;
import guru.springframework.recipe.springrecipe.converters.RecipeCommandToRecipe;
import guru.springframework.recipe.springrecipe.converters.RecipeToRecipeCommand;
import guru.springframework.recipe.springrecipe.domain.Recipe;
import guru.springframework.recipe.springrecipe.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository      recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository      = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipe(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved Recipe : {}", savedRecipe);
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public Recipe getRecipeById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (recipeOptional.isPresent()) {
            return recipeOptional.get();
        } else {
            throw new RuntimeException("Recipe Not Found");
        }
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I,am in the Service");

        Set<Recipe> recipeSet = new HashSet<>();

        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);

        return recipeSet;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
