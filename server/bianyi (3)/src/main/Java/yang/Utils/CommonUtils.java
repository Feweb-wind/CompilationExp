package yang.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 单分界符（增加/减少/修改）Single DelimiterTable（运算符+括号+标点符号）
//关键字表（增加/减少/修改） Keyword table
//字符表（增加/减少/修改） Character table
public class CommonUtils {
    String CharacterTable;//字符表
    String KeywordTable;//关键字表
    String SingleDelimiterTable;//字符表_单分界符表
    String[] SingleDelimiter;
    String[] KeyWord;
    String[] Character;
    public void parseJSONWithJSONObject(String json) {//解析JSON数据
        try{

            JSONArray array = new JSONArray(json);
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                if(object.has("SingleDelimiter")){
                    SingleDelimiterTable=object.getString("SingleDelimiter");
                    SingleDelimiter=SingleDelimiterTable.split(",");
                }
                if(object.has("KeyWord")){
                    KeywordTable=object.getString("KeyWord");
                    KeyWord=KeywordTable.split(",");
                }
                if(object.has("Character")){
                    CharacterTable=object.getString("Character");
                    Character=CharacterTable.split(",");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
