package com.vitweb.vitwebapi.application.constants;

public class UrlConstant {

  public static class User {
    private User() {
    }

    private static final String PREFIX = "/users";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String EDIT = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";
  }

  public static class Role {
    private Role() {
    }

    private static final String PREFIX = "/roles";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String EDIT = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";
  }

}