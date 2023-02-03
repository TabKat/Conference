package com.lv.conf.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Participant first name must be presented")
    private String firstName;

    @NotBlank(message = "Participant last name must be presented")
    private String lastName;

    @NotNull
    private Long conferenceId;

    @NotNull
    private Long roomId;

    @NotNull
    private Long reservedSit;
}
