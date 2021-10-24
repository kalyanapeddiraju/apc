package resources.InputRequest;

import libapihelper.Helpers;

public class TestData {

    public static String ValidUserEmail(){
        String randomEmail=Helpers.generateRandomEmail("test.com");

        String inputRequest = "{" +
                "\"country_phone_code\":1," +
                "\"level\":3," +
                "\"busy\":false," +
                "\"available\":true," +
                "\"enabled\":true," +
                "\"email\":" + "\"" + randomEmail +"\","  +
                "\"password\": \"super1234\"" +
                "}";
        return inputRequest;
    }

    public static String InvalidUserEmail(){
        String randomEmail=Helpers.generateRandomEmail("test.com");

        String inputRequest = "{" +
                "\"country_phone_code\":1," +
                "\"level\":3," +
                "\"busy\":false," +
                "\"available\":true," +
                "\"enabled\":true," +
                "\"email\":" + "\" " + randomEmail +"\","  +
                "\"password\": \"super1234\"" +
                "}";
        return inputRequest;
    }

    public static String emailEmpty(){
        String randomEmail=Helpers.generateRandomEmail("test.com");

        String inputRequest = "{" +
                "\"country_phone_code\":1," +
                "\"level\":3," +
                "\"busy\":false," +
                "\"available\":true," +
                "\"enabled\":true," +
                "\"email\":" + "\" " + "\","  +
                "\"password\": \"super1234\"" +
                "}";
        return inputRequest;
    }

    public static String passwordEmpty(){
        String randomEmail=Helpers.generateRandomEmail("test.com");

        String inputRequest = "{" +
                "\"country_phone_code\":1," +
                "\"level\":3," +
                "\"busy\":false," +
                "\"available\":true," +
                "\"enabled\":true," +
                "\"email\":" + "\"" + randomEmail +"\","  +
                "\"password\": \"\"" +
                "}";
        return inputRequest;
    }

    public static String passWordLessThan8Char(){
        String randomEmail=Helpers.generateRandomEmail("test.com");

        String inputRequest = "{" +
                "\"country_phone_code\":1," +
                "\"level\":3," +
                "\"busy\":false," +
                "\"available\":true," +
                "\"enabled\":true," +
                "\"email\":" + "\"" + randomEmail +"\","  +
                "\"password\": \"super\"" +
                "}";
        return inputRequest;
    }

    public static String inValidEmailFormat(){
        String randomEmail=Helpers.generateRandomEmail("test");

        String inputRequest = "{" +
                "\"country_phone_code\":1," +
                "\"level\":3," +
                "\"busy\":false," +
                "\"available\":true," +
                "\"enabled\":true," +
                "\"email\":" + "\"" + randomEmail +"\","  +
                "\"password\": \"super1234\"" +
                "}";
        return inputRequest;
    }

    public static String inValidEmailNoDomain(){

        String inputRequest = "{" +
                "\"country_phone_code\":1," +
                "\"level\":3," +
                "\"busy\":false," +
                "\"available\":true," +
                "\"enabled\":true," +
                "\"email\":\"testemail1@test\","  +
                "\"password\": \"super1234\"" +
                "}";
        return inputRequest;
    }

}

