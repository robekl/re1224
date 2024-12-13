package example.utils;

import example.domain.Charge;
import example.repository.HolidayRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class that provides helper methods for handling date-related calculations
 * and determining chargeable days in a rental scenario.
 */
public class CalendarHelper {
    /**
     * Calculates the number of chargeable days within a rental period, based on the rental rules
     * for weekends, weekdays, and holidays. Non-chargeable days are excluded based on the
     * specifications of the provided {@link Charge} object.
     *
     * @param checkoutDate the starting date of the rental period
     * @param rentalDays the length of the rental period in days
     * @param charge the charge configuration which dictates whether weekends, holidays,
     *               and weekdays are chargeable
     * @return the number of chargeable days within the provided rental period
     */
    public static int calculateChargeDays(
            LocalDate checkoutDate,
            int rentalDays,
            Charge charge) {
        Set<LocalDate> holidays = determineHolidays(checkoutDate, rentalDays);

        LocalDate date = checkoutDate.plusDays(1);
        int chargeDays = 0;
        
        // Loop through each day in the rental period to calculate the number of chargeable days.
        // Excludes days based on the “no charge” rules for weekends, weekdays, and holidays.
        while (rentalDays-- > 0) {
            // Count of chargeable days, excluding “no charge” days
            if ((isWeekend(date.getDayOfWeek()) && !charge.isChargedOnWeekend()) ||
                    (isWeekday(date.getDayOfWeek()) && !charge.isChargedOnWeekday()) ||
                    (holidays.contains(date) && !charge.isChargedOnHoliday())) {
                // this type of day is excluded
            } else {
                chargeDays++;
            }
            date = date.plusDays(1);
        }

        return chargeDays;
    }

    /**
     * Determines the set of holidays within a specified rental period, based on the checkout date 
     * and rental duration. The holidays are identified through a repository, and adjustments 
     * are made for weekends if required based on their observance rules.
     *
     * @param checkoutDate the starting date of the rental period
     * @param rentalDays the length of the rental period in days
     * @return a set of LocalDate objects representing the holidays within the rental period
     */
    private static Set<LocalDate> determineHolidays(LocalDate checkoutDate, int rentalDays) {
        HolidayRepository holidayRepository = new HolidayRepository();
        Set<LocalDate> holidayDates = new HashSet<>();

        // get the year for the last day of the rental period
        int endYear = checkoutDate.plusDays(rentalDays).getYear();

        // Iterate through all years within the rental period to calculate applicable holidays.
        // Each holiday is determined based on its type (e.g., fixed date or nth weekday) and 
        // adjusted for weekend observance rules before being added to the holiday set.
        holidayRepository.getHolidays().forEach(holiday -> {
            for (int year = checkoutDate.getYear(); year <= endYear; year++) {
                switch (holiday.getType()) {
                    case FIXED_DAY: {
                        holidayDates.add(
                                adjustForWeekendObservance(
                                        LocalDate.of(year, holiday.getMonth(), holiday.getDayOfMonth()),
                                        holiday.isObservedOnClosestWeekday()
                                )
                        );
                        break;
                    }
                    case NTH_WEEKDAY: {
                        LocalDate date = LocalDate.of(year, holiday.getMonth(), 1);
                        int weekdayOffset = holiday.getDayOfWeek().getValue() - date.getDayOfWeek().getValue();

                        // adjust date based on day-of-week difference from the desired weekday
                        date = date.plusDays((weekdayOffset + 7) % 7);

                        // adjust date based on which day-of-week is desired (eg the 2nd monday)
                        date = date.plusDays(7L * (holiday.getNthOfMonth() - 1));
                        if (date.getMonth() == holiday.getMonth()) {
                            holidayDates.add(
                                    adjustForWeekendObservance(date, holiday.isObservedOnClosestWeekday())
                            );
                        } else {
                            // TODO: how should an illegal holiday configuration be handled?
                            //       eg "the 10th Sunday of the month"
                            //       currently do nothing with the assumption that such a configuration
                            //       doesn't exist and that any newly added holidays will be tested
                        }
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("Unimplemented holiday type: " + holiday.getType());
                    }
                }
            }
        });

        return holidayDates;
    }

    /**
     * Adjusts a given date to account for weekend observance rules.
     * If the specified date falls on a weekend (Saturday or Sunday) and the
     * adjustment flag is enabled, the date is modified to the closest weekday
     * (Friday for Saturday and Monday for Sunday). If the adjustment flag is
     * disabled, the date remains unchanged.
     *
     * @param date the date to be adjusted
     * @param observedOnClosestWeekday a flag indicating whether to observe the date on
     *                                 the closest weekday when it falls on a weekend
     * @return the adjusted date reflecting the weekend observance rule, if applicable
     */
    private static LocalDate adjustForWeekendObservance(LocalDate date, boolean observedOnClosestWeekday) {
        if (observedOnClosestWeekday) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                return date.minusDays(1);
            } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return date.plusDays(1);
            } else {
                return date;
            }
        } else {
            return date;
        }
    }

    private static boolean isWeekend(DayOfWeek day) {
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    private static boolean isWeekday(DayOfWeek dayOfWeek) {
        return !isWeekend(dayOfWeek);
    }
}
