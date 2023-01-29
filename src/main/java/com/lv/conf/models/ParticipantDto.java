package com.lv.conf.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {
    private String firstName;
    private String lastName;
    private Long sitNumber;
    private Conference conference;
}
