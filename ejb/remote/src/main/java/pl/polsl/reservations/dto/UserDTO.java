package pl.polsl.reservations.dto;

import pl.polsl.reservationsdatabasebeanremote.database.Workers;

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

    public UserDTO() {}


    public UserDTO(long id, String address, String department, String grade, String pesel, String name, String surname) {
        this.id = id;
        this.address = address;
        this.department = department;
        this.grade = grade;
        this.pesel = pesel;
        this.name = name;
        this.surname = surname;
    }
    public UserDTO(Workers w) {
        this.id = w.getId();
        this.address = w.getAdress();
        this.department = w.getDepartamentId().getDepratamentName();
        this.grade = w.getGrade();
        this.pesel = w.getPesel();
        this.name = w.getWorkerName();
        this.surname = w.getSurname();
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
}
