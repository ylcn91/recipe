package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;

    @NonNull
    @NotEmpty
    @NotBlank
    private String name;

    @NonNull
    @NotEmpty
    @NotBlank
    private String description;

    @Size(min = 1)
    @NotNull
    private String[] ingredients;

    @Size(min = 1)
    @NotNull
    private String[] directions;
}
