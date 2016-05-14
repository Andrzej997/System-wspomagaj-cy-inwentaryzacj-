package pl.polsl.reservations.dto;

import pl.polsl.reservationsdatabasebeanremote.database.Workers;
import pl.polsl.reservationsdatabasebeanremote.database.Users;

import java.io.Serializable;

/**
 * Created by Krzysztof Stręk on 2016-05-14.
 */
public class UserDTO implements Serializable {

    private long id;

    private String address;

    private String department;

    private String grade;

    private String pesel;

    private String name;

    private String surname;

    private String userName;

    private String phoneNumber;

    private String email;

    private Long privilegeLevel;

    private int roomNumber;

    public UserDTO() {
    }

    public UserDTO(long id, String address, String department, String grade, String pesel, String name, String surname, String userName, String phoneNumber, String email, Long privilegeLevel, int roomNumber) {
        this.id = id;
        this.address = address;
        this.department = department;
        this.grade = grade;
        this.pesel = pesel;
        this.name = name;
        this.surname = surname;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.privilegeLevel = privilegeLevel;
        this.roomNumber = roomNumber;
    }

    public UserDTO(Workers w) {
        this.id = w.getId();
        this.address = w.getAdress();
        this.department = w.getDepartamentId().getDepratamentName();
        this.grade = w.getGrade();
        this.pesel = w.getPesel();
        this.name = w.getWorkerName();
        this.surname = w.getSurname();
        this.roomNumber = w.getRoom().getRoomNumber();
    }

    public UserDTO(Users u) {
        Workers w = u.getWorkers();
        this.id = u.getUserId();
        this.address = w.getAdress();
        this.department = w.getDepartamentId().getDepratamentName();
        this.grade = w.getGrade();
        this.pesel = w.getPesel();
        this.name = w.getWorkerName();
        this.surname = w.getSurname();
        this.roomNumber = w.getRoom().getRoomNumber();
        this.userName = u.getUsername();
        this.phoneNumber = u.getPhoneNumber().toString();
        this.email = u.getEmail();
        this.privilegeLevel = u.getPriviligeLevel().getPriviligeLevel();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPrivilegeLevel() {
        return privilegeLevel;
    }

    public void setPrivilegeLevel(Long privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
