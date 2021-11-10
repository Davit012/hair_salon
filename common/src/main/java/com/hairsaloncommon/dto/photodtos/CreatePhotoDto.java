package com.hairsaloncommon.dto.photodtos;

import com.hairsaloncommon.model.Worker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreatePhotoDto {


    @NotEmpty(message = "photo is required")
    private String value;
    @NotNull
    private Worker worker;

}
