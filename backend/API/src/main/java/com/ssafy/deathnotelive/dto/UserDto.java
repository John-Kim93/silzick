package com.ssafy.deathnotelive.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("Member")
public class UserDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRegist {
        @ApiModelProperty(name = "유저 ID", example = "ssafy")
        private String userId;
        @ApiModelProperty(name = "유저 Email", example = "ssafy@ssafy.com")
        private String email;
        @ApiModelProperty(name = "유저 Password")
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        @ApiModelProperty(name = "유저 PK", example = "1")
        private Long userPk;
        @ApiModelProperty(name = "유저 Id", example = "ssafy")
        private String userId;
        @ApiModelProperty(name = "유저 Email", example = "ssafy@ssafy.com")
        private String email;
    }
}
