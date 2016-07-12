package com.softgarden.garden.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 封装了操作string的方法
 */
public class StringUtils {
    public final static String UTF_8 = "utf-8";

    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        if (value != null && !"".equalsIgnoreCase(value.trim())
                && !"null".equalsIgnoreCase(value.trim())) {
            return false;
        } else {
            return true;
        }
    }

    public static String getMd5String(String pwd) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
//		pwd=pwd+"fdsaf4dsa56f6e4ret5yT%ygfd1516";
        StringBuffer buffer = new StringBuffer();
        byte[] result = digest.digest(pwd.getBytes());

        for (byte b : result) {

            int num = b & 0xff;//和ff进行与运算。再转成16位的字符串。如果长度为1则补0

            String temp = Integer.toHexString(num);

            if (temp.length() == 1) {
                buffer.append("0");
            }
            buffer.append(temp);

        }
        return buffer.toString();
    }


    /**
     * 判断是否是邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
//        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public static boolean isUserName(String name) {
        String regex = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
        return name.matches(regex);
    }

    public static boolean isPhone(String phone) {

        return phone.matches("^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$");

    }

    public static boolean isIdCard(String idCard) {
        return idCard.matches("^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$");

    }

    public static boolean isTel(String tel) {
        return tel.matches("^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$");

    }


    /**
     * 将unix时间戳转换成日期 格式,时间说明
     * <p/>
     * 当天24小时制显示时分，如"15:40"
     * <p/>
     * 昨天明天加上"昨天、明天"，如"昨天 15:40"
     * <p/>
     * 其他显示"9-23 14:59"
     * <p/>
     * 非本年显示"2015-9-23 15:00"
     * <p/>
     * <p/>
     * 步骤： 1.获取当前时间的毫秒数
     * 2.当前时间减要显示的时间，得到相差的毫秒数，
     * 如果如果相差的毫秒数是正数，则是昨天或者今天，否则时间是明天或者未来的
     * 3.将相差毫秒数的毫秒数/（1000*60）*60*24,如果大于1.<2.则是昨天，返回昨天时分秒
     * 4.将相差毫秒数的毫秒数/（1000*60）*60*24,如果小于1则是今天，返回时分秒
     * 5.如果相差的毫秒数是负数。则表示时间是明天或者未来的
     * 6.将相差毫秒数的毫秒数取绝对值/（1000*60）*60*24,如果大于1.<2.则是明天，返回明天时分秒
     * 7.以上都不是的话，将毫秒数转换成年，如果超过一年，则显示年月日时分秒，
     * 8.如果不超过一年，则显示月日时分秒，
     *
     * @param timestampString
     * @return
     */
    public static String TimeStamp2Date(String timestampString) {
        if (isEmpty(timestampString)) {
            return "";
        }
        try {
            SimpleDateFormat format;
            Long timestamp = Long.parseLong(timestampString) * 1000;
            long curr = System.currentTimeMillis();
            //相差的天数
            long tempDay;
            long temp = curr - timestamp;
            if (temp > 0) {
                //如果相差的毫秒数是正数，则是昨天或者今天以前的，否则时间是明天或者未来的
                tempDay = temp / (1000 * 60 * 60 * 24);
                if (tempDay >= 1.0 && tempDay < 2.0) {
                    format = new SimpleDateFormat("HH:mm");
                    //将相差毫秒数的毫秒数/（1000*60）*60*24,如果大于1.<2.则是昨天，返回昨天时分秒
                    return "昨天" + format.format(timestamp);
                } else if (tempDay < 1.0) {
                    format = new SimpleDateFormat("HH:mm");
                    //将相差毫秒数的毫秒数/（1000*60）*60*24,如果小于1则是今天，返回时分秒
                    LogUtils.i("今天：" + timestampString);
                    return format.format(timestamp);
                } else if (tempDay < 365) {
                    format = new SimpleDateFormat("MM-dd HH:mm");
                    //以上都不是的话，将毫秒数转换成年，如果超过一年，则显示年月日时分秒，
                    return format.format(timestamp);
                } else {
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    //如果不超过一年，则显示月日时分秒，
                    return format.format(timestamp);
                }


            } else {
                tempDay = timestamp - curr;
                tempDay = temp / (1000 * 60 * 60 * 24);
                //如果相差的毫秒数是正数，则是当前时间以后的
                tempDay = temp / (1000 * 60 * 60 * 24);
                if (tempDay >= 1.0 && tempDay < 2.0) {
                    format = new SimpleDateFormat("HH:mm");
                    //将相差毫秒数的毫秒数/（1000*60）*60*24,如果大于1.<2.则是昨天，返回昨天时分秒
                    return "明天" + format.format(timestamp);
                } else if (tempDay < 1.0) {
                    format = new SimpleDateFormat("HH:mm");
                    //将相差毫秒数的毫秒数/（1000*60）*60*24,如果小于1则是今天，返回时分秒
                    return format.format(timestamp);
                } else if (tempDay < 365) {
                    format = new SimpleDateFormat("MM-dd HH:mm");
                    //以上都不是的话，将毫秒数转换成年，如果超过一年，则显示年月日时分秒，
                    return format.format(timestamp);
                } else {
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    //如果不超过一年，则显示月日时分秒，
                    return format.format(timestamp);
                }


            }


//			步骤： 1.获取当前时间的毫秒数
//					* 2.当前时间减要显示的时间，得到相差的毫秒数，
//			* 如果如果相差的毫秒数是正数，则是昨天或者今天，否则时间是明天或者未来的
//					* 3.将相差毫秒数的毫秒数/（1000*60）*60*24,如果大于1.<2.则是昨天，返回昨天时分秒
//					* 4.将相差毫秒数的毫秒数/（1000*60）*60*24,如果小于1则是今天，返回时分秒
//					* 5.如果相差的毫秒数是负数。则表示时间是明天或者未来的
//					* 6.将相差毫秒数的毫秒数取绝对值/（1000*60）*60*24,如果大于1.<2.则是明天，返回明天时分秒
//					* 7.以上都不是的话，将毫秒数转换成年，如果超过一年，则显示年月日时分秒，
//			*8.如果不超过一年，则显示月日时分秒，
//			String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
//			return date;
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * 将unix时间戳转换成日期 格式
     *
     * @param timestampString
     * @return
     */
    public static String TimeStamp2DateTime(String timestampString) {
        if (isEmpty(timestampString)) {
            return "";
        }
        try {
            Long timestamp = Long.parseLong(timestampString) * 1000;
            String date = new SimpleDateFormat("MM-dd HH:mm").format(new java.util.Date(timestamp));
            return date;
        } catch (Exception e) {
            return timestampString;
        }
    }

    /**
     * 将unix时间戳转换成日期 格式
     *
     * @param timestampString
     * @return
     */
    public static String getDate(String timestampString) {
        if (isEmpty(timestampString)) {
            return "";
        }
        try {
            Long timestamp = Long.parseLong(timestampString) * 1000;
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));
            return date;
        } catch (Exception e) {
            return timestampString;
        }
    }

    /**
     * 将unix时间戳转换成小时 格式
     *
     * @param timestampString
     * @return
     */
    public static String getTime(String timestampString) {
        if (isEmpty(timestampString)) {
            return "";
        }
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat("HH:mm").format(new java.util.Date(timestamp));
        return date;
    }


    /**
     * 将unix时间戳转换成日期 格式
     *
     * @param
     * @return
     */
    public static String TimeStamp2Date(long time) {

        Long timestamp = time * 1000;
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new java.util.Date(timestamp));
        return date;
    }


    /**
     * 将unix时间戳转换成日期 格式
     *
     * @param
     * @return yyyy.MM.dd
     */
    public static String TimeStamp2ShortDate(long time) {
        Long timestamp = time * 1000;
        String date = new SimpleDateFormat("yyyy.MM.dd").format(new java.util.Date(timestamp));
        return date;
    }

    /**
     * 将unix时间戳转换成时间 格式
     *
     * @param
     * @return
     */
    public static String TimeStamp2Time(long time) {
        Long timestamp = time * 1000;
        String date = new SimpleDateFormat("HH:mm").format(new java.util.Date(timestamp));
        return date;
    }

    /**
     * 获取当前时间日期
     *
     * @return
     */
    public static String getCurrDate() {
        Long timestamp = System.currentTimeMillis();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
        return date;
    }

    /**
     * 获取当前时间日期
     *
     * @return
     */
    public static String getCurrDay() {
        Long timestamp = System.currentTimeMillis();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));
        return date;
    }

    /**
     * 返回.00格式的数字
     * @param num
     * @return
     */
    public static String getNumFormat(String num) {
        try {
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(Double.parseDouble(num));
        }catch (Exception ex){
            return num;
        }

    }


}
