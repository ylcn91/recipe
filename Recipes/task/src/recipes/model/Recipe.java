package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @NonNull
    @NotEmpty
    @NotBlank
    private String name;

    @NonNull
    @NotEmpty
    @NotBlank
    private String category;

    @CreationTimestamp
    private LocalDateTime date;

    @NonNull
    @NotEmpty
    @NotBlank
    private String description;

    @Size(min = 1)
    @NotNull
    @OrderColumn
    @ElementCollection(fetch = FetchType.LAZY)
    private String[] ingredients;

    @Size(min = 1)
    @NotNull
    @OrderColumn
    @ElementCollection(fetch = FetchType.LAZY)
    private String[] directions;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonIgnore
    private User owner;
}
