package eShop.models;

import java.util.Scanner;

public class Features {

    Scanner sc = new Scanner(System.in);
    String output;

    protected void payInRates(long price) {

        int rate;

        System.out.println("Do you want to pay in rates?");
        output = sc.next();
        if (output.equalsIgnoreCase("yes")) {
            System.out.println("How many rates do you want?");
            System.out.println("You can choose between 3/6/9/12");
            rate = sc.nextInt();
            System.out.println("The price per month is: $" + price/rate);
        }
    }
}
