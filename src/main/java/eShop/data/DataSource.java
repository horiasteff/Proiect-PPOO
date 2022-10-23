package eShop.data;

import eShop.models.Admin;
import eShop.models.User;
import eShop.phones.Phone;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<Phone> phoneList(){
        List<Phone> phones = new ArrayList<>();

        return phones;
    }

    public static List<User> userList(){
        List<User> users = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Users.txt"));
            String st;
            while ((st = br.readLine()) != null) {
                User user = new User(st.split(" ")[0], st.split(" ")[1], st.split(" ")[2]);
                users.add(user);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return users;
    }

    public static List<Admin> adminList(){
        List<Admin> admins = new ArrayList<>();

        return admins;
    }
}
