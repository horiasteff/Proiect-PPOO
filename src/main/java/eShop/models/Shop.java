package eShop.models;

import eShop.data.DataSource;
import eShop.enums.Color;
import eShop.phones.Phone;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shop {

    Scanner sc = new Scanner(System.in);
    List<User> users = DataSource.userList();
    List<Admin> admins = DataSource.adminList();
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
        if (status.equalsIgnoreCase("user")) {
            showUserMenu();
        } else {
            showAdminMenu();
        }
    }

    private void chooseStart() {
        boolean isValid = false;
        do {
            startOption = sc.next();
            switch (startOption) {
                case "1":
                    login();
                    if (status.equalsIgnoreCase("user")) {
                        showUserMenu();
                    } else {
                        showAdminMenu();
                    }
                    break;
                case "2":
                    register();
                    start();
                    break;
                case "3":
                    getAllPhones();
                    break;
                case "4":
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
        System.out.println("Enter your date of birth (DD-MM-YYYY)");
        String date = sc.next();

        User user = new User(username, password, date);
        users.add(user);
        try {
            PrintWriter out = new PrintWriter(new FileWriter("Users.txt", true));
            out.append(user.getName() + " " + user.getPassword() + " " + user.getBirthDate() +"\n");
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
        System.out.println("2. List available phones");
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
                case "1":
                    getAllPhones();
                    System.out.println("Want to go back to previous menu?");
                    output = sc.next();
                    if (output.equalsIgnoreCase("yes")) {
                        showAdminMenu();
                    }
                    isValid = true;
                    break;
                case "2":
                    getAvailablePhones();
                    System.out.println();
                    System.out.println("Want to go back to previous menu?");
                    output = sc.next();
                    if (output.equalsIgnoreCase("yes")) {
                        showAdminMenu();
                    }
                    isValid = true;
                    break;
                case "3":
                    getBoughtPhones();
                    System.out.println("Want to go back to previous menu?");
                    output = sc.next();
                    if (output.equalsIgnoreCase("yes")) {
                        showAdminMenu();
                    }
                    isValid = true;
                    break;
                case "4":
                    statistics();
                    System.out.println("Do you want to go back to previous menu?");
                    output = sc.next();
                    if (output.equalsIgnoreCase("yes")) {
                        statistics();
                    } else {
                        System.out.println("You entered a wrong number!");
                        showUserMenu();
                    }
                    isValid = true;
                    break;
                case "5":
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
                    break;
                case "6":
                    showInfo();
                    isValid = true;
                    break;
                case "7":
                    login();
                    if (status.equalsIgnoreCase("user")) {
                        showUserMenu();
                    } else {
                        showAdminMenu();
                    }
                    isValid = true;
                    break;
                case "8":
                    isValid = true;
                    break;
                default:
                    System.out.println("Please enter a valid choice");
                    isValid = false;
                    break;
            }
        } while (!isValid);

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
        System.out.println("4. List your bought phones");
        System.out.println("5. Change password");
        System.out.println("6. Show personal info");
        System.out.println("7. Filter phones");
        System.out.println("8. Logout");
        System.out.println("9. Exit");

        System.out.println();
        System.out.println("Enter your option");

        boolean isValidChoice = false;

        do {
            String choice = sc.next();
            switch (choice) {
                case "1":
                    getAllPhones();
                    System.out.println();
                    buying();
                    isValidChoice = true;
                    break;
                case "2":
                    getAvailablePhones();
                    System.out.println();
                    buying();
                    isValidChoice = true;
                    break;
                case "3":
                    getBoughtPhones();
                    buying();
                    isValidChoice = true;
                    break;
                case "4":
                    getCurrentlyBoughtPhones();
                    isValidChoice = true;
                    break;
                case "5":
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
                    break;
                case "6":
                    showInfo();
                    isValidChoice = true;
                    break;
                case "7":
                    showListMenuOptions();
                    isValidChoice = true;
                    break;
                case "8":
                    start();
                    if (status.equalsIgnoreCase("user")) {
                        showUserMenu();
                    } else {
                        showAdminMenu();
                    }
                    isValidChoice = true;
                    break;
                case "9":
                    isValidChoice = true;
                    break;

                default:
                    System.out.println("You entered a wrong number!");
                    showUserMenu();
                    isValidChoice = false;
                    break;
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
                case "1":
                    System.out.println("What is the NAME of the phone?");
                    String name = sc.next();
                    service.filterByName(name);
                    buying();
                    isValidChoice = true;
                    break;
                case "2":
                    System.out.println("What is the YEAR of the phone?");
                    Integer year = sc.nextInt();
                    service.filterByYear(year);
                    buying();
                    isValidChoice = true;
                    break;
                case "3":
                    System.out.println("What is the COLOR of the phone?");
                    String color = sc.next().toUpperCase();
                    service.filterByColor(Color.valueOf(color));
                    buying();
                    isValidChoice = true;
                    break;
                case "4":
                    System.out.println("What is the SIZE of the phone?");
                    float size = sc.nextFloat();
                    service.filterBySize(size);
                    buying();
                    isValidChoice = true;
                    break;
                case "5":
                    buying();
                    isValidChoice = true;
                    break;
                case "6":
                    showUserMenu();
                    isValidChoice = true;
                    break;
                default:
                    System.out.println("Enter a valid choice");
                    isValidChoice = false;
                    break;
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
        int i = 1;
        System.out.println("There are all the phones");
        for (Phone phone : DataSource.phoneList()) {
            String padding = i < 10 ? " " : "";
            System.out.print(padding + i + ". ");
            System.out.println(phone);
            i++;
        }
    }

    private void getAllUsers() {
        for (User user : users) {
            System.out.println(user);
            System.out.println(user.boughtPhones);
        }
    }

    private void getBoughtPhones() {
        int i = 1;
        System.out.println("These are the bought phones");
        for (Phone phone : phones) {
            if (phone.isBought()) {
                System.out.print(i);
                System.out.println(phone);
                i++;
            }
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
        boolean isFound = false;

        while (buying) {
            buying = false;
            System.out.println("Choose 'I' for buying the index or 'F' to go with a filter");
            output = sc.next().toUpperCase();
            if (output.equals("I")) {
                boolean isValid = false;
                do {
                    System.out.println("Choose index");
                    int index = sc.nextInt();
                    for (Phone phone : phones) {
                        if (!phone.isBought()) {
                            if (index == phone.getIndex()) {
                                isFound = true;
                                isValid = true;
                               // System.out.println(phone);
                                warantyBuy(phone);
                            } else {
                                isValid = false;
                            }
                        }
                    }
                } while (!isValid);
            } else if (output.equals("F")) {
                //showListMenuOptions();
                System.out.println("What is the NAME of the phone you want to buy?");
                String name = sc.next();
                service.filterByName(name);
                System.out.println("What is the YEAR of the phone you want to buy?");
                Integer year = sc.nextInt();
                service.filterByYear(year);
                System.out.println("What is the COLOR of the phone you want to buy?");
                String color = sc.next().toUpperCase();
                service.filterByColor(Color.valueOf(color));
                System.out.println("What is the SIZE of the phone you want to buy?");
                float size = sc.nextFloat();
                service.filterBySize(size);

                for (Phone phone : phones) {
                    if (!phone.isBought()) {
                        if (name.equalsIgnoreCase(phone.getName()) && year.equals(phone.getYear()) && color.equalsIgnoreCase(String.valueOf(phone.getColor())) && size == phone.getSize()) {
                            isFound = true;
                            warantyBuy(phone);
                            break;
                        }
                    }
                }
            } else {
                System.out.println("Please choose from the options");
            }
            if (!isFound) {
                System.out.println("We couldn't find your specified phone. Try something else");
                buying = true;
            }
        }
        showUserMenu();
    }

    private void warantyBuy(Phone phone) {
        int warantyYears;

        System.out.println("The phone you want to buy is: \n" + phone);
        System.out.println("\nFow how many years do you want the waranty?");
        System.out.println("Please choose a number smaller than 5");
        do {
            warantyYears = sc.nextInt();
            if (warantyYears > 5) {
                System.out.println("Please choose a number smaller than 5");
            }
        } while (warantyYears > 5);


        System.out.println("The price of the phone is: $" + phone.getBasePrice());
        phone.setBought(true);
        BoughtPhone currentPhone = new BoughtPhone();
        currentPhone.setPhone(phone);
        currentPhone.setCurrentlyBought(true);
        currentPhone.setPickUpDate(LocalDate.now());
        currentUser.boughtPhones.add(currentPhone);
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

    private List<BoughtPhone> getCurrentlyBoughtPhones() {
        List<BoughtPhone> boughtPhones = new ArrayList<>();
        for (User user : users) {
            boughtPhones.addAll(user.getCurrentlyBoughtPhones());
        }
        return boughtPhones;
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
        System.out.println("3. Go to the previous menu");
        boolean isValid = false;
        do {
            String option = sc.next();
            switch (option) {
                case "1":
                    seeIncome();
                    isValid = true;
                    break;
                case "2":
                    getAllUsers();
                    isValid = true;
                    break;
                case "3":
                    showUserMenu();
                    isValid = true;
                    break;
                default:
                    System.out.println("Please enter a valid choice");
                    isValid = false;
                    break;
            }
        } while (!isValid);
    }

    private void chooseIndex(int index) {
        for (Phone phone : phones) {
            if (!phone.isBought()) {
                if (index == phone.getIndex()) {
                }
            }
        }
    }

//    private void addUserToFile(User user) throws IOException {
//        FileWriter fw = new FileWriter("Users.txt", true);
//        PrintWriter printWriter = new PrintWriter("Users.txt");
//        //BufferedWriter writer = new BufferedWriter(fw);
//        //writer.write(String.valueOf(user.getName()));
//        printWriter.println(user.getName());
//    }
}
