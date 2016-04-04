package in.therenter.therenterfirebase;

import java.util.UUID;

public class User {
    private int birthYear;
    private String fullName;
    private String imageString;
    private String uuid;

    public User() {
    }

    public User(String fullName, int birthYear, String imageString) {
        this.fullName = fullName;
        this.birthYear = birthYear;
        this.imageString = imageString;
        this.uuid = UUID.randomUUID().toString();
    }

    public long getBirthYear() {
        return birthYear;
    }

    public String getFullName() {
        return fullName;
    }

    public String getImageString() {
        return imageString;
    }

    public String getUuid() {
        return uuid;
    }
}