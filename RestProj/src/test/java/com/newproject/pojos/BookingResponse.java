package com.newproject.pojos; // Using the POJO package

public class BookingResponse {
    private int bookingid;
    private BookingDetailsResponse booking; // Using the nested POJO class for booking details

    // Default constructor is required for deserialization by Jackson/RestAssured
    public BookingResponse() {
    }

    // Constructor with fields (optional)
    public BookingResponse(int bookingid, BookingDetailsResponse booking) {
        this.bookingid = bookingid;
        this.booking = booking;
    }

    // Getter for bookingid
    public int getBookingid() {
        return bookingid;
    }

    // Setter for bookingid
    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    // Getter for booking
    public BookingDetailsResponse getBooking() {
        return booking;
    }

    // Setter for booking
    public void setBooking(BookingDetailsResponse booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "BookingResponse{" +
               "bookingid=" + bookingid +
               ", booking=" + booking +
               '}';
    }

    // Nested class to represent the 'booking' object within the response
    public static class BookingDetailsResponse {
        private String firstname;
        private String lastname;
        private int totalprice;
        private boolean depositpaid;
        private BookingDatesResponse bookingdates; // Using the nested BookingDatesResponse POJO
        private String additionalneeds;

        // Default constructor is required for deserialization
        public BookingDetailsResponse() {
        }

        // Constructor with fields (optional)
        public BookingDetailsResponse(String firstname, String lastname, int totalprice, boolean depositpaid, BookingDatesResponse bookingdates, String additionalneeds) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.totalprice = totalprice;
            this.depositpaid = depositpaid;
            this.bookingdates = bookingdates;
            this.additionalneeds = additionalneeds;
        }

        // Getters and Setters for BookingDetailsResponse
        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public int getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(int totalprice) {
            this.totalprice = totalprice;
        }

        public boolean isDepositpaid() { // Using 'is' prefix for boolean getter
            return depositpaid;
        }

        public void setDepositpaid(boolean depositpaid) {
            this.depositpaid = depositpaid;
        }

        public BookingDatesResponse getBookingdates() {
            return bookingdates;
        }

        public void setBookingdates(BookingDatesResponse bookingdates) {
            this.bookingdates = bookingdates;
        }

        public String getAdditionalneeds() {
            return additionalneeds;
        }

        public void setAdditionalneeds(String additionalneeds) {
            this.additionalneeds = additionalneeds;
        }

        @Override
        public String toString() {
            return "BookingDetailsResponse{" +
                   "firstname='" + firstname + '\'' +
                   ", lastname='" + lastname + '\'' +
                   ", totalprice=" + totalprice +
                   ", depositpaid=" + depositpaid +
                   ", bookingdates=" + bookingdates +
                   ", additionalneeds='" + additionalneeds + '\'' +
                   '}';
        }
    }
}
