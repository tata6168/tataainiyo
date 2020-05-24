package com.activiti.tataainiyo.util;
/*
路径格式转换工具类
'/a/a'=>'[a:a]'
* */
public class UrlUtil {
    //转换为permission格式
    public static String confirmUrlType(String s){
        char[] chars = s.toCharArray();
        int len = chars.length;
        if(chars[0]=='/'&&chars[len-1]=='/'){
            chars[0]='[';
            chars[len-1]=']';
            return String.valueOf(urlTransitionSn(chars,new char[len],0));
        }else if(chars[0]=='/'||chars[len-1]=='/'){
            char[] chars1 = new char[len+1];
            if(chars[0]=='/'){
                chars[0]='[';
                return String.valueOf(urlTransitionSn(chars,chars1,0));
            }else {
                chars[len-1]=']';
                return String.valueOf(urlTransitionSn(chars,chars1,1));
            }
        }else{
            return String.valueOf(urlTransitionSn(chars,new char[len+2],0));
        }
    }
    public static char[] urlTransitionSn(char[] oldArray,char[] newArray,int start){
        /*
         * start==1 表示 newArray自[1] 开始赋值
         * i1 == 1 / i1 == 2 1和start表示单个[的位置  2表示两头没有[]
         * */
        int i1 = newArray.length - oldArray.length;
        for (int i = 0; i < newArray.length; i++) {
            if(i1==1&&start==1){
                //第一个字符不为'['
                if(i==0) {
                    newArray[0] = '[';
                }else {
                    if(oldArray[i-1]=='/'){
                        oldArray[i-1]=':';
                    }
                    newArray[i] = oldArray[i - 1];
                }
            }else if(i1==1&&start==0){
                if(oldArray[i]=='/'){
                    oldArray[i]=':';
                }
                //第一个字符为'['
                newArray[i]=oldArray[i];
                //复制完成
                if(i==newArray.length-2){
                    //将最后一个字符赋值']'
                    newArray[newArray.length-1]=']';
                    break;
                }
            }else if(i1==0&&start==0){
                if(oldArray[i]=='/'){
                    oldArray[i]=':';
                }
                newArray[i]=oldArray[i];
            } else{
                // "cc/c"  "[cc: ]"
                if(i==0) {
                    newArray[0] = '[';
                }else {
                    if(oldArray[i-1]=='/'){
                        oldArray[i-1]=':';
                    }
                    newArray[i]=oldArray[i-1];
                }
                if (i==newArray.length-2){
                    newArray[newArray.length-1]=']';
                    break;
                }
            }
        }
        return newArray;
    }
    //获取路劲前缀
    public static String urlGetPrefix(String url){
        String perfix = "";
        int start = url.indexOf("/");
        int end = url.indexOf("/",start+1);
        if(start<0 && end<0){
            // "aaa"
            perfix=url;
        }else if(start==0 && end<0){
            // "/aaa"
            perfix=url.substring(1);
        }else if(start>0 && end<0){
            // "aaa/"
            perfix=url.substring(0,start);
        }else if(start>0 && end>0 && end>start){
            // "asd/asda/asd"
            perfix=url.substring(0,start);
        }else {
            // "/aaa/"
            perfix=url.substring(1,end);
        }
        return perfix;
    }
}
