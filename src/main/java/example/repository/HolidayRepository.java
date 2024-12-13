package example.repository;

import example.domain.Holiday;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.List;

/**
 * The HolidayRepository class provides a predefined repository of holiday information.
 * It contains a list of holidays, each defined with properties such as the holiday type,
 * month, day of the month, and observance rules.
 *
 * This repository is intended to simplify access to holiday data for use in scheduling
 * or date-related computations. Holidays are categorized into two types:
 * - Fixed-day holidays, which occur on the same date every year.
 * - Nth-weekday holidays, which occur on a specific weekday and week of a given month.
 *
 * The data is initialized as a static list and is immutable, ensuring consistent holiday data
 * throughout the application lifecycle. Specific holidays may include additional configuration,
 * such as whether they are observed on the closest weekday when falling on a weekend.
 *
 * Looking forward, this can be moved to a database with minimal changes in other classes.
 */
@Getter
public class HolidayRepository {
    private final List<Holiday> holidays = List.of(
            Holiday.builder().type(Holiday.HolidayType.FIXED_DAY).month(Month.JULY).dayOfMonth(4).observedOnClosestWeekday(true).build(),
            Holiday.builder().type(Holiday.HolidayType.NTH_WEEKDAY).month(Month.SEPTEMBER).nthOfMonth(1).dayOfWeek(DayOfWeek.MONDAY).build()
    );
}
