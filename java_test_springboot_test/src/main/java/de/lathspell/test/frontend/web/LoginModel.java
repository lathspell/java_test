package de.lathspell.test.frontend.web;

import lombok.Data;

@Data
public class LoginModel {
    private String name;
    private String password;
    private String msg;
}
