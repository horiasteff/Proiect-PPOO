package eShop.data;

import eShop.enums.Color;
import eShop.models.Admin;
import eShop.models.User;
import eShop.phones.Phone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataSource {

    public static Set<User> userList(){
        Set<User> users = new HashSet<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Users.txt"));
            String st;
            while ((st = br.readLine()) != null) {
                User user = new User(st.split(" ")[0], st.split(" ")[1], st.split(" ")[2]);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static Set<Admin> adminList(){
        Set<Admin> admins = new HashSet<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Admins.txt"));
            String st;
            while ((st = br.readLine()) != null) {
                Admin admin = new Admin(st.split(" ")[0], st.split(" ")[1], st.split(" ")[2]);
                admins.add(admin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return admins;
    }
    public static List<Phone> phoneList(){
        List<Phone> phones = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Phones.txt"));
            String st;
            while ((st = br.readLine()) != null) {
                Phone phone = new Phone(Integer.parseInt(st.split(" ")[0]), st.split(" ")[1],  st.split(" ")[2], Float.parseFloat(st.split(" ")[3]), Integer.parseInt(st.split(" ")[4]), Color.valueOf(st.split(" ")[5].toUpperCase(Locale.ROOT)), Long.parseLong(st.split(" ")[6]));
                phones.add(phone);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return phones;
    }
}
