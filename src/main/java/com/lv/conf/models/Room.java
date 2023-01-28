package com.lv.conf.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @NotBlank(message = "Room's name mast be presented")
    private String name;

    @NonNull
    @Positive(message = "Sits number must be positive")
    @Min(value = 1, message = "Sits number can not be less that 1")
    private Integer sitsNumber;

}
