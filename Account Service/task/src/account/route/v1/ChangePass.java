package account.route.v1;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

public class ChangePass extends Auth {
    public static final String ROUTE =  "/changepass";
    public static final String PATH =  Auth.PATH + ROUTE;

    private static final String RESPONSE_TEMPLATE = "{\n" +
            "   \"email\": \"%s\",\n" +
            "   \"status\": \"The password has been updated successfully\"\n" +
            "}";

    public static String responseBuilder(@NotEmpty String email) {
        return String.format(RESPONSE_TEMPLATE, email );
    }

}
