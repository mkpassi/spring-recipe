package guru.springframework.recipe.springrecipe.controllers;

import guru.springframework.recipe.springrecipe.commands.RecipeCommand;
import guru.springframework.recipe.springrecipe.exceptions.NotFoundException;
import guru.springframework.recipe.springrecipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;


    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    private static final String RECIPE_FORM_URL = "recipe/recipeform";

    @GetMapping
    @RequestMapping("/recipe/{id}/show")
    public String getRecipeById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.getRecipeById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("recipe/new")
    public String AddNewRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return RECIPE_FORM_URL;
    }


    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){

        if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return RECIPE_FORM_URL;
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }



    @GetMapping
    @RequestMapping("recipe/{id}/update")
    public String updateExistingRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));
        return RECIPE_FORM_URL;
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteExistingRecipe(@PathVariable String id, Model model){
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){
        log.error("Handling Not Found Exception");
        log.error("Exception message {}", exception.getMessage());
        ModelAndView modelANdView = new ModelAndView();
        modelANdView.setViewName("404error");
        modelANdView.addObject("exception", exception);
        return modelANdView;
    }


}
