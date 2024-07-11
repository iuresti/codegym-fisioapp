package codegym.tequila.fisioapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "therapists")
public class Therapist {
    @Id
    private String id;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String address;
    private String specialties;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Therapist therapist = (Therapist) o;
        return Objects.equals(id, therapist.id)
                && Objects.equals(firstname, therapist.firstname)
                && Objects.equals(lastName, therapist.lastName)
                && Objects.equals(birthDate, therapist.birthDate)
                && Objects.equals(gender, therapist.gender)
                && Objects.equals(phone, therapist.phone)
                && Objects.equals(address, therapist.address)
                && Objects.equals(specialties, therapist.specialties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastName, birthDate, gender, phone, address, specialties);
    }
}
