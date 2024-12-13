package example.domain;

import lombok.Builder;
import lombok.Data;

/**
 * The Charge class represents a pricing model for tool rentals. It defines the type of tool
 * and the daily rental charge in cents, as well as whether charges are applicable on weekdays,
 * weekends, and holidays.
 *
 * This class is used to encapsulate the charge-related details of a tool type, which can
 * later be used to calculate rental costs based on a specific rental period and associated rules.
 */
@Data
@Builder
public class Charge {
    private String type;

    private int dailyChargeCents;

    private boolean isChargedOnWeekday;

    private boolean isChargedOnWeekend;

    private boolean isChargedOnHoliday;
}
