package recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Email
    @NotBlank
    @Pattern(regexp = ".*@.*\\..+")
    private String email;


    @NotBlank
    @Size(min = 8)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @ToString.Exclude
    private List<Recipe> ownedRecipes = new ArrayList<>();


}
