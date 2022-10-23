package eShop.models;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {

    protected String name;
    protected String password;
    protected LocalDate birthDate;
    protected List<BoughtPhone> boughtPhones = new ArrayList<>();


    public User(String name, String password, String birthDate) {
        this.name = name;
        this.password = password;
        this.birthDate = LocalDate.parse(birthDate);
    }

    public List<BoughtPhone> getCurrentlyBoughtPhones() {
        List<BoughtPhone> currentlyBoughtPhones = new ArrayList<>();
        for (BoughtPhone boughtPhones : boughtPhones) {
            if (boughtPhones.isCurrentlyBought()) {
                currentlyBoughtPhones.add(boughtPhones);
            }
        }
        return currentlyBoughtPhones;
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
