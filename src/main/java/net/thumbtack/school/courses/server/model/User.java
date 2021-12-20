package net.thumbtack.school.courses.server.model;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@EqualsAndHashCode(exclude = {"idUser"})
public class User implements Serializable {

    @Getter @Expose
    private String login;
    @Getter @Expose
    private String password;
    @Getter @Expose
    private final String lastName;
    @Getter @Expose
    private final String firstName;
    @Getter @Expose
    private final String patronymic;
    @Expose @Getter @Setter
    private int idUser;

    public User(String login, String password, String lastName, String firstName, String patronymic) {
        this.login = login;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
    }

    public void update(String updLogin, String updPass){
        this.login = updLogin;
        this.password = updPass;
    }
}
