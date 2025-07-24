package com.newproject.pojos; // A new package for your POJO classes

public class BookingDates {
    private String checkin;
    private String checkout;

    // Default constructor is required for deserialization by Jackson/RestAssured
    public BookingDates() {
    }

    public BookingDates(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    // Getter for checkin
    public String getCheckin() {
        return checkin;
    }

    // Setter for checkin
    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    // Getter for checkout
    public String getCheckout() {
        return checkout;
    }

    // Setter for checkout
    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    @Override
    public String toString() {
        return "BookingDates{" +
               "checkin='" + checkin + '\'' +
               ", checkout='" + checkout + '\'' +
               '}';
    }
}
