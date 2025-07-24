package com.example.parsers; // New package for POJO and Custom Parser

// This POJO (Plain Old Java Object) represents the structure of the API response.
// Rest Assured will use this to map the JSON response into a Java object.
public class BookingResponsePojo {
    private int bookingid;
    private BookingDetails booking;

    // Nested class to represent the 'booking' object within the response
    public static class BookingDetails {
        private String firstname;
        private String lastname;
        private int totalprice;
        private boolean depositpaid;
        private BookingDates bookingdates;
        private String additionalneeds;

        // Nested class for 'bookingdates'
        public static class BookingDates {
            private String checkin;
            private String checkout;

            // Getters and Setters for BookingDates
            public String getCheckin() {
                return checkin;
            }

            public void setCheckin(String checkin) {
                this.checkin = checkin;
            }

            public String getCheckout() {
                return checkout;
            }

            public void setCheckout(String checkout) {
                this.checkout = checkout;
            }
        }

        // Getters and Setters for BookingDetails
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

        public boolean isDepositpaid() {
            return depositpaid;
        }

        public void setDepositpaid(boolean depositpaid) {
            this.depositpaid = depositpaid;
        }

        public BookingDates getBookingdates() {
            return bookingdates;
        }

        public void setBookingdates(BookingDates bookingdates) {
            this.bookingdates = bookingdates;
        }

        public String getAdditionalneeds() {
            return additionalneeds;
        }

        public void setAdditionalneeds(String additionalneeds) {
            this.additionalneeds = additionalneeds;
        }
    }

    // Getters and Setters for BookingResponsePojo
    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public BookingDetails getBooking() {
        return booking;
    }

    public void setBooking(BookingDetails booking) {
        this.booking = booking;
    }
}
