package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.model.Recipe;
import recipes.service.RecipeService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
        return new ResponseEntity<>(Map.of("id",recipe.getId()) ,HttpStatus.OK);
    }

    @GetMapping(value = "/api/recipe/{recipeId}")
    public ResponseEntity<?> findRecipe(@PathVariable int recipeId) {
        return new ResponseEntity<>(recipeService.findById(recipeId),HttpStatus.OK);
    }

    @DeleteMapping(value ="/api/recipe/{recipeId}" )
    public ResponseEntity<?> deleteRecipe(@PathVariable int recipeId){

        return recipeService.deleteById(recipeId);
    }


    @PutMapping(value = "/api/recipe/{recipeId}")
    public ResponseEntity<?> updateRecipe(@PathVariable int recipeId, @Valid @RequestBody Recipe recipe) {
        return recipeService.findRecipeToUpdate(recipeId,recipe);
    }

    @GetMapping(path = "api/recipe/search")
    public ResponseEntity<List<Recipe>> searchRecipe(@RequestParam(required = false) String category,
                                                     @RequestParam(required = false) String name) {
        //will refactor this line ****
        if ((category != null && name != null) || (category == null && name == null))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(category != null ?
                recipeService.getAllByCategory(category) :
                recipeService.getAllByName(name), HttpStatus.OK);
    }



}
