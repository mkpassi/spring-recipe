package guru.springframework.recipe.springrecipe.converters;

import guru.springframework.recipe.springrecipe.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.recipe.springrecipe.commands.RecipeCommand;
import guru.springframework.recipe.springrecipe.domain.Recipe;

import lombok.Synchronized;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final CategoryCommandToCategory     categoryConveter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes           notesConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConveter,
                                 IngredientCommandToIngredient ingredientConverter,
                                 NotesCommandToNotes notesConverter) {
        this.categoryConveter    = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter      = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();

        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());

        if (notesConverter.convert(source.getNotes()) != null) {
            recipe.setNotes(notesConverter.convert(source.getNotes()));
        }else{
            recipe.setNotes(new Notes());
        }

        if ((source.getCategories() != null) && (source.getCategories().size() > 0)) {
            source.getCategories().forEach(category -> recipe.getCategories().add(categoryConveter.convert(category)));
        }

        if ((source.getIngredients() != null) && (source.getIngredients().size() > 0)) {
            source.getIngredients()
                  .forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return recipe;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
