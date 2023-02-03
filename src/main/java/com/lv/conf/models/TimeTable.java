package com.lv.conf.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @NotNull
    @NotBlank(message = "Conference id mast be presented.")
    private String conferenceId;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;
}
