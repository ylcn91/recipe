package recipes.service;

import org.springframework.http.ResponseEntity;
import recipes.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface IRecipeService {

    Optional<Recipe> findById(int id);
    ResponseEntity<?> deleteById(int id);
    void save(Recipe recipe);

    ResponseEntity<?> findRecipeToUpdate(int recipeId, Recipe recipe);

    List<Recipe> getAllByCategory(String category);

    List<Recipe> getAllByName(String name);
}
