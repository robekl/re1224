package example.utils;

import example.domain.Tool;
import example.domain.RentalAgreement;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static example.utils.Constants.LADDER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReceiptTest {
    String brand = "brand";
    String code = "FN2187";

    @Test
    void testReceiptForOneDayNoDiscount() {
        Tool tool = Tool.builder().brand(brand).type(LADDER).code(code).build();
        int rentalDays = 1;
        LocalDate checkoutDate = LocalDate.of(2024, 7, 1);
        BigDecimal discountPercentage = BigDecimal.valueOf(0L);

        RentalAgreement rentalAgreement = new RentalAgreement(tool, rentalDays, checkoutDate, discountPercentage);

        Receipt receipt = new Receipt(rentalAgreement);

        assertEquals(1, receipt.getChargeDays());
        assertEquals(199, receipt.getPreDiscountChargeCents());
        assertEquals(BigDecimal.ZERO, receipt.getDiscountPercentAsDecimal());
        assertEquals(BigDecimal.ZERO, receipt.getDiscountCents());
        assertEquals(BigDecimal.valueOf(199L), receipt.getFinalChargeCents());
    }

    @Test
    void testReceiptForOneDayOnePercentDiscount() {
        Tool tool = Tool.builder().brand(brand).type(LADDER).code(code).build();
        int rentalDays = 1;
        LocalDate checkoutDate = LocalDate.of(2024, 7, 1);
        BigDecimal discountPercentage = BigDecimal.valueOf(1L);

        RentalAgreement rentalAgreement = new RentalAgreement(tool, rentalDays, checkoutDate, discountPercentage);

        Receipt receipt = new Receipt(rentalAgreement);

        assertEquals(1, receipt.getChargeDays());
        assertEquals(199, receipt.getPreDiscountChargeCents());
        assertEquals(BigDecimal.valueOf(0.01), receipt.getDiscountPercentAsDecimal());
        assertEquals(BigDecimal.valueOf(2L), receipt.getDiscountCents());
        assertEquals(BigDecimal.valueOf(197L), receipt.getFinalChargeCents());
    }

    @Test
    void testReceiptForOneDayOnePercentDiscountTenDays() {
        Tool tool = Tool.builder().brand(brand).type(LADDER).code(code).build();
        int rentalDays = 11;
        LocalDate checkoutDate = LocalDate.of(2024, 7, 1);
        BigDecimal discountPercentage = BigDecimal.valueOf(10L);

        RentalAgreement rentalAgreement = new RentalAgreement(tool, rentalDays, checkoutDate, discountPercentage);

        Receipt receipt = new Receipt(rentalAgreement);

        assertEquals(10, receipt.getChargeDays());
        assertEquals(1990, receipt.getPreDiscountChargeCents());
        assertEquals(BigDecimal.valueOf(0.1), receipt.getDiscountPercentAsDecimal());
        assertEquals(BigDecimal.valueOf(199L), receipt.getDiscountCents());
        assertEquals(BigDecimal.valueOf(1791L), receipt.getFinalChargeCents());
    }
}