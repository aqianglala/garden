package com.softgarden.garden.interfaces;

/**
 * Created by qiang-pc on 2016/7/1.
 */
public interface UrlsAndKeys {
    String md5Str = "77&*Kouyubi%4p3";
    String API_SIGN_KEY = "Ysljsd&sfli%87wirioew3^534rjkljl";
    String baseUrl = "http://www.256app.com/jiadun/";
    String login = baseUrl + "index.php/App/Api/login";
    String getCode = baseUrl + "index.php/App/Api/getregVerify";
    String verifyCode = baseUrl + "index.php/App/Api/checkfindverify";
    String modifyPswd = baseUrl + "index.php/App/Api/modifyPassword";

    String USERNAME = "username";
    String TOKEN = "token";
    String PHONE = "phone";
    String HASMODIFYPSWD = "hasmodifypswd";
}
