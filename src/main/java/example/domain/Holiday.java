package example.domain;

import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.Month;

/**
 * The Holiday class represents a holiday with specific properties such as its type,
 * date or occurrence rules, and whether it is observed on the nearest weekday.
 *
 * Holidays can be categorized into two types:
 *
 * - Fixed-day holidays: These occur on the same calendar date every year.
 * - Nth-weekday holidays: These occur on a specific weekday and week of a given month.
 */
@Data
@Builder
public class Holiday {
    public enum HolidayType {
        FIXED_DAY,
        NTH_WEEKDAY
    }

    // @Enumerated(EnumType.STRING)
    private HolidayType type;

    private boolean observedOnClosestWeekday;

    // @Enumerated(EnumType.STRING)
    private Month month;

    private int dayOfMonth;

    // @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private int nthOfMonth;
}
