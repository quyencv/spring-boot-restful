package com.tutorial.constant;

public class UrlPathConstant {

    public interface Authentication {
        String PRE_URL = "/api/authentication";
        String SIGN_UP = "sign-up";
        String SIGN_IN = PRE_URL + "/sign-in";
        String SIGN_OUT = PRE_URL + "/sign-out";
    }

    public interface Internal {
        String PRE_URL = "api/internal";
    }

    public interface Book {
        String PRE_URL = "api/book";
        String DELETE = "{bookId}";
    }

    public interface PurchaseHistory {
        String PRE_URL = "api/purchase-history";
    }

}
