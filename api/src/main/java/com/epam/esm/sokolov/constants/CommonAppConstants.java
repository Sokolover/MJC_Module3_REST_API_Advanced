package com.epam.esm.sokolov.constants;

public final class CommonAppConstants {
    public static final String INCORRECT_PAGE_SIZE_MESSAGE = "size or page parameters set in URI incorrectly";
    public static final String INCORRECT_TAG_NAMES_MESSAGE = "tagNames parameters set in URI incorrectly";
    public static final Long DEFAULT_PAGE_SIZE = 10L;
    public static final Long DEFAULT_PAGE_NUMBER = 0L;
    public static final String USERS_REF = "users";
    public static final String ORDERS_REF = "orders";
    public static final String TAG_NAMES_SEARCH_PARAM = "tagNames";
    public static final String USERNAME_REQUEST_ATTRIBUTE = "username";
    public static final String JWT_REQUEST_ATTRIBUTE = "jwt";
    public static final int BEARER_HEADER_OFFSET = 7;
    public static final String BEARER = "Bearer ";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String EMAIL_PATTERN = "^(.+)@(.+)$";
    public static final String CONVERT_ERROR_MESSAGE = "Can't convert %s entity - source is null";

    private CommonAppConstants() {
    }
}
