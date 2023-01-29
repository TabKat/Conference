package com.lv.conf.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Positive(message = "Reserved sit number must be positive")
    @Min(value = 1, message = "Sit number can not be less that 1")
    private Long reservedSit;

    @NotNull
    private Long conferenceId;

    @NotNull
    private Long roomId;
}
