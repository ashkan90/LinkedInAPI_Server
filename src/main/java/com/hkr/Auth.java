package com.hkr;

public class Auth {
    protected static String id;

    public static String id(String id) {
        Auth.id = id;
        return Auth.id;
    }

    public static String id() {
        return Auth.id;
    }

    public Object clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException("This class is not clonnable");
    }


}
