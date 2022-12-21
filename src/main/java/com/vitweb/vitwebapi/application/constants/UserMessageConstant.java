package com.vitweb.vitwebapi.application.constants;

public class UserMessageConstant {

  public static final String ERR_EXCEPTION_GENERAL = "exception.general";
  public static final String ERR_EXCEPTION_ACCESS_SYSTEM = "exception.access.system";
  public static final String INVALID_SOME_THING_FIELD_IS_REQUIRED = "invalid.general.required";
  public static final String INVALID_SOME_THING_FIELD = "invalid.general";
  public static final String INVALID_AUTHENTICATION_INVALID_STUDENT_CODE = "student_code.is.invalid";
  public static final String INVALID_AUTHENTICATION_INVALID_EMAIL = "email.is.invalid";
  public static final String INVALID_AUTHENTICATION_INVALID_PASSWORD = "password.is.invalid";
  public static final String INVALID_AUTHENTICATION_INVALID_GENDER = "gender.is.invalid";
  public static final String INVALID_AUTHENTICATION_INVALID_PHONE = "phone.is.invalid";
  public static final String INVALID_AUTHENTICATION_INVALID_TOKEN = "phone.is.invalid";
  public static final String ERR_ACCESS_DENIED = "exception.access.denied";

  public static final String INVALID_CREATE_STUDENT_CODE = "student_code.is.created";

  public static final class User {
    public static final String ERR_NOT_FOUND_BY_ID = "invalid.not.found.user_id";
  }

  public static final class Role {
    public static final String ERR_NOT_FOUND_BY_ID = "invalid.not.found.role_id";
  }

  public static final class Category {
    public static final String ERR_NOT_FOUND_BY_ID = "invalid.not.found.category_id";
  }
}
