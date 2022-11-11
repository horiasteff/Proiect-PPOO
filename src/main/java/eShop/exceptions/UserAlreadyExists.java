package eShop.exceptions;

public class UserAlreadyExists extends Exception{

    public UserAlreadyExists(){}

    public UserAlreadyExists(String message) {
        super(message);
    }
}
