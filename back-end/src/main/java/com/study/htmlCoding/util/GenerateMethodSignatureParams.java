package com.study.htmlCoding.util;

public class GenerateMethodSignatureParams {

    /**
     * 该方法被用来生成整备格式化参数
     *
     * @param s 输入的原版参数
     * @return 返回格式化后的参数
     */
    public static String generateMethodSignatureParams(String s){
        String[] strings = s.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            String[] strings1 = strings[i].trim().split(" ");
            stringBuilder.append(strings1[1]);
            stringBuilder.append(",");
            if(strings1[0].contains("<")){
                int index1 = strings1[0].indexOf("<");
                int index2 = strings1[0].indexOf(">");
                stringBuilder.append(strings1[0], index1 + 1, index2);
                stringBuilder.append(",");
                stringBuilder.append(strings1[0].substring(0, index1).toLowerCase());
            } else if(strings1[0].contains("[]")){
                int index1 = strings1[0].indexOf("[");
                stringBuilder.append(strings1[0], 0, index1);
                stringBuilder.append(",");
                stringBuilder.append("array");
            } else {
                stringBuilder.append(strings1[0]);
                stringBuilder.append(",");
                stringBuilder.append("one");
            }
            if(i < strings.length - 1){
                stringBuilder.append(";");
            }
        }
        return stringBuilder.toString();
    }
}
