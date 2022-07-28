package recipes.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.exception.RecipeNotFoundException;
import recipes.model.Recipe;
import recipes.repository.RecipeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class RecipeService implements IRecipeService{


    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    /**
     * @param id
     * @return "recipes"
     */
    @Override
    public Optional<Recipe> findById(int id) {
        return Optional.ofNullable(recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    /**
     * @param id
     * @return
     */
    @Override
    public void deleteById(int id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
        }else {
            throw new RecipeNotFoundException();
        }
    }

    @Override
    public Recipe save(Recipe recipe){
        return recipeRepository.save(recipe);
    }



}
