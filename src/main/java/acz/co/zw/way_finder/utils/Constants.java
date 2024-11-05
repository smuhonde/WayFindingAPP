package acz.co.zw.way_finder.utils;

import java.util.Locale;


public interface Constants {
    String AUDIT_DATE_FORMAT ="dd/MM/yyyy";
    int TEMPORARY_SCALE = 8;
    int FINAL_SCALE = 4;
    int DISPLAY_SCALE = 2;
    int PRECISION = 19;
    Locale DEFAULT_LOCALE = new Locale("en");

    interface ResponseCodes {
        interface Acs {
            String SUCCESS = "200";
        }
    }
    interface ContextDataKeys {
        String REQUEST_SOURCE = "REQUEST_SOURCE";
        String ERROR_MESSAGE = "ERROR_MESSAGE";
        String CURRENT_LOAN_BALANCE = "CURRENT_LOAN_BALANCE";
        String SUCCESS_MESSAGE = "SUCCESS_MESSAGE";
        String CURRENCY_SYMBOL = "CURRENCY_SYMBOL";
        String DEFAULT_CONTENT_TYPE = "application/octet-stream";
        String COUNTRY_CODE = "COUNTRY_CODE";
        String AMOUNT = "AMOUNT";
    }

    interface ContextDataValues {
        String SELF_INITIATED_CREDIT = "self";
        String AUTOMATIC_CREDIT = "auto";
    }

    interface Parameters {
        String REQUEST_SOURCE = "acs";
        String API_KEY = "YWNzMTQxMDIwMTUxNTA4";
    }


    interface ResponseMessages {
        String INVALID_SELECTION = "The option you supplied is invalid";
        String PROCESSING_ERROR = "An error occured while processing your api request. Please try again later";
    }
}
