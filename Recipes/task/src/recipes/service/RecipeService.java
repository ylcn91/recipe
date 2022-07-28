package recipes.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.config.IAuthenticationFacade;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.repository.RecipeRepository;
import recipes.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeService implements IRecipeService{


    private final RecipeRepository recipeRepository;
    private final IAuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    public RecipeService(RecipeRepository recipeRepository, IAuthenticationFacade authenticationFacade, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.authenticationFacade = authenticationFacade;
        this.userRepository = userRepository;
    }

    private User getLoggedUser() {
        Object auth = authenticationFacade.getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Object loggedUser = authenticationFacade.getAuthentication().getPrincipal();
        return userRepository.findById(((User) loggedUser).getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @Override
    public Optional<Recipe> findById(int id) {
        return Optional.ofNullable(recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }



    @Override
    public ResponseEntity<?> deleteById(int id) {
        var recipe = recipeRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (recipe.getOwner().equals(getLoggedUser())) {
            recipeRepository.deleteById(recipe.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public void save(Recipe recipe){
         recipe.setOwner(getLoggedUser());
         recipeRepository.save(recipe);
    }



    @Override
    public ResponseEntity<?> findRecipeToUpdate(int recipeId, Recipe updatedRecipe) {
        Recipe newRecipe =
                 recipeRepository.findById(recipeId)
                        .orElseThrow(()
                                -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        /*
        newRecipe.setName(updatedRecipe.getName());
        newRecipe.setCategory(updatedRecipe.getCategory());
        newRecipe.setDescription(updatedRecipe.getDescription());
        newRecipe.setDirections(updatedRecipe.getDirections());
        newRecipe.setIngredients(updatedRecipe.getIngredients());
        newRecipe.setDate(LocalDateTime.now());
        recipeRepository.save(newRecipe);
         */

        if (newRecipe.getOwner().equals(getLoggedUser())) {
            newRecipe.setDate(LocalDateTime.now());
            newRecipe.setCategory(updatedRecipe.getCategory());
            newRecipe.setDescription(updatedRecipe.getDescription());
            newRecipe.setName(updatedRecipe.getName());
            newRecipe.setIngredients(updatedRecipe.getIngredients());
            newRecipe.setDirections(updatedRecipe.getDirections());
            recipeRepository.save(newRecipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }


    @Override
    public List<Recipe> getAllByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }


    @Override
    public List<Recipe> getAllByName(String name) {
        return recipeRepository.findDistinctByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

}
