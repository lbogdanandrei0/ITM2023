package com.itm.api.request.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BreakRequestDTO {

    private String place;

    private String comment;

    private List<String> users;

}
