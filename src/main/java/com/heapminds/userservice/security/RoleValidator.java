package com.heapminds.userservice.security;

public class RoleValidator {
    public static final String IS_ADMIN = "hasAnyAuthority('ADMIN')";
    public static final String IS_PREMIUM_USER = "hasAnyAuthority('PREMIUM_USER')";
    public static final String IS_INSTRUCTOR = "hasAnyAuthority('INSTRUCTOR')";
    public static final String IS_USER = "hasAnyAuthority('USER')";
}
