package eShop.data;

import eShop.models.Admin;
import eShop.models.User;
import eShop.phones.Phone;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<Phone> phoneList(){
        List<Phone> phones = new ArrayList<>();

        return phones;
    }

    public static List<User> userList(){
        List<User> users = new ArrayList<>();
        User user = new User("Horia", "1234", "2000-05-01");
        users.add(user);

        return users;
    }

    public static List<Admin> adminList(){
        List<Admin> admins = new ArrayList<>();

        return admins;
    }
}
