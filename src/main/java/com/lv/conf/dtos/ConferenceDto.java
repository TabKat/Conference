package com.lv.conf.dtos;

import com.lv.conf.models.Conference;
import com.lv.conf.models.Sit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceDto {
    Conference conference;
    List<Sit> sits;
}
