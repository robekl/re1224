package example.service;

import example.domain.Tool;
import example.domain.RentalAgreement;
import example.repository.ToolRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.temporal.ChronoField.*;

/**
 * The CheckoutService class handles the processing of tool rentals by creating a rental agreement
 * based on user-provided input and validating the input parameters. It facilitates the retrieval of
 * tool information, creation of a {@link RentalAgreement}, and printing of the rental receipt.
 *
 * This service ensures that:
 * - The required input arguments are correctly provided.
 * - Input arguments such as rental days and discount percentage are within valid ranges.
 * - Tool data is retrieved from the {@link ToolRepository}.
 * - A rental agreement specifying the rental terms is created.
 * - The receipt is printed using the {@link ReceiptPrinterService}.
 */
public class CheckoutService {
    private final ToolRepository toolRepository = new ToolRepository();
    private final ReceiptPrinterService receiptPrinterService = new ReceiptPrinterService();
    public static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendValue(MONTH_OF_YEAR, 2)
            .appendLiteral('/')
            .appendValue(DAY_OF_MONTH, 2)
            .appendLiteral('/')
            .appendValue(YEAR, 2)
            .toFormatter();

    /**
     * Processes a tool rental checkout by validating the input arguments, creating a rental agreement,
     * and printing a rental receipt. The method ensures that the provided arguments meet the required
     * format and logical constraints, retrieves tool data from the repository, calculates rental
     * charges, and outputs the details of the rental agreement.
     *
     * @param args An array of strings containing the required arguments for the tool rental checkout:
     *             args[0] - The tool code of the tool being rented.
     *             args[1] - The number of days the tool will be rented, as a string.
     *             args[2] - The discount percentage for the rental, as a string.
     *             args[3] - The checkout date in the format MM/dd/YY, as a string.
     */
    public void checkout(String[] args) {
        if (args.length != 4) {
            System.err.println("wrong number of arguments");
            printUsage();
            return;
        }

        String toolCode = args[0];
        int rentalDays;
        try {
            rentalDays = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("The rental day count must be a positive integer");
            printUsage();
            return;
        }
        BigDecimal discountPercentage;
        try {
            discountPercentage = new BigDecimal(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("The discount percentage must be a valid number");
            printUsage();
            return;
        }
        LocalDate checkoutDate;
        try {
            checkoutDate = LocalDate.parse(args[3], formatter);
        } catch (Exception e) {
            System.err.println("The checkout date must be formatted like MM/dd/YY");
            printUsage();
            return;
        }
        Tool tool = toolRepository.getTool(toolCode);

        if (rentalDays <= 0) {
            System.err.println("The number of rental days must be 1 or greater");
            printUsage();
            return;
        }

        if (!(discountPercentage.compareTo(BigDecimal.valueOf(0L)) >= 0 &&
                discountPercentage.compareTo(BigDecimal.valueOf(100L)) < 0)) {
            System.err.println("The discount percentage must be between 0 and 100");
            printUsage();
            return;
        }
        
        if (tool == null) {
            System.err.println("The tool code provided does not match any tool in the repository.");
            printUsage();
            return;
        }

        RentalAgreement rentalAgreement = new RentalAgreement(tool, rentalDays, checkoutDate, discountPercentage);

        receiptPrinterService.printReceipt(rentalAgreement);
    }

    private void printUsage() {
        System.out.println("required arguments: <tool code> <rental day count> <discount percent> <check out date>");
        System.out.println("where <check out date> is formatted like MM/dd/YY");
        System.out.println("and <tool code> is one of " + toolRepository.getAllToolCodes());
    }
}
