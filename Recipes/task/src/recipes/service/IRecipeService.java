package recipes.service;

import recipes.model.Recipe;

import java.util.Optional;

public interface IRecipeService {

    Optional<Recipe> findById(int id);
    void deleteById(int id);
    Recipe save(Recipe recipe);

}
