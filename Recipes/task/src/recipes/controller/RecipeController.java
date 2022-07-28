package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.model.Recipe;
import recipes.service.RecipeService;

import javax.validation.Valid;

@RestController
public class RecipeController {

    /*
    Before H2 - DB
    Map<Integer, Recipe> dictionary =
            new HashMap<>();

    AtomicInteger key
            = new AtomicInteger(0);
     */

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @PostMapping(value = "/api/recipe/new")
    public ResponseEntity<?> addRecipe(@RequestBody @Valid Recipe recipe) {
        //dictionary.put(key.incrementAndGet(),recipe);
        recipeService.save(recipe);
        return new ResponseEntity<>("{ \"id\" : " + recipe.getId() + " }",HttpStatus.OK);
    }

    @GetMapping(value = "/api/recipe/{recipeId}")
    public ResponseEntity<?> findRecipe(@PathVariable int recipeId) {
        return new ResponseEntity<>(recipeService.findById(recipeId),HttpStatus.OK);
    }

    @DeleteMapping(value ="/api/recipe/{recipeId}" )
    public ResponseEntity<?> deleteRecipe(@PathVariable int recipeId){
        recipeService.deleteById(recipeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
