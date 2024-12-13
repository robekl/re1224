package example;

import example.service.CheckoutService;

public class RentalAgreementApplication {
    public static void main(String[] args) {
        new CheckoutService().checkout(args);
    }
}