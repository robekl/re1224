package example.service;

import example.domain.RentalAgreement;
import example.utils.Receipt;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * The ReceiptPrinterService class is responsible for printing the details of a rental transaction
 * as specified in a given {@link RentalAgreement}. It outputs key information about the rental,
 * including the tool's details, rental duration, charges, and applicable discounts.
 *
 * The printed receipt includes:
 * - Tool code
 * - Tool type
 * - Tool brand
 * - Rental days
 * - Check-out date
 * - Due date
 * - Daily rental charge
 * - Charge days
 * - Pre-discount charge
 * - Discount percentage
 * - Discount amount
 * - Final charge
 *
 * This service converts charge values from cents to a formatted currency string and makes use
 * of a {@link Receipt} object to calculate and retrieve certain billing details.
 */
public class ReceiptPrinterService {
    public void printReceipt(RentalAgreement rentalAgreement) {
        Receipt receipt = new Receipt(rentalAgreement);

        System.out.println("Tool code: " + rentalAgreement.getTool().getCode());
        System.out.println("Tool type: " + rentalAgreement.getTool().getType());
        System.out.println("Tool brand: " + rentalAgreement.getTool().getBrand());
        System.out.println("Rental days: " + rentalAgreement.getRentalDays());
        System.out.println("Check out date: " + CheckoutService.formatter.format(rentalAgreement.getCheckOutDate()));
        System.out.println("Due date: " + CheckoutService.formatter.format(rentalAgreement.getDueDate()));
        System.out.println("Daily rental charge: " + centsToCurrencyString(rentalAgreement.getDailyRentalChargeInCents()));
        System.out.println("Charge days: " + receipt.getChargeDays());
        System.out.println("Pre-discount charge: " + centsToCurrencyString(receipt.getPreDiscountChargeCents()));
        System.out.println("Discount percent: " + rentalAgreement.getDiscountPercentage() + "%");
        System.out.println("Discount amount: " + centsToCurrencyString(receipt.getDiscountCents().intValue()));
        System.out.println("Final charge: " + centsToCurrencyString(receipt.getFinalChargeCents().intValue()));
    }

    /**
     * Converts a monetary value represented in cents to a formatted currency string.
     * This method ensures that the cents value is properly scaled to dollars and formatted
     * according to the US currency standards.
     *
     * @param cents the monetary value in cents to be converted to a currency string.
     * @return a string representing the formatted currency value in dollars.
     */
    private String centsToCurrencyString(int cents) {
        BigDecimal dollars = BigDecimal.valueOf(cents, 2);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        return currencyFormatter.format(dollars);
    }
}
