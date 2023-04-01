package com.itm.api.user.model.dto;

import com.itm.api.base.validation.PatchValidation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
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
    @Null(groups = {PatchValidation.class})
    private String profilePicLocation;
    @Null(groups = {PatchValidation.class})
    private String profilePicName;
    @Null(groups = {PatchValidation.class})
    private UUID externalUuid;
    
}
