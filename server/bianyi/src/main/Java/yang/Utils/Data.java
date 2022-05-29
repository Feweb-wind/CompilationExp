package yang.Utils;

import java.util.*;
// 关键字（变）
// 单分界符（变）
// 无符号整数 标识符--->字符表
// 双分界符
// 注释头部
// 注释尾部
// 数组下标界限符
// 字符串标识符

public class Data {
    String[] SingleDelimiter;//单分界符
    String KeyWord[];//关键字
    ArrayList<String> Letter;//字符表_字母表
    ArrayList<String> Number;//字符表_数字表
    ArrayList <String>Annotation;//字符表_注释
    String[] DoubleDelimiter;//字符表_双分节符
    String[] ArrayDelimiter;//数组下标界限符
    String[] StringIdentifier;//字符串标识符
    public static Map<String,Integer> map;
    public static Set<String> set;
    public void init() {
        Letter=new ArrayList<>();
        Number=new ArrayList<>();
        Annotation=new ArrayList();
        DoubleDelimiter= null;
        ArrayDelimiter= null;
        StringIdentifier= null;
        SingleDelimiter=null;
        KeyWord=null;
    }

    void Update(String json) {//根据json数据进行更新
        init();
        CommonUtils utils = new CommonUtils();
        utils.parseJSONWithJSONObject(json);
        SingleDelimiter = utils.SingleDelimiter;
        KeyWord = utils.KeyWord;
        if (utils.Character != null) {
            for (int i = 0; i < utils.Character.length; i++) {
                for (char k = 'A'; k < 'Z'; k++) {
                    if (utils.Character[i].equals(k + "")) {
                        Letter.add(k + "");
                    }
                }
                for (char k = 'a'; k < 'z'; k++) {
                    if (utils.Character[i].equals(k + "")) {
                        Letter.add(k + "");
                    }
                }
                for (int k = 0; k <= 9; k++) {
                    if (utils.Character[i].equals(k + "")) {
                        Number.add(k + "");
                    }
                }
                if (utils.Character[i].equals(":")) {
                    DoubleDelimiter = new String[]{":"};
                }
                if (utils.Character[i].equals("{") ) {
                    Annotation.add("{");
                }
                if (utils.Character[i] .equals("}") ) {
                    Annotation.add("}");
                }
                if (utils.Character[i].equals(".") ) {
                    ArrayDelimiter = new String[]{"."};
                }
                if (utils.Character[i].equals("‘")) {
                    StringIdentifier = new String[]{"‘"};
                }
            }
        }
    }
    void prepare(){
        set=new HashSet<>();
        if(KeyWord!=null) {
            for (String s : KeyWord) {
                set.add(s);
            }
        }
        map=new HashMap<>();
        for(String s:Letter){
            map.put(s,1);
        }
        for(String s:Number){
            map.put(s,3);
        }
        if(SingleDelimiter!=null) {
            for (String s : SingleDelimiter) {
                if(s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/")||s.equals("=")||s.equals("<")||s.equals(">")||s.equals("(")||s.equals(")")||s.equals("[")||s.equals("]")||s.equals(";")) {
                    map.put(s, 4);
                }
            }
        }
        if(Annotation!=null) {
            for (String s : Annotation) {
                map.put(s, 5);
            }
        }
        if(ArrayDelimiter!=null){
            map.put(".",6);
        }

        if(DoubleDelimiter!=null){
            map.put(":",7);
        }

        if(StringIdentifier!=null){
            map.put("‘",8);
        }
        map.put(" ",9);
    }

    /**
     *
     * @param words 传入的字符串的char型数组
     * @param start 截取的开始索引
     * @param end 截取的末尾索引
     * @return 截取的字符串
     * 这个方法用来返回一个截取的字符串
     */
    public String partOfArray(char[] words,int start,int end){
        String str = new String(words);
        return str.substring(start,end + 1);
    }


    /**
     *
     * @param words 传入的字符串的char型数组
     * @param start 截取的开始索引
     * @param end 截取的末尾索引
     * @return 截取的字符串
     * 这个方法用来判断传来的串是不是关键字
     */
    public boolean isKeyWord(char[] words,int start,int end) {
        String str = partOfArray(words, start, end);
        return set.contains(str);
    }

}
