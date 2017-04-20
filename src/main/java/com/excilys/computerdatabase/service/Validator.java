package com.excilys.computerdatabase.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.persistance.dto.ComputerDTO;

public enum Validator {
    INSTANCE;

    private static final String COMPUTER_ID = "id";
    private static final String COMPUTER_NAME = "name";
    private static final String INTRODUCED_DATE = "introduced";
    private static final String DISCONTINUED_DATE = "discontinued";

    static final Logger LOG = LoggerFactory.getLogger(Validator.class);

    /**
     * Method to validate a computer, returns a map of errors.
     *
     * @param computerToValidate - The computer to validate
     * @param errors - The map to fill with errors
     * @return The errors map filled
     */
    public static Map<String, String> validate(ComputerDTO computerToValidate) {
        Map<String, String> errors = new HashMap<>();
        if(computerToValidate.getComputerId() != null) {
            String computerId = validateComputerId(String.valueOf(computerToValidate.getComputerId()));
            if (computerId != null) {
                errors.put(COMPUTER_ID, computerId);
            }
        }
        String computerName = validateName(computerToValidate.getComputerName());
        if (computerName != null) {
            errors.put(COMPUTER_NAME, computerName);
        }
        String introducedVal = validateDate(computerToValidate.getIntroducedDate());
        if (introducedVal != null) {
            errors.put(INTRODUCED_DATE, introducedVal);
        }
        String discontinuedVal = validateDate(computerToValidate.getDiscontinuedDate());
        if (discontinuedVal != null) {
            errors.put(DISCONTINUED_DATE, discontinuedVal);
        }
        if (errors.isEmpty()) {
            String discontinuedValOK = validateDateNotBeforeDate(computerToValidate.getDiscontinuedDate(), computerToValidate.getIntroducedDate());
            if (discontinuedValOK != null) {
                errors.put(DISCONTINUED_DATE, discontinuedValOK);
            }
        }
        return errors;
    }

    /**
     * Check if the parameter id is not empty.
     *
     * @param computerId - The id to check
     * @return null if valid or the error
     */
    private static String validateComputerId(String computerId) {
        // Cannot be null..
        if (computerId != null) {
            // ..or empty
            if (computerId.trim().isEmpty()) {
                LOG.info("The computer id is empty.");
                return "The computer id is empty.";
            }
            try {
                Long.parseLong(computerId);
                return null;
            } catch (NumberFormatException e) {
                LOG.info("The parameter is not an id.");
                return "The parameter is not an id.";
            }
        } else {
            return null;
        }
    }

    /**
     * Check if the name is not empty and longer than 2 characters.
     *
     * @param computerName - The name to check
     */
    private static String validateName(String computerName) {
        if (computerName == null || computerName.trim().isEmpty()) {
            return "The name is empty. Please try again";
        } else {
            String patternString = "^(.(.+))$";
            Pattern pattern = Pattern.compile(patternString);
            Matcher match = pattern.matcher(computerName);
            if (!match.matches() || computerName.length() < 2) {
                return "The name needs to contain at least two characters. Please try again.";
            } else {
                return null;
            }
        }
    }

    /**
     * Validates the input date.
     *
     * @param date - The date to validate
     */
    private static String validateDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            // Can be null or empty
            return null;
        } else {
            try {
                LocalDate.parse(date);
            } catch (DateTimeParseException e) {
                return "Please enter a valid date.";
            }
            String patternString = "^(\\d{4})-(\\d{2})-(\\d{2})$";
            Pattern pattern = Pattern.compile(patternString);
            Matcher match = pattern.matcher(date);
            if (match.matches()) {
                String[] results = date.split("-");
                int year = Integer.parseInt(results[0]);
                int month = Integer.parseInt(results[1]);
                int day = Integer.parseInt(results[2]);
                // Testing the validity of the date
                if (year % 4 == 0 && month == 2 && day == 29) {
                    // February of leap years
                    return null;
                } else if (month == 2 && day > 28) {
                    // February of other years
                    return "February had only 28 days that year. Please enter a valid date.";
                } else if ((month == 1 || month == 3 || month == 5 || month == 7
                        || month == 8 || month == 10 || month == 12)
                        && day > 31) {
                    // Months with 31 days
                    return "That month doesn't have more than 31 days. Please enter a valid date.";
                } else if ((month == 4 || month == 6 || month == 9
                        || month == 11) && day > 30) {
                    // Months with 30 days
                    return "The month doesn't have more than 30 days. Please enter a valid date.";
                } else if (month > 12) {
                    // Month cannot be > 12
                    return "There are only 12 months in a year. Please enter a valid date.";
                } else if (LocalDate.parse(date).isAfter(LocalDate.parse("1970-01-01"))) {
                    // Is after 1970-01-01
                    return null;
                } else {
                    return "The date must be after 1970-01-01.";
                }
            } else {
                return "Invalid date format.";
            }
        }
    }

    /**
     * Check if the parameters introduced and discontinued dates are dates and
     * that the introduced one is before the discontinued date.
     *
     * @param introducedDate - The introduced date
     * @param discontinuedDate - The discontinued date
     */
    private static String validateDateNotBeforeDate(String discontinuedDate, String introducedDate) {
        if (introducedDate != null && discontinuedDate != null && !introducedDate.trim().isEmpty()  && !discontinuedDate.trim().isEmpty()) {
            if (LocalDate.parse(introducedDate).isAfter(LocalDate.parse(discontinuedDate))) {
                return "The introduced date is after the discontinued date";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}