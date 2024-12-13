package example.utils;

import example.domain.RentalAgreement;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a receipt detailing the charges and discounts associated with a rental agreement.
 * The receipt contains calculated values such as chargeable days, pre-discount charges,
 * discount amounts, and the final charge after applying any discounts.
 *
 * This class is immutable and is initialized using a {@link RentalAgreement} from which
 * necessary details such as rental days, checkout date, daily rental charges, and
 * discount percentage are obtained.
 */
@Getter
public class Receipt {
    private final int chargeDays;
    private final int preDiscountChargeCents;
    private final BigDecimal discountPercentAsDecimal;
    private final BigDecimal discountCents;
    private final BigDecimal finalChargeCents;

    public Receipt(RentalAgreement rentalAgreement) {
        chargeDays = CalendarHelper.calculateChargeDays(rentalAgreement.getCheckOutDate(), rentalAgreement.getRentalDays(), rentalAgreement.getCharge());
        preDiscountChargeCents = chargeDays * rentalAgreement.getDailyRentalChargeInCents();
        discountPercentAsDecimal = rentalAgreement.getDiscountPercentage().divide(BigDecimal.valueOf(100L));
        discountCents = discountPercentAsDecimal.multiply(BigDecimal.valueOf(preDiscountChargeCents)).setScale(0, RoundingMode.HALF_UP);
        finalChargeCents = BigDecimal.valueOf(preDiscountChargeCents).subtract(discountCents);
    }
}
