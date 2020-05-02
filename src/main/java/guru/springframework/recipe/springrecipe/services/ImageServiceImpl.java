package guru.springframework.recipe.springrecipe.services;

import guru.springframework.recipe.springrecipe.domain.Recipe;
import guru.springframework.recipe.springrecipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by jt on 7/3/17.
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {

        log.debug("Received a file");

        try{
            Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
            if(recipeOptional.isPresent()){
                Recipe recipe = recipeOptional.get();
                Byte[] imageBytesObject = new Byte[file.getBytes().length];
                int i=0;
                for(byte b : file.getBytes()){
                    imageBytesObject[i++] = b;
                }
                recipe.setImage(imageBytesObject);
                recipeRepository.save(recipe);
            }
        }catch (IOException | NoSuchElementException exception){
            log.debug("Exception Caught with message : {}", exception.getMessage());
            exception.printStackTrace();
        }

    }
}
