package domainModel.system;

import java.text.SimpleDateFormat;

/**
 * Author: Difan Chen
 * Date: 25/10/2016
 * Student No: 4901496
 */
public class JsonConstant {
    final public static String SUCCESS = "SUCCESS";
    final public static String ERR_MSG = "ERRMSG";
    final public static String REDIRECT_URL = "redirect_url";

    public static void main(String[] args) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String a = "2016-10-26";
        System.out.println(sdf.parse(a));
    }
}
