package example.domain;

import example.repository.ChargeRepository;
import lombok.AccessLevel;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The RentalAgreement class represents a contract for renting a tool. It captures the terms of the
 * rental, including the tool being rented, the rental duration, the discount applied, and the charges.
 * The class provides methods to compute secondary information such as the due date and daily rental charges.
 *
 * Each RentalAgreement is initialized with a specific tool, rental duration, checkout date, and discount
 * percentage, and determines the associated charges based on the tool type using a ChargeRepository.
 */
@Getter
public class RentalAgreement {
    private final Tool tool;
    private final int rentalDays;
    private final LocalDate checkOutDate;
    private final Charge charge;
    private final BigDecimal discountPercentage;

    @Getter(AccessLevel.NONE)
    private final ChargeRepository chargeRepository = new ChargeRepository();

    public RentalAgreement(Tool tool,
                           int rentalDays,
                           LocalDate checkoutDate,
                           BigDecimal discountPercentage
                           ) {
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.checkOutDate = checkoutDate;
        this.discountPercentage = discountPercentage;
        this.charge = chargeRepository.getCharge(tool.getType());
    }

    public LocalDate getDueDate() {
        return checkOutDate.plusDays(rentalDays);
    }

    public int getDailyRentalChargeInCents() {
        return charge.getDailyChargeCents();
    }
}
