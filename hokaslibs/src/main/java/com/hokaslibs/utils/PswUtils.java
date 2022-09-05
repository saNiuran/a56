package com.hokaslibs.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PswUtils {
    private static boolean isFourDigtialDec(String str){
        if (str == null || str ==""){
            return true ;
        }

        //String regEx="(?<=\\d|^)(?:(?:9(?=8|\\d))?(?:8(?=7|\\d))?(?:7(?=6|\\d))?(?:6(?=5|\\d))?(?:5(?=4|\\d))?(?:4(?=3|\\d))?(?:3(?=2|\\d))?(?:2(?=1|\\d))?(?:1(?=0|\\d))?(?:0(?=\\d|$))?)(?=\\d|$)";
        String regEx="^\\d(?:(?<=1)0|(?<=2)1|(?<=3)2|(?<=4)3|(?<=5)4|(?<=6)5|(?<=7)6|(?<=8)7|(?<=9)8){3}$";

        Pattern   p   =   Pattern.compile(regEx);
        Matcher m   =   p.matcher(str);
        if (m.find()){

            return true ;
        }
        return false  ;
    }
    private static boolean isFourDigtialAdd(String str){
        if (str == null || str ==""){
            return true ;
        }

        String regEx="^\\d(?:(?<=0)1|(?<=1)2|(?<=2)3|(?<=3)4|(?<=4)5|(?<=5)6|(?<=6)7|(?<=7)8|(?<=8)9){3}$";
        Pattern   p   =   Pattern.compile(regEx);
        Matcher   m   =   p.matcher(str);
        if (m.find()){

            return true ;
        }
        return false  ;
    }
    private static boolean isAllDigtial(String str){
        if (str == null || str ==""){
            return true ;
        }

        String regEx="^[0-9]{6,18}$";
        Pattern   p   =   Pattern.compile(regEx);
        Matcher   m   =   p.matcher(str);
        if (m.find()){

            return true ;
        }
        return false  ;
    }
    private static boolean isAllLetter(String str){
        if (str == null || str ==""){
            return true ;
        }

        String regEx="^[a-zA-Z]{6,18}$";
        Pattern   p   =   Pattern.compile(regEx);
        Matcher   m   =   p.matcher(str);
        if (m.find()){

            return true ;
        }
        return false  ;
    }
    private static boolean isFourDigtial(String str){
        if (str == null || str ==""){
            return true ;
        }

        String regEx="^[0-9]{4,4}$";
        Pattern   p   =   Pattern.compile(regEx);
        Matcher   m   =   p.matcher(str);
        if (m.find()){

            return true ;
        }
        return false  ;
    }
    private static boolean isFourLetter(String str){
        if (str == null || str ==""){
            return true ;
        }

        String regEx="^[a-zA-Z]{4,4}$";
        Pattern   p   =   Pattern.compile(regEx);
        Matcher   m   =   p.matcher(str);
        if (m.find()){

            return true ;
        }
        return false  ;
    }
    public static boolean isDGCheckUKEYPinPwd(String str){
        if (str == null || str ==""){
            return true ;
        }
        if(isAllDigtial(str)){
            return true;
        }
        if(isAllLetter(str)){
            return true;
        }
        //System.out.println(str);
        List<String> arr2 =new ArrayList<String>();
        int i=0;
        int len=str.length();
        //System.out.println(len);
        while(i<len-3){
            arr2.add(str.substring(i, i+4));
            //System.err.println(arr2.toString());
            i++;
        }
        int j=0;
        while(j<arr2.size()){
            if(isFourDigtial(arr2.get(j))){
                if((arr2.get(j).charAt(0)==arr2.get(j).charAt(1))&&(arr2.get(j).charAt(1)==arr2.get(j).charAt(2))&&(arr2.get(j).charAt(2)==arr2.get(j).charAt(3))){
                    return false;
                }
                if(isFourDigtialAdd(arr2.get(j))){
                    return false;
                }
                if(isFourDigtialDec(arr2.get(j))){
                    return false;
                }
            }
            if(isFourLetter(arr2.get(j))){
                if((arr2.get(j).charAt(0)==arr2.get(j).charAt(1))&&(arr2.get(j).charAt(1)==arr2.get(j).charAt(2))&&(arr2.get(j).charAt(2)==arr2.get(j).charAt(3))){
                    return false;
                }
                char[] arrPwd = new char[arr2.get(j).length()];

                for (int k = 3; k >= 0; k--) {
                    arrPwd[k]=arr2.get(j).charAt(k);
                }
                //System.out.println(arrPwd);
                //递增或者递减
                if(((arrPwd[3]-arrPwd[2]==1)&&(arrPwd[2]-arrPwd[1]==1)&&(arrPwd[1]-arrPwd[0]==1))||((arrPwd[3]-arrPwd[2]==-1)&&(arrPwd[2]-arrPwd[1]==-1)&&(arrPwd[1]-arrPwd[0]==-1))){
                    return false;
                }
            }
            j++;
        }
        return true;
    }

    public static void main(String[] args) {
        System.err.println(isDGCheckUKEYPinPwd("ABCE1236ABCE"));
        //System.err.println(isFourDigtialAdd("12345"));
        //System.err.println(isFourDigtialDec("5432"));
    }
}
