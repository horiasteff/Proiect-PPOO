package eShop.models;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class User {

    protected String name;
    protected String password;
    protected LocalDate birthDate;


    public User(String name, String password, String birthDate) {
        this.name = name;
        this.password = password;
        this.birthDate = LocalDate.parse(birthDate);
    }

    public int calculateAge(){
        return LocalDate.now().getYear()- birthDate.getYear();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return StringUtils.center(name,10) +
         StringUtils.center(password,10) +
         StringUtils.center(String.valueOf(birthDate),10) ;
    }

}
