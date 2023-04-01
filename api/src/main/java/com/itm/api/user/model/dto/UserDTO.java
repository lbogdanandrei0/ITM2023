package com.itm.api.user.model.dto;

import com.itm.api.base.validation.PatchValidation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public class UserDTO {

    @NotBlank(groups = {PatchValidation.class})
    private String username;
    private String firstName;
    private String lastName;
    private String department;
    private String officeName;
    private String teamName;
    private Integer floorNumber;
    private String profilePicLocation;
    private String profilePicName;
    private UUID externalUuid;
    
}
