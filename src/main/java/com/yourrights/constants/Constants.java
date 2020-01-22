package com.yourrights.constants;

public class Constants {

    public static final String PROTEST_REST = "/protests";
    public static final String CREATE = "/create";
    public static final String LIST = "/list";
    public static final String SEARCH = "/search";
    public static final String LOGIN = "/login";
    public static final String SIGN_UP = "/sign-up";
    public static final String REGENERATE_PWD = "/regenerate_pwd";
    public static final String FORGOT_PWD = "/forgot_pwd";

    public static final String FORMAT_DD_MM_YYYY = "dd-MM-yyyy";
    public static final String FORMAT_HH_MM = "HH:mm";

    public static final String ERROR = "error";
    public static final String WARNING = "warning";

    public static final String WAR_001 = "WAR_001"; // Protest very similar
    public static final String WAR_002 = "WAR_002"; // Protest in the same city at the same time

    public static final String USER_EXISTED = "ERR_USER_01"; // User already exists
    public static final String USER_WRONG_PASSWORD = "ERR_USER_02"; // User wrong password
    public static final String USER_NOT_FOUND = "ERR_USER_03"; // User not found
    public static final String ERROR_SENDING_MAIL = "ERR_USER_04"; // User not found

    public enum Months {
	ENERO, FEBRERO, MARZO, ABRIL, MAYO, JUNIO, JULIO, AGOSTO, SEPTIEMBRE, OCTUBRE, NOVIEMBRE, DICIEMBRE;
    }
}
