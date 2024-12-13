package example.repository;

import example.domain.Charge;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static example.utils.Constants.*;

/**
 * The ChargeRepository class serves as a repository for predefined charge data associated
 * with different tool types. This repository is used to retrieve charge details such as
 * daily rental charge rates and rules for when charges apply (e.g., weekdays, weekends, holidays).
 *
 * The charge configuration is initialized as a static collection of Charge objects, making
 * it easily accessible across the application. Looking forward,
 * this can be moved to a database with minimal changes in other classes.
 */
public class ChargeRepository {
    private static final Map<String, Charge> charges = Stream.of(
                    Charge.builder().type(LADDER).dailyChargeCents(199).isChargedOnWeekday(true).isChargedOnWeekend(true).isChargedOnHoliday(false).build(),
                    Charge.builder().type(CHAINSAW).dailyChargeCents(149).isChargedOnWeekday(true).isChargedOnWeekend(false).isChargedOnHoliday(true).build(),
                    Charge.builder().type(JACKHAMMER).dailyChargeCents(299).isChargedOnWeekday(true).isChargedOnWeekend(false).isChargedOnHoliday(false).build()
            )
        .collect(Collectors.toMap(Charge::getType, Function.identity()));

    public Charge getCharge(String type) {
        return charges.get(type);
    }
}
