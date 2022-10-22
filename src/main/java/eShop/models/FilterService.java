package eShop.models;

import eShop.data.DataSource;
import eShop.enums.Color;
import eShop.phones.Phone;

import java.util.List;
import java.util.Objects;

public class FilterService {
    List<Phone> phones = DataSource.phoneList();

    public void filterByName(String name) {
        int i = 1;
        //System.out.println(getCarHeader());
        for (Phone phone : phones) {
            if (phone.getName().equalsIgnoreCase(name)) {
                System.out.print(i + ". ");
                System.out.println(phone);
                i++;
            }
        }
    }

    public void filterByYear(Integer year) {
        int i = 1;
        //System.out.println(getCarHeader());
        for (Phone phone : phones) {
            if (Objects.equals(phone.getYear(), year)) {
                System.out.print(i + ". ");
                System.out.println(phone);
                i++;
            }
        }
    }
    public void filterByColor(Color color) {
        int i = 1;
        //System.out.println(getCarHeader());
        for (Phone phone : phones) {
            if (phone.getColor().equals(color)) {
                System.out.print(i + ". ");
                System.out.println(phone);
                i++;
            }
        }
    }
    public void filterBySize(float size) {
        int i = 1;
        //System.out.println(getCarHeader());
        for (Phone phone : phones) {
            if (phone.getSize() == size) {
                System.out.print(i + ". ");
                System.out.println(phone);
                i++;
            }
        }
    }



}
