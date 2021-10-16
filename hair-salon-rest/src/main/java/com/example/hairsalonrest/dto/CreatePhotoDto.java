package com.example.hairsalonrest.dto;

import com.hairsaloncommon.model.Worker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreatePhotoDto {


    private String value;
    private Worker worker;

}
