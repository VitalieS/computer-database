
package com.excilys.computerdatabase.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Validate {
    INSTANCE;

    static final Logger LOG = LoggerFactory.getLogger(Validate.class);

    // DateTimeFormatter form = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    /**
     * Check if the name is not empty, if is longer than 2 characters and if it contains letters, number and spaces.
     * @param id the id to check
     */
    public void checkName(String computerName) {
        if (computerName != null) {
            if (computerName.trim().isEmpty()) {
                LOG.info("The introduced parameter is empty.");
                throw new IllegalArgumentException("The introduced parameter is empty.");
            }
            if (computerName.length() < 2) {
                LOG.info("The introduced parameter is too short.");
                throw new IllegalArgumentException("The introduced parameter is too short.");
            }

            if(!computerName.matches("[a-zA-Z0-9 ]*")){
                LOG.info("The introduced parameter is not a valid name containing letters and numbers.");
                throw new IllegalArgumentException("The introduced parameter is not a valid name containing letters and numbers.");
            }

        } else {
            throw new IllegalArgumentException("Name cannot be null.");
        }
    }

    /**
     * Check if the parameter id is not empty.
     * @param id - The id to check
     */
    public void checkId(String id) {
        if (id != null) {
            if (id.trim().isEmpty()) {
                LOG.info("The introduced parameter is empty.");
                throw new IllegalArgumentException("The introduced parameter is empty.");
            }
            try {
                Long.parseLong(id);
            } catch (NumberFormatException e) {
                LOG.info("The introduced parameter is not an id.");
                throw new IllegalArgumentException("The introduced parameter is not an id.");
            }
        } else {
            throw new IllegalArgumentException("Please select a company.");
        }
    }

    /**
     * Check if the parameter date is a date.
     * @param date - The introduced date
     */
    public void checkDate(String date) {
        if (date != null && !date.trim().isEmpty()) {
            try {
                //LocalDate.parse(date, form);
                LocalDate.parse(date);
            } catch (DateTimeParseException e) {
                LOG.info("The given date is not a date");
                throw new IllegalArgumentException("Invalid format for date", e);
            }
        }
    }

    /**
     * Check if the parameters introduced and discontinued dates are dates and that the introduced one is before the discontinued date.
     * @param introducedDate - The introduced date
     * @param discontinuedDate - The discontinued date
     */
    public void checkDateNotBeforeDate(String introducedDate, String discontinuedDate) {
        if (introducedDate != null && discontinuedDate != null && !introducedDate.trim().isEmpty() && !discontinuedDate.trim().isEmpty()) {
            checkDate(introducedDate);
            checkDate(discontinuedDate);
            //LocalDate introducedDateLD = LocalDate.parse(introducedDate, form);
            //LocalDate discontinuedDateLD = LocalDate.parse(discontinuedDate, form);
            LocalDate introducedDateLD = LocalDate.parse(introducedDate);
            LocalDate discontinuedDateLD = LocalDate.parse(discontinuedDate);
            if (introducedDateLD.isAfter(discontinuedDateLD)) {
                LOG.info("The introduced date is after the discontinued date");
                throw new IllegalArgumentException("The introduced date is after the discontinued date");
            }
        }
    }

}