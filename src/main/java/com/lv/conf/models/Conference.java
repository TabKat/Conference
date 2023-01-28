package com.lv.conf.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank(message = "Conference name mast be presented.")
    private String name;

    @NotNull
    @Positive
    private Long roomId;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "time_table_id")
    private List<TimeTable> timeTable;
}
