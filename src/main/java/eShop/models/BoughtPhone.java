package eShop.models;

import eShop.phones.Phone;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class BoughtPhone {

    protected Phone phone;
    protected LocalDate pickUpDate;
    protected boolean isCurrentlyBought;

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public LocalDate getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDate pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public boolean isCurrentlyBought() {
        return isCurrentlyBought;
    }

    public void setCurrentlyBought(boolean currentlyBought) {
        isCurrentlyBought = currentlyBought;
    }

    @Override
    public String toString() {
        return "BoughtPhone: " +
                "Phone=" + phone + "\n" +
                StringUtils.center(String.valueOf(pickUpDate), 15, " ");
    }
}
