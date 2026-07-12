package org.example.data;

public class CustomerInfo {

    private String fullName;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private String address1;
    private String address2;
    private int totalPrice;
    private String bookName;
    private String subject;
    private String message;

    private String updateFirstName;
    private String updateLastName;
    private int updateTotalPrice;
    private String updateBookName;

    private boolean depositPaid;
    private boolean updateDepositPaid;

    private String phoneNumber;
    private String state;
    private String zipCode;
    private String country;
    private String dob;
    private String gender;

    public CustomerInfo() {
    }

    // -------- getters & setters --------
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUpdateFirstName() {
        return updateFirstName;
    }

    public void setUpdateFirstName(String updateFirstName) {
        this.updateFirstName = updateFirstName;
    }

    public String getUpdateLastName() {
        return updateLastName;
    }

    public void setUpdateLastName(String updateLastName) {
        this.updateLastName = updateLastName;
    }

    public int getUpdateTotalPrice() {
        return updateTotalPrice;
    }

    public void setUpdateTotalPrice(int updateTotalPrice) {
        this.updateTotalPrice = updateTotalPrice;
    }

    public String getUpdateBookName() {
        return updateBookName;
    }

    public void setUpdateBookName(String updateBookName) {
        this.updateBookName = updateBookName;
    }

    public boolean isDepositPaid() {
        return depositPaid;
    }

    public void setDepositPaid(boolean depositPaid) {
        this.depositPaid = depositPaid;
    }

    public boolean isUpdateDepositPaid() {
        return updateDepositPaid;
    }

    public void setUpdateDepositPaid(boolean updateDepositPaid) {
        this.updateDepositPaid = updateDepositPaid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}