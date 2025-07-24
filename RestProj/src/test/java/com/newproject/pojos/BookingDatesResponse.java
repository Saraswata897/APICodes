package com.newproject.pojos; // Using the POJO package

public class BookingDatesResponse {
    private String checkin;
    private String checkout;

    // Default constructor is required for deserialization by Jackson/RestAssured
    public BookingDatesResponse() {
    }

    // Constructor with fields (optional, but good for creating instances programmatically)
    public BookingDatesResponse(String checkin, String checkout) {
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
        return "BookingDatesResponse{" +
               "checkin='" + checkin + '\'' +
               ", checkout='" + checkout + '\'' +
               '}';
    }
}
