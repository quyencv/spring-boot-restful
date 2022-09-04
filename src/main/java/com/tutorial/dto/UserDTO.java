package com.tutorial.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.tutorial.constant.Role;
import com.tutorial.utils.ValidatorUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {

    private static final long serialVersionUID = -7806223629030945599L;

    private String name;

    @NotEmpty(message = "login.notempty")
    @Pattern(regexp = ValidatorUtils.LOGIN_REGEXP, message = "password.pattern")
    private String username;

    @NotEmpty(message = "password.notempty")
    @Pattern(regexp = ValidatorUtils.PASSWORD_REGEXP, message = "password.pattern")
    private String password;

    private Role role;

    private String clientId;

    private String secretKey;

    private String token;
}
