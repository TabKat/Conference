package com.lv.conf.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Sit sit = (Sit) o;
        return id != null && Objects.equals(id, sit.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
