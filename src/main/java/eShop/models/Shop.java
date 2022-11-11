package eShop.models;

import eShop.data.DataSource;
import eShop.enums.Color;
import eShop.exceptions.UserAlreadyExists;
import eShop.phones.*;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class Shop {

    Scanner sc = new Scanner(System.in);
    Set<User> users = DataSource.userList();
    Set<Admin> admins = DataSource.adminList();
    FilterService service = new FilterService();
    List<Phone> phones = DataSource.phoneList();
    Features feature = new Features();
    User currentUser = null;
    Admin currentAdmin = null;
    String output;
    String option;
    String startOption;
    String status;
    long thePrice = 0;

    public void start() {
        System.out.println(" -----------------------------------------------");
        System.out.println("|              Welcome to the e-Shop             |");
        System.out.println(" -----------------------------------------------");
        System.out.println();
        System.out.println("                    START MENU                   ");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. View phones only");
        System.out.println("4. Exit");
        chooseStart();
        if (status != null) {
            if (status.equalsIgnoreCase("user")) {
                showUserMenu();
            } else {
                showAdminMenu();
            }
        }
    }

    private void chooseStart() {
        boolean isValid = false;
        do {
            startOption = sc.next();
            switch (startOption) {
                case "1":
                    isValid = true;
                    login();
                    if (status.equalsIgnoreCase("user")) {
                        showUserMenu();
                    } else {
                        showAdminMenu();
                    }
                    break;
                case "2":
                    isValid = true;
                    register();
                    start();
                    break;
                case "3":
                    isValid = true;
                    getAllPhones();
                    break;
                case "4":
                    isValid = true;
                    exit();
                    break;
                default:
                    System.out.println("Please enter a valid choice");
                    isValid = false;
                    break;
            }

        } while (!isValid);
    }

    private void register() {
        System.out.println("What is your name?");
        String username = sc.next();
        System.out.println("Please choose a password for your account");
        String password = sc.next();
        System.out.println("Enter your date of birth (YYYY-MM-DD)");
        String date = sc.next();

        User user = new User(username, password, date);
        for (User u : users) {
            if (u.getName().equals(user.getName())) {
                try {
                    throw new UserAlreadyExists("This user already exists");
                } catch (UserAlreadyExists e) {
                    e.printStackTrace();
                    start();
                }
            }
        }
        users.add(user);
        try {
            PrintWriter out = new PrintWriter(new FileWriter("Users.txt", true));
            out.append(user.getName()).append(" ").append(user.getPassword()).append(" ").append(String.valueOf(user.getBirthDate())).append("\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login() {
        boolean loginSuccess = false;
        do {
            System.out.println("Please enter the username");
            String username = sc.next();
            System.out.println("Please enter the password");
            String password = sc.next();

            for (User user : users) {
                if (username.equalsIgnoreCase(user.getName()) && password.equalsIgnoreCase(user.getPassword())) {
                    System.out.println("User: " + user.getName() + "\n");
                    loginSuccess = true;
                    currentUser = user;
                    status = "user";
                }
            }
            for (Admin admin : admins) {
                if (username.equalsIgnoreCase(admin.getName()) && password.equalsIgnoreCase(admin.getPassword())) {
                    System.out.println("Admin: " + admin.getName() + "\n");
                    loginSuccess = true;
                    currentAdmin = admin;
                    status = "admin";
                }
            }
            if (!loginSuccess) {
                System.out.println("Incorrect Username/Password");
            }
        } while (!loginSuccess);
    }

    private void showAdminMenu() {
        System.out.println(" -----------------------------------------------");
        System.out.println("|              Welcome to the e-Shop             |");
        System.out.println("|        ~  You are logged as an ADMIN  ~        |");
        System.out.println(" -----------------------------------------------");
        System.out.println();
        System.out.println("                    MAIN MENU                   ");
        System.out.println("1. List all phones");
        System.out.println("2. Add a phone");
        System.out.println("3. List all bought phones");
        System.out.println("4. Statistics");
        System.out.println("5. Change password");
        System.out.println("6. Show personal info");
        System.out.println("7. Logout");
        System.out.println("8. Exit");
        System.out.println();

        System.out.println("Enter your option");
        boolean isValid = false;
        do {

            option = sc.next();

            switch (option) {
                case "1" -> {
                    getAllPhones();
                    System.out.println("Want to go back to previous menu?");
                    output = sc.next();
                    if (output.equalsIgnoreCase("yes")) {
                        showAdminMenu();
                    }
                    isValid = true;
                }
                case "2" -> {
                    addAPhone();
                    isValid = true;
                }
                case "3" -> {
                    getBoughtPhones();
                    System.out.println("Want to go back to previous menu?");
                    output = sc.next();
                    if (output.equalsIgnoreCase("yes")) {
                        showAdminMenu();
                    }
                    isValid = true;
                }
                case "4" -> {
                    statistics();
                    System.out.println("Do you want to go back to previous menu?");
                    output = sc.next();
                    if (output.equalsIgnoreCase("yes")) {
                        showAdminMenu();
                    } else if (output.equalsIgnoreCase("no")){
                        exit();
                    }else {
                        System.out.println("You entered a wrong number!");
                    }
                    isValid = true;
                }
                case "5" -> {
                    changePassword();
                    System.out.println("Do you want to go back to previous menu?");
                    output = sc.next();
                    if (output.equalsIgnoreCase("yes")) {
                        showUserMenu();
                    } else if (output.equalsIgnoreCase("no")) {
                        System.out.println("Do you want to exit or re-login?");
                        output = sc.next();
                        if (output.equalsIgnoreCase("login")) {
                            login();
                            showUserMenu();
                        } else {
                            break;
                        }
                    }
                    isValid = true;
                }
                case "6" -> {
                    showInfo();
                    isValid = true;
                }
                case "7" -> {
                    login();
                    if (status.equalsIgnoreCase("user")) {
                        showUserMenu();
                    } else {
                        showAdminMenu();
                    }
                    isValid = true;
                }
                case "8" -> isValid = true;
                default -> {
                    System.out.println("Please enter a valid choice");
                    isValid = false;
                }
            }
        } while (!isValid);

    }

    private void addAPhone() {
        int indexNumber = getIndexOfNumber();
        Phone phone = null;
        System.out.println("What is the type of the phone? You can choose between Huawei, Samsung, IPhone, Xiaomi");
        String phoneName = sc.next();
        System.out.println("What is the model of the phone");
        String phoneModel = sc.next();
        System.out.println("What is the size of the phone?");
        float phoneSize = Float.parseFloat(sc.next());
        System.out.println("What is the fabrication year?");
        int phoneYear = Integer.parseInt(sc.next());
        System.out.println("What is the color?");
        Color color = Color.valueOf(sc.next().toUpperCase(Locale.ROOT));
        System.out.println("What is the base price of the phone?");
        long phonePrice = Long.parseLong(sc.next());

        if (phoneName.equalsIgnoreCase("iphone")) {
            phone = new IPhone(indexNumber, phoneModel, phoneSize, phoneYear, color, phonePrice);
        } else if (phoneName.equalsIgnoreCase("huawei")) {
            phone = new Huawei(indexNumber, phoneModel, phoneSize, phoneYear, color, phonePrice);
        } else if (phoneName.equalsIgnoreCase("samsung")) {
            phone = new Samsung(indexNumber, phoneModel, phoneSize, phoneYear, color, phonePrice);
        } else if (phoneName.equalsIgnoreCase("xiaomi")) {
            phone = new Xiaomi(indexNumber, phoneModel, phoneSize, phoneYear, color, phonePrice);
        } else {
            System.out.println("Please enter a valid name");
            addAPhone();
        }

        if (phone != null) {
            phones.add(phone);
            try {
                PrintWriter out = new PrintWriter(new FileWriter("Phones.txt", true));
                out.append(String.valueOf(phone.getIndex())).append(" ").append(phone.getName()).append(" ").append(String.valueOf(phone.getSize())).append(" ").append(String.valueOf(phone.getYear())).append(" ").append(String.valueOf(phone.getColor())).append(" ").append(String.valueOf(phone.getBasePrice())).append("\n");
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private int getIndexOfNumber() {

        return phones.size() + 1;
    }

    private void showUserMenu() {

        System.out.println(" -----------------------------------------------");
        System.out.println("|              Welcome to the e-Shop             |");
        System.out.println("|         ~  You are logged as an USER  ~        |");
        System.out.println(" -----------------------------------------------");
        System.out.println();
        System.out.println("                    MAIN MENU                   ");
        System.out.println("1. List all phones");
        System.out.println("2. List available phones");
        System.out.println("3. List all bought phones");
        System.out.println("4. Change password");
        System.out.println("5. Show personal info");
        System.out.println("6. Filter phones");
        System.out.println("7. Logout");
        System.out.println("8. Exit");

        System.out.println();
        System.out.println("Enter your option");

        boolean isValidChoice = false;

        do {
            String choice = sc.next();
            switch (choice) {
                case "1" -> {
                    getAllPhones();
                    System.out.println();
                    buying();
                    isValidChoice = true;
                }
                case "2" -> {
                    getAvailablePhones();
                    System.out.println();
                    buying();
                    isValidChoice = true;
                }
                case "3" -> {
                    getBoughtPhones();
                    buying();
                    isValidChoice = true;
                }
                case "4" -> {
                    changePassword();
                    System.out.println("Do you want to go back to previous menu?");
                    output = sc.next();
                    if (output.equalsIgnoreCase("yes")) {
                        showUserMenu();
                    } else if (output.equalsIgnoreCase("no")) {
                        System.out.println("Do you want to exit or re-login?");
                        output = sc.next();
                        if (output.equalsIgnoreCase("login")) {
                            login();
                            showUserMenu();
                        } else {
                            break;
                        }
                    }
                    isValidChoice = true;
                }
                case "5" -> {
                    showInfo();
                    isValidChoice = true;
                }
                case "6" -> {
                    showListMenuOptions();
                    isValidChoice = true;
                }
                case "7" -> {
                    start();
                    if (status.equalsIgnoreCase("user")) {
                        showUserMenu();
                    } else {
                        showAdminMenu();
                    }
                    isValidChoice = true;
                }
                case "8" -> isValidChoice = true;
                default -> {
                    System.out.println("You entered a wrong number!");
                    showUserMenu();
                    isValidChoice = false;
                }
            }
        } while (!isValidChoice);
    }

    private void showListMenuOptions() {

        System.out.println("Select an action from below:");
        System.out.println("1. Filter by Name");
        System.out.println("2. Filter by Year");
        System.out.println("3. Filter by Color");
        System.out.println("4. Filter by Size");
        System.out.println("5. Buy without filter");
        System.out.println("6. Back to previous menu");
        System.out.println();

        System.out.println("What is your choice?");

        boolean isValidChoice = false;
        do {
            String choice = sc.next();
            switch (choice) {
                case "1" -> {
                    System.out.println("What is the NAME of the phone?");
                    String name = sc.next();
                    service.filterByName(name);
                    buying();
                    isValidChoice = true;
                }
                case "2" -> {
                    System.out.println("What is the YEAR of the phone?");
                    Integer year = sc.nextInt();
                    service.filterByYear(year);
                    buying();
                    isValidChoice = true;
                }
                case "3" -> {
                    System.out.println("What is the COLOR of the phone?");
                    String color = sc.next().toUpperCase();
                    service.filterByColor(Color.valueOf(color));
                    buying();
                    isValidChoice = true;
                }
                case "4" -> {
                    System.out.println("What is the SIZE of the phone?");
                    float size = sc.nextFloat();
                    service.filterBySize(size);
                    buying();
                    isValidChoice = true;
                }
                case "5" -> {
                    buying();
                    isValidChoice = true;
                }
                case "6" -> {
                    showUserMenu();
                    isValidChoice = true;
                }
                default -> {
                    System.out.println("Enter a valid choice");
                    isValidChoice = false;
                }
            }
        } while (!isValidChoice);
    }

    private void showInfo() {
        if (status.equalsIgnoreCase("user")) {
            System.out.println("You're personal info is:");
            System.out.println("Your name is: " + currentUser.getName());
            System.out.println("Your age is: " + currentUser.calculateAge());
            System.out.println("Do you want to change some personal info?");
            boolean isValid;
            do {
                output = sc.next();
                if (output.equalsIgnoreCase("yes")) {
                    changePassword();
                    isValid = true;
                } else if (output.equalsIgnoreCase("no")) {
                    showUserMenu();
                    isValid = true;
                } else {
                    System.out.println("Please select between 'yes' and 'no'");
                    isValid = false;
                }
            } while (!isValid);

        } else {
            System.out.println("You're personal info is:");
            System.out.println("Your name is: " + currentAdmin.getName());
            System.out.println("Your age is: " + currentAdmin.calculateAge());
            System.out.println("Do you want to change some personal info?");
            boolean isValid;
            do {
                output = sc.next();
                if (output.equalsIgnoreCase("yes")) {
                    changePassword();
                    isValid = true;
                } else if (output.equalsIgnoreCase("no")) {
                    showUserMenu();
                    isValid = true;
                } else {
                    System.out.println("Please choose between 'yes' and 'no'");
                    isValid = false;
                }
            } while (!isValid);
        }
    }

    private void seeIncome() {

        try {
            BufferedReader br = new BufferedReader(new FileReader("BoughtPhones.txt"));
            String st;
            while ((st = br.readLine()) != null) {
                Phone phone = new Phone(Integer.parseInt(st.split(" ")[0]), st.split(" ")[1], st.split(" ")[2], Float.parseFloat(st.split(" ")[3]), Integer.parseInt(st.split(" ")[4]), Color.valueOf(st.split(" ")[5].toUpperCase(Locale.ROOT)), Long.parseLong(st.split(" ")[6]));
                thePrice += phone.getBasePrice();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Total income: $ " + thePrice);

    }

    private void changePassword() {
        System.out.println("PASSWORD: type 'password' ");
        String info = sc.next();
        if (status.equalsIgnoreCase("user")) {
            if (info.equalsIgnoreCase("password")) {
                System.out.println("Enter your old password first");
                String password = sc.next();
                if (password.equals(currentUser.getPassword())) {
                    System.out.println("Enter your new password");
                    password = sc.next();
                    currentUser.setPassword(password);

                    try {
                        FileUtils.write(new File("Temp.txt"), "", Charset.defaultCharset());

                        BufferedReader br = new BufferedReader(new FileReader("Users.txt"));
                        BufferedWriter out = new BufferedWriter(new FileWriter("Temp.txt", true));
                        String st;
                        while ((st = br.readLine()) != null) {
                            User user = new User(st.split(" ")[0], st.split(" ")[1], st.split(" ")[2]);
                            if (user.getName().equals(currentUser.getName()) && user.getBirthDate().equals(currentUser.getBirthDate())) {
                                out.write(currentUser.getName() + " " + currentUser.getPassword() + " " + currentUser.getBirthDate() + "\n");
                            } else {
                                out.write(user.getName() + " " + user.getPassword() + " " + user.getBirthDate() + "\n");
                            }
                        }
                        out.close();

                        FileUtils.write(new File("Users.txt"), "", Charset.defaultCharset());


                        BufferedReader br2 = new BufferedReader(new FileReader("Temp.txt"));
                        PrintWriter printWriter = new PrintWriter(new FileWriter("Users.txt"));
                        String line;
                        while ((line = br2.readLine()) != null) {
                            printWriter.append(line.split(" ")[0]).append(" ").append(line.split(" ")[1]).append(" ").append(line.split(" ")[2]).append("\n");
                        }
                        printWriter.close();

                        FileUtils.write(new File("Temp.txt"), "", Charset.defaultCharset());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("You entered the wrong password");
                    showUserMenu();
                }
            }
        } else {
            if (info.equalsIgnoreCase("password")) {
                System.out.println("Enter your old password first");
                String password = sc.next();
                if (password.equals(currentAdmin.getPassword())) {
                    System.out.println("Enter your new password");
                    password = sc.next();
                    currentAdmin.setPassword(password);
                } else {
                    System.out.println("You entered the wrong password");
                    showUserMenu();
                }
            }
        }
    }

    private void getAllPhones() {
        System.out.println("There are all the phones");
        for (Phone phone : phones) {
            System.out.println(phone);
        }
    }

    private void getAllUsers() {
        for (User user : users) {
            System.out.println(user);
        }
    }

    private void getBoughtPhones() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("BoughtPhones.txt"));
            String st;
            while ((st = br.readLine()) != null) {
                Phone phone = new Phone(Integer.parseInt(st.split(" ")[0]), st.split(" ")[1], st.split(" ")[2], Float.parseFloat(st.split(" ")[3]), Integer.parseInt(st.split(" ")[4]), Color.valueOf(st.split(" ")[5].toUpperCase(Locale.ROOT)), Long.parseLong(st.split(" ")[6]));
                System.out.println(phone);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getAvailablePhones() {
        int i = 1;
        System.out.println("These are the available phones");
        for (Phone phone : phones) {
            if (!phone.isBought()) {
                System.out.print(i + ". ");
                System.out.println(phone);
                i++;
            }
        }
    }

    private void buyAPhone() {
        boolean buying = true;

        while (buying) {
            buying = false;

            boolean isValid = false;
            do {
                System.out.println("Choose the index");
                int index = sc.nextInt();
                for (Phone phone : phones) {
                    if (!phone.isBought()) {
                        if (index == phone.getIndex()) {
                            isValid = true;
                            warantyBuy(phone);
                        } else {
                            isValid = false;
                        }
                    }
                }
            } while (!isValid);
        }
        showUserMenu();
    }

    private void warantyBuy(Phone phone) {
        int warantyYears = -1;

        System.out.println("The phone you want to buy is: \n" + phone);
        System.out.println("\nFow how many years do you want the waranty?");
        System.out.println("Please choose a number smaller than 5");
        do {
            try {
                warantyYears = Integer.parseInt(sc.next());
            } catch (Exception e) {
                System.out.println("Ai introdus eronat");
                warantyBuy(phone);
            }
            ;
            if (warantyYears > 5) {
                System.out.println("Please choose a number smaller than 5");
            }
        } while (warantyYears > 5);

        long totalPrice = phone.getBasePrice() + warantyYears * 100L;
        phone.setBasePrice(totalPrice);
        System.out.println("The price of the phone is: $" + phone.getBasePrice());
        phone.setBought(true);
        BoughtPhone currentPhone = new BoughtPhone();
        currentPhone.setPhone(phone);
        currentPhone.setCurrentlyBought(true);
        try {
            PrintWriter out = new PrintWriter(new FileWriter("BoughtPhones.txt", true));
            out.append(String.valueOf(currentPhone.getPhone().getIndex())).append(currentPhone.getPhone().getName()).append(" ").append(String.valueOf(currentPhone.getPhone().getSize())).append(" ").append(String.valueOf(currentPhone.getPhone().getYear())).append(" ").append(String.valueOf(currentPhone.getPhone().getColor())).append(" ").append(String.valueOf(currentPhone.getPhone().getBasePrice())).append("\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        feature.payInRates(phone.getBasePrice());

        System.out.println("Do you want to go to the main page, re-login or exit?");
        output = sc.next();
        if (output.equalsIgnoreCase("return")) {
            showUserMenu();
        } else if (output.equalsIgnoreCase("login")) {
            login();
            showUserMenu();
        } else if (output.equalsIgnoreCase("exit")) {
            exit();
        }
    }

    private void exit() {
        System.exit(0);
    }

    private void buying() {
        System.out.println("Do you want to buy a phone?");
        output = sc.next();
        if (output.equalsIgnoreCase("yes")) {
            buyAPhone();
        }
    }

    private void statistics() {
        System.out.println("                    STATISTICS");
        System.out.println("1. Show total income");
        System.out.println("2. Show list of users");
        System.out.println("3. Get list of phones by name");
        System.out.println("4. Go to the previous menu");
        boolean isValid = false;
        do {
            String option = sc.next();
            switch (option) {
                case "1" -> {
                    seeIncome();
                    isValid = true;
                }
                case "2" -> {
                    getAllUsers();
                    isValid = true;
                }
                case "3" -> {
                    getNumberOfPhones();
                    isValid = true;
                }
                case "4" -> {
                    if (status.equals("user")) {
                        showUserMenu();
                    } else if (status.equals("admin")) {
                        showAdminMenu();
                    }
                    isValid = true;
                }
                default -> {
                    System.out.println("Please enter a valid choice");
                    isValid = false;
                }
            }
        } while (!isValid);
    }

    private void getNumberOfPhones() {
        String[][] phonesNumberList = new String[2][4];
        int iphone = 0;
        int samsung = 0;
        int huawei = 0;
        int xiaomi = 0;
        phonesNumberList[0][0] = "IPhone";
        phonesNumberList[0][1] = "Samsung";
        phonesNumberList[0][2] = "Huawei";
        phonesNumberList[0][3] = "Xiaomi";

        for (Phone p : phones) {
            if (p.getName().equalsIgnoreCase("iphone")) {
                iphone++;
            } else if (p.getName().equalsIgnoreCase("samsung")) {
                samsung++;
            } else if (p.getName().equalsIgnoreCase("huawei")) {
                huawei++;
            } else if (p.getName().equalsIgnoreCase("xiaomi")) {
                xiaomi++;
            }
        }

        phonesNumberList[1][0] = String.valueOf(iphone);
        phonesNumberList[1][1] = String.valueOf(samsung);
        phonesNumberList[1][2] = String.valueOf(huawei);
        phonesNumberList[1][3] = String.valueOf(xiaomi);


            for (int j = 0; j < 4; j++) {
                System.out.println(phonesNumberList[0][j] + " " + phonesNumberList[1][j]);
            }

    }
}
