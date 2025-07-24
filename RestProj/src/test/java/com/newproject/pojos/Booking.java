package com.newproject.pojos; // A new package for your POJO classes

public class Booking {
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private BookingDates bookingdates; // Using the nested POJO class
    private String additionalneeds;

    // Default constructor is required for deserialization by Jackson/RestAssured
    public Booking() {
    }

    public Booking(String firstname, String lastname, int totalprice, boolean depositpaid, BookingDates bookingdates, String additionalneeds) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
        this.additionalneeds = additionalneeds;
    }

    // Getter for firstname
    public String getFirstname() {
        return firstname;
    }

    // Setter for firstname
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    // Getter for lastname
    public String getLastname() {
        return lastname;
    }

    // Setter for lastname
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    // Getter for totalprice
    public int getTotalprice() {
        return totalprice;
    }

    // Setter for totalprice
    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    // Getter for depositpaid
    public boolean isDepositpaid() { // Using 'is' prefix for boolean getter
        return depositpaid;
    }

    // Setter for depositpaid
    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    // Getter for bookingdates
    public BookingDates getBookingdates() {
        return bookingdates;
    }

    // Setter for bookingdates
    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    // Getter for additionalneeds
    public String getAdditionalneeds() {
        return additionalneeds;
    }

    // Setter for additionalneeds
    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }

    @Override
    public String toString() {
        return "Booking{" +
               "firstname='" + firstname + '\'' +
               ", lastname='" + lastname + '\'' +
               ", totalprice=" + totalprice +
               ", depositpaid=" + depositpaid +
               ", bookingdates=" + bookingdates +
               ", additionalneeds='" + additionalneeds + '\'' +
               '}';
    }
}
