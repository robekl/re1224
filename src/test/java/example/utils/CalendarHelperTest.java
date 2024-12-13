package example.utils;

import example.domain.Charge;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalendarHelperTest {
    @Test
    void testCalculateChargeDaysForOneRentalDay() {
        Charge charge = Charge.builder().isChargedOnWeekday(true).isChargedOnWeekend(true).isChargedOnHoliday(true).build();
        LocalDate checkoutDate = LocalDate.of(2024, 12, 1);
        int result = CalendarHelper.calculateChargeDays(checkoutDate, 1, charge);

        assertEquals(1, result);
    }

    @Test
    void testCalculateChargeDaysForSevenRentalDays() {
        Charge charge = Charge.builder().isChargedOnWeekday(true).isChargedOnWeekend(true).isChargedOnHoliday(true).build();
        LocalDate checkoutDate = LocalDate.of(2024, 12, 1);
        int result = CalendarHelper.calculateChargeDays(checkoutDate, 7, charge);

        assertEquals(7, result);
    }

    @Test
    void testCalculateChargeDaysFor31RentalDays() {
        Charge charge = Charge.builder().isChargedOnWeekday(true).isChargedOnWeekend(true).isChargedOnHoliday(true).build();
        LocalDate checkoutDate = LocalDate.of(2024, 12, 1);
        int result = CalendarHelper.calculateChargeDays(checkoutDate, 31, charge);

        assertEquals(31, result);
    }

    @Test
    void testCalculateChargeDaysFor31RentalDaysWithoutWeekends() {
        Charge charge = Charge.builder().isChargedOnWeekday(true).isChargedOnWeekend(false).isChargedOnHoliday(true).build();
        LocalDate checkoutDate = LocalDate.of(2024, 12, 1);
        int result = CalendarHelper.calculateChargeDays(checkoutDate, 31, charge);

        assertEquals(23, result);
    }

    @Test
    void testCalculateChargeDaysFor31RentalDaysWithoutWeekdays() {
        Charge charge = Charge.builder().isChargedOnWeekday(false).isChargedOnWeekend(true).isChargedOnHoliday(true).build();
        LocalDate checkoutDate = LocalDate.of(2024, 12, 1);
        int result = CalendarHelper.calculateChargeDays(checkoutDate, 31, charge);

        assertEquals(8, result);
    }

    @Test
    void testCalculateChargeDaysFor31RentalDaysWithoutWeekdaysOrWeekends() {
        Charge charge = Charge.builder().isChargedOnWeekday(false).isChargedOnWeekend(false).isChargedOnHoliday(true).build();
        LocalDate checkoutDate = LocalDate.of(2024, 12, 1);
        int result = CalendarHelper.calculateChargeDays(checkoutDate, 31, charge);

        assertEquals(0, result);
    }

    @Test
    void testCalculateChargeDaysFor7RentalDaysWithoutHolidays() {
        Charge charge = Charge.builder().isChargedOnWeekday(true).isChargedOnWeekend(true).isChargedOnHoliday(false).build();
        LocalDate checkoutDate = LocalDate.of(2024, 9, 1);
        int result = CalendarHelper.calculateChargeDays(checkoutDate, 7, charge);

        assertEquals(6, result);
    }

    @Test
    void testCalculateChargeDaysFor400RentalDaysWithoutHolidays() {
        Charge charge = Charge.builder().isChargedOnWeekday(true).isChargedOnWeekend(true).isChargedOnHoliday(false).build();
        LocalDate checkoutDate = LocalDate.of(2024, 7, 1);
        int result = CalendarHelper.calculateChargeDays(checkoutDate, 400, charge);

        assertEquals(397, result);
    }

    @Test
    void testCalculateChargeDaysFor760RentalDaysWithoutHolidays() {
        Charge charge = Charge.builder().isChargedOnWeekday(true).isChargedOnWeekend(true).isChargedOnHoliday(false).build();
        LocalDate checkoutDate = LocalDate.of(2024, 7, 1);
        int result = CalendarHelper.calculateChargeDays(checkoutDate, 760, charge);

        assertEquals(755, result);
    }

    @Test
    void testCalculateChargeDaysFor7RentalDaysWithoutHolidaysAndWithoutWeekends() {
        Charge charge = Charge.builder().isChargedOnWeekday(true).isChargedOnWeekend(false).isChargedOnHoliday(false).build();
        LocalDate checkoutDate = LocalDate.of(2024, 9, 1);
        int result = CalendarHelper.calculateChargeDays(checkoutDate, 7, charge);

        assertEquals(4, result);
    }

    @Test
    void testCalculateChargeDaysFor7RentalDaysWithoutHolidaysAndWithoutWeekdays() {
        Charge charge = Charge.builder().isChargedOnWeekday(false).isChargedOnWeekend(true).isChargedOnHoliday(false).build();
        LocalDate checkoutDate = LocalDate.of(2024, 9, 1);
        int result = CalendarHelper.calculateChargeDays(checkoutDate, 7, charge);

        assertEquals(2, result);
    }

    @Test
    void testCalculateChargeDaysFor7RentalDaysWithoutHolidaysThatFallOnWeekend() {
        Charge charge = Charge.builder().isChargedOnWeekday(true).isChargedOnWeekend(true).isChargedOnHoliday(false).build();
        LocalDate checkoutDate = LocalDate.of(2020, 7, 1);
        int result = CalendarHelper.calculateChargeDays(checkoutDate, 3, charge);

        assertEquals(2, result);
    }

    @Test
    void testCalculateChargeDaysFor7RentalDaysWithoutHolidaysThatFallOnSunday() {
        Charge charge = Charge.builder().isChargedOnWeekday(true).isChargedOnWeekend(true).isChargedOnHoliday(false).build();
        LocalDate checkoutDate = LocalDate.of(2021, 7, 4);
        int result = CalendarHelper.calculateChargeDays(checkoutDate, 3, charge);

        assertEquals(2, result);
    }

    @Test
    void testCalculateChargeDaysFor7RentalDaysWithoutWeekendHolidaysObservedOnWeekday() {
        Charge charge = Charge.builder().isChargedOnWeekday(true).isChargedOnWeekend(true).isChargedOnHoliday(false).build();
        LocalDate checkoutDate = LocalDate.of(2021, 7, 1);
        int result = CalendarHelper.calculateChargeDays(checkoutDate, 7, charge);

        assertEquals(6, result);
    }
}