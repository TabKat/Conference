package com.lv.conf.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank(message = "Participant first name must be presented")
    private String firstName;

    @NotNull
    @NotBlank(message = "Participant last name must be presented")
    private String lastName;

    @OneToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @OneToOne
    @JoinColumn(name = "sit_id")
    private Room room;
}
