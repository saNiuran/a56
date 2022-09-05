package com.hokaslibs.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hokaslibs.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

//import timber.log.Timber;

/**
 * 作者： Hokas
 * 时间： 2016/12/8
 * 类别：
 */
@SuppressWarnings("Duplicates")
public class HokasUtils {
    private static final String TAG = "HokasUtils";
    private static String DATA = "yyyy年MM月dd日";
    private static String SHI_FEN = "MM-dd HH:mm";
    private static String SHI_FEN_MIAO = "yyyy-MM-dd HH:mm:ss";
    private static String DATA_YEAR_MONTH = "yyyy年MM月";
    private static String DATA_SHI_FEN = "HH:mm";
    private static String DATA_YONGSHI = "HH小时mm分";
    private static String DATA_NYR = "yyyy-MM-dd";
    private static String DATA_NYR_2 = "MM-dd";
    private static String SHI_FEN_2 = "yyyy-MM-dd HH:mm";


    public static String sex(int type) {
        String sex = null;
        switch (type) {
            case 0:
                sex = "女";
                break;
            case 1:
                sex = "男";
                break;
            case 2:
                sex = "保密";
                break;
        }
        return sex;
    }

    public static String getTotalMoneyString() {
        return "待议";
    }

    public static String getMoneyString() {
        return "单价:议价";
    }


    /**
     * yyyy/MM/dd
     *
     * @param time
     * @return
     */
    public static String getDateToString2(long time) {
        SimpleDateFormat sf = new SimpleDateFormat(DATA_NYR, Locale.getDefault());
        Date d = new Date(time);
        return sf.format(d);
    }

    /**
     * MM-dd
     *
     * @param time
     * @return
     */
    public static String getDateToString3(long time) {
        SimpleDateFormat sf = new SimpleDateFormat(DATA_NYR_2, Locale.getDefault());
        Date d = new Date(time);
        return sf.format(d);
    }

    /**
     * yyyy年MM月dd日
     *
     * @param time
     * @return
     */
    public static String getDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat(DATA, Locale.getDefault());
        Date d = new Date(time);
        return sf.format(d);
    }

    public static String getDateToStringShiFen(long time) {
        SimpleDateFormat sf = new SimpleDateFormat(SHI_FEN, Locale.getDefault());
        Date d = new Date(time);
        return sf.format(d);
    }

    public static String getDateToStringShiFenMiao(long time) {
        SimpleDateFormat sf = new SimpleDateFormat(SHI_FEN_MIAO, Locale.getDefault());
        Date d = new Date(time);
        return sf.format(d);
    }

    public static String getDateShiFen(long time) {
        SimpleDateFormat sf = new SimpleDateFormat(DATA_SHI_FEN, Locale.getDefault());
        Date d = new Date(time);
        return sf.format(d);
    }

    //获得当天0点时间
    public static long getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    //获得当天24点时间
    public static long getTimesnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static String getShiFen2(long time) {
        SimpleDateFormat sf = new SimpleDateFormat(SHI_FEN_2, Locale.getDefault());
        Date d = new Date(time);
        return sf.format(d);
    }

    public static String getDateYongShi(long taskStartTime, long taskEndTime) {
        long l = taskEndTime - taskStartTime;
        long day = l / (24 * 60 * 60 * 1000);
        long hour = Math.abs(l / (60 * 60 * 1000) - day * 24);
        long min = Math.abs((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = Math.abs(l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        return hour + "小时" + min + "分";
    }

    //获取2个时间之间的天数
    public static long getDateDays(long taskStartTime, long taskEndTime) {
        long l = taskEndTime - taskStartTime;
        long day = l / (24 * 60 * 60 * 1000);
        return day;
    }

    public static boolean is5MinTime(long time) {
        return time < getMinTime(5);
    }

    public static boolean is10MinTime(long time) {
        return getMinTime(5) < time && time < getMinTime(10);
    }

    public static boolean is15MinTime(long time) {
        return time < getMinTime(15);
    }

    public static boolean is30MinTime(long time) {
        return time < getMinTime(30);
    }

    public static long getMinTime(int min) {
        return min * 60 * 1000;
    }

    public static long getDayTime(int day) {
        return day * 60 * 24 * 60 * 1000;
    }

    public static boolean isSuperTime(long time, int superTime) {
        Log.d(TAG, "time:" + time);
        Log.d(TAG, "superTime:" + superTime);
        return time < getMinTime(superTime);
    }

    /**
     * 年月
     *
     * @param time 时间
     * @return xxxx年xx月
     */
    public static String getDateToStringYearMonth(long time) {
        SimpleDateFormat sf = new SimpleDateFormat(DATA_YEAR_MONTH);
        Date d = new Date(time);
        return sf.format(d);
    }

    /**
     * yy年MM月dd日
     *
     * @param time
     * @return
     */
    public static String getDateToString00(long time) {
        SimpleDateFormat sf = new SimpleDateFormat(DATA);
        Date d = new Date(time);
        return sf.format(d).substring(2, sf.format(d).length());
    }

    /**
     * 按行读取txt
     */
    public static String readTextFromSDcard(Context context, String fileName) {
        StringBuilder buffer = new StringBuilder("");
        try {
            InputStream is = null;
            is = context.getAssets().open(fileName);
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
                buffer.append("\n");
            }
        } catch (IOException e) {
            Log.d("HddDateUtils", e.toString());
        }
        return buffer.toString();
    }


    public static String wanYuan(double moneys) {
        double money = moneys / 1000.00;
        String str = null;
        String strs[];
        if (money >= 10000000) {
            strs = HokasUtils.toMoney(money / 10000000).split("\\.");
//            strs = String.valueOf(money / 10000000).split("\\.");
            str = strs[0] + "." + strs[1] + "千万元";
        } else if (money >= 10000 && money < 10000000) {
            strs = HokasUtils.toMoney(money / 10000).split("\\.");
//            strs = String.valueOf(money / 10000).split("\\.");
            str = strs[0] + "." + strs[1] + "万元";
        } else if (money < 10000) {
            str = toMoney(money) + "元";  //一直保持有两位小数  2018年10月19日11:28:24
//            str = money + "元";
        }
        return str;
    }

    public static String wanYuanUnit(float money) {
        String str = null;
        String strs[];
        if (money >= 10000000) {
            strs = HokasUtils.toMoney(money / 10000000).split("\\.");
//            strs = String.valueOf(money / 10000000).split("\\.");
            str = strs[0] + "." + strs[1] + "千万";
        } else if (money >= 10000 && money < 10000000) {
            strs = HokasUtils.toMoney(money / 10000).split("\\.");
//            strs = String.valueOf(money / 10000).split("\\.");
            str = strs[0] + "." + strs[1] + "万";
        } else if (money < 10000) {
            str = String.valueOf(money);  //一直保持有1位小数  2018年10月23日18:09:25
//            str = money + "元";
        }
        return str;
    }

    public static int getFactoryAge(long news, long old) {
        String xinTime = getDateToString(news);
        String laoTime = getDateToString(old);
        int xin = Integer.parseInt(xinTime.substring(0, 4));
        int lao = Integer.parseInt(laoTime.substring(0, 4));
        return xin - lao;
    }

    /**
     * 验证手机号码
     */
    public static boolean isMobile(String mobiles) {
        String telRegex = "(1[3456789]\\d{9}$)|(9[28]\\d{9}$)";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 验证手机格式和电话号码
     */
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "(^(\\d{3,4}-)?\\d{7,8})$|(1[34578][0-9]\\d{8})$|(1[345789][0-9]-\\d{4}-\\d{4}$)";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 区号+座机号码+分机号码
     *
     * @param fixedPhone
     * @return
     */
    public static boolean isFixedPhone(String fixedPhone) {
        String reg = "(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
        if (TextUtils.isEmpty(fixedPhone)) return false;
        return Pattern.matches(reg, fixedPhone);
    }

    /**
     * 验证400的电话号码
     */
    public static boolean isCompany400NO(String mobiles) {
        String telRegex = "(^400-[016789]\\d{2}-\\d{4}$|400[016789]\\d{6}$)";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }


    public static boolean isPhone(String mobiles) {
        return !TextUtils.isEmpty(mobiles) && (isMobileNO(mobiles) || isFixedPhone(mobiles) || isCompany400NO(mobiles));
    }

    /**
     * 匹配中国邮政编码
     *
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isPostCode(String postCode) {
        String reg = "[1-9]\\d{5}";
        return Pattern.matches(reg, postCode);
    }

    /**
     * 转换金钱小数点0.0
     */
    public static String toMoney(float money) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(money);
    }

    public static String get4to5(double number, int decimal) {
        return String.valueOf(round_half_up(number, decimal));
    }


    public static double round_down(double number, int decimal) {
        if (parseNumber(number) <= decimal) {
            return number;
        }
        return new BigDecimal(number).setScale(decimal, BigDecimal.ROUND_DOWN).doubleValue();
    }

    public static double round_up(double number, int decimal) {
        if (parseNumber(number) <= decimal) {
            return number;
        }
        return new BigDecimal(number).setScale(decimal, BigDecimal.ROUND_UP).doubleValue();
    }

    //四舍五入
    public static double round_half_up(double number, int decimal) {
        if (parseNumber(number) <= decimal) {
            return number;
        }
        return new BigDecimal(number).setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double round_half_down(double number, int decimal) {
        if (parseNumber(number) <= decimal) {
            return number;
        }
        return new BigDecimal(number).setScale(decimal, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static int parseNumber(double number) {
        String[] aa = (number + "").split("\\.");
        return aa[1].length();
    }

    /**
     * 转换金钱小数点0.00
     */
    public static String toMoney(double money) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        return decimalFormat.format(money);
    }

    /**
     * 转换金钱小数点0.00
     */
    public static String toMoney(long money) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        return decimalFormat.format(money / 1000.00);
    }

    public static void logcat(String str) {
        if (BuildConfig.LOG_DEBUG)
            Log.e(TAG, str);
    }

    /**
     * 隐藏手机号码中间四位
     */
    public static String phone4(String mobile) {
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }


    public static int getMonth(long end, long start) {
        long result = (end / 1000) - (start / 1000);
        return (int) (result / (30 * 60 * 60 * 24L));
    }

    public static void callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 单选 下方弹出
     *
     * @param context
     * @param textView
     * @param stringItems
     */
    public static void ActionSheetDialogNoTitle(Context context,
                                                final TextView textView,
                                                final String[] stringItems) {
        final ActionSheetDialog dialog = new ActionSheetDialog(context, stringItems, null);
        dialog.isTitleShow(false).show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                textView.setText(stringItems[position]);
            }
        });
    }

    public static String toReversedHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            if (i > 0) {
                sb.append("");
            }
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
        }
        return sb.toString().toUpperCase();
    }


    /**
     * 读取url后面的文件名
     *
     * @param url
     * @return
     */
    public static String getFileName(String url) {
        String[] str = url.substring(url.lastIndexOf("/") + 1).split("_");
        return str[1];
    }

    /**
     * 读取url后面的文件名
     *
     * @param url
     * @return
     */
    public static String getFileName2(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 获取文件扩展名
     *
     * @return
     */
    public static String ext(String filename) {
        int index = filename.lastIndexOf(".");

        if (index == -1) {
            return null;
        }
        String result = filename.substring(index + 1);
        return result;
    }

    /**
     * 目标文件存储的文件夹路径
     */
    private static String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "znxjfile";

    public static File getFile(String fileName) {
        File file = null;
        File files = new File(destFileDir);
        if (!files.exists())
            files.mkdir();
        if (files.isDirectory()) {
            File[] fileList = files.listFiles();
            for (File file1 : fileList) {
                if (fileName.equals(file1.getName()))
                    file = file1;
            }
        }
        return file;
    }

    private static char[] chineseParam = new char[]{'」', '，', '。', '？', '…', '：', '～', '【',
            '＃', '、', '％', '＊', '＆', '＄', '（', '‘', '’', '“', '”', '『', '〔', '｛', '【',
            '￥', '￡', '‖', '〖', '《', '「', '》', '〗', '】', '｝', '〕', '』', '”', '）', '！',
            '；', '—', '!', '@', '#', '~', '$', '%', '^', '&', '*', '(', ')', '-', '=', '+', '[', ']',
            '{', '}', ';', ':', '"', ',', '<', '>', '/', '?', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 是否包含符号和字符   可以接受 _  .
     */
    public static boolean isFHAndZF(String s) {
        for (char c : chineseParam) {
//            Log.d(TAG, String.valueOf(c));
            if (s.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> getListString(String str) {
        ArrayList<String> list = new Gson().fromJson(str, new TypeToken<ArrayList<String>>() {
        }.getType());
        return list != null && list.size() > 0 ? list : null;
    }

    public static ArrayList<?> getListStrings(String str) {
        ArrayList<?> list = new Gson().fromJson(str, new TypeToken<ArrayList<?>>() {
        }.getType());
        return list != null && list.size() > 0 ? list : null;
    }

    /**
     * dp to px
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private static final int MIN_DELAY_TIME = 500;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime = 0;

    public static boolean isFastClick() {
        long currentClickTime = System.currentTimeMillis();
        if(lastClickTime==0){
            lastClickTime = currentClickTime;
            return false;
        }else{
            if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
                lastClickTime = currentClickTime;
                return false;
            }else{
                lastClickTime = currentClickTime;
                return true;
            }
        }
    }

    public static boolean isNoEmpty(String str) {
        if (str != null && !str.isEmpty()) {
            return true;
        } else
            return false;
    }

    public static boolean isNoEmptyImg(String str) {
        if (str != null && !str.isEmpty() && !str.equals("[]") && !str.equals("null")) {
            return true;
        } else
            return false;
    }

    public static boolean isNoEmpty(Double d) {
        if (d != null) {
            return true;
        } else
            return false;
    }

    public static boolean isNoEmpty(Boolean d) {
        if (d != null) {
            return d;
        } else
            return false;
    }

    public static boolean isNoEmpty(Float d) {
        if (d != null) {
            return true;
        } else
            return false;
    }

    public static boolean isNoEmpty(Integer integer) {
        if (integer != null) {
            return true;
        } else
            return false;
    }

    public static boolean isNoEmpty(Short s) {
        if (s != null) {
            return true;
        } else
            return false;
    }

    public static boolean isNoEmpty(Long longl) {
        if (longl != null) {
            return true;
        } else
            return false;
    }

    public static boolean isNoEmpty(TextView view) {
        if (view != null && !view.getText().toString().isEmpty()) {
            return true;
        } else
            return false;
    }

    public static boolean isNoEmpty(EditText editText) {
        if (editText != null && !editText.getText().toString().isEmpty()) {
            return true;
        } else
            return false;
    }

    public static void setTextColor(Context context, EditText v, int color) {
        v.setTextColor(ContextCompat.getColor(context, color));
    }

    public static void setTextColor(Context context, TextView v, int color) {
        v.setTextColor(ContextCompat.getColor(context, color));
    }


    public static String isNum999(int num) {
        if (num > 999) {
            HokasUtils.logcat(" isNum999 " + num);
            return "999+";
        } else {
            HokasUtils.logcat(" isNum999 1 " + num);
            return "" + num;
        }
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    // 替换、过滤特殊字符
    public static String StringFilter(String str) throws PatternSyntaxException {
        str = str.replaceAll(",", "，")
                .replaceAll("!", "！");//替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static boolean isHttpStart(String str) {
        return str.startsWith("http://") || str.startsWith("https://");
    }

    public static boolean isDecimalN(String priceString, int decimalQuantity){
            if(priceString.trim().indexOf(".") == -1){
                return false;
            }
            int dotPlace = priceString.trim().length() - priceString.trim().indexOf(".") - 1;
            if(dotPlace<decimalQuantity){
                return false;
            }else{
                return true;
            }
    }

    public static boolean isNumber(String str){
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            return false;
        }
        return true;
    }

    public static String hideIdInfo(String idCode) {
        if(isNoEmpty(idCode)){
            return  idCode.substring(0,12)+"******";
        }else{
            return "";
        }
    }
}
