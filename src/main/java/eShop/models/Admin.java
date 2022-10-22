package eShop.models;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class Admin {
    protected String name;
    protected String password;
    protected LocalDate birthDate;

    public Admin(String name, String password, String birthDate) {
        this.name = name;
        this.password = password;
        this.birthDate = LocalDate.parse(birthDate);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int calculateAge(){
        return LocalDate.now().getYear()- birthDate.getYear();
    }

    @Override
    public String toString() {
        return "Admin: " +
                StringUtils.center(name,10);
    }
}
