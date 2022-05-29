package yang.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

import javax.xml.stream.events.StartDocument;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
public class DFA {
    private static final int START = 0;//自动机的开始状态
    private static final int NUM = 1;//自动机接收数字的状态
    private static final int CHAR = 2;//自动机接收字符的状态
    private static final int SINGLE_DOUBLE = 3;//自动机接收到一个:后的状态
    private static final int DOUBLE = 4;//自动机在接收到:后接收到=后的状态
    private static final int INSIDE_ANNO = 5;//自动机接收到{后的状态
    private static final int ANNOTATION = 6;//自动机接收到}后的状态
    private static final int OTHER_ARRAY = 7;//自动机接收到.后的状态
    private static final int ARRAY = 8;//自动机接收了两个.后的状态
    private static final int GETCHAR = 9;//自动机接收到‘后的状态
    private static final int STRING = 10;//自动机接收到后的状态
    private static final int END = 11;//自动机的终止状态
    private static final int ERROR = -1;//自动机的错误状态
    private static LinkedHashMap<String,String> res ;
    public void dfa(String word,String json){
        Data data=new Data();
        data.init();
        data.Update(json);
        data.prepare();
        res = new LinkedHashMap();
        String preparedWord = word.replaceAll("\\r\\n|\\r|\\n", " ");
        System.out.println(preparedWord);
        char[] words = preparedWord.toCharArray();
        int statu = 0;//状态变量
        int start = 0;//用于截取char数组的头指针
        int pos = 0;//char数组的移动指针
        int errorFlag = 0;//错误信号
        while(pos < words.length && errorFlag != 1&&data.map!=null){
            switch (statu){
                case START :
                    if(!data.map.containsKey(words[pos]+"")){
                        statu = ERROR;//若一开始就接收到其他字符直接报错
                        errorFlag = 1;
                        System.out.println("错误！！");
                    }else if(data.map.get(words[pos]+"") == 3){
                        statu = NUM;//若接收到一个数字，则将状态转为NUM

                    }else if(data.map.get(words[pos]+"") == 1 || data.map.get(words[pos]+"") == 2){
                        statu = CHAR;//若接收到一个字母，则将状态转为CHfAR

                    }else if(data.map.get(words[pos]+"") == 4){
                        statu = START;//若接收到一个字母，则将状态转为CHAR
                        res.put(data.partOfArray(words,start,pos),"单分界符");//由于已经到了尽头，将该字符push到res中
                        pos++;
                        start = pos;//改变start指针，使其指向下一个字符
                    }else if(data.map.get(words[pos]+"") == 7){
                        statu = SINGLE_DOUBLE;//若接收到一个: 则将状态转为SINGLE_DOUBLE

                    }else if(data.map.get(words[pos]+"") == 5){
                        statu = INSIDE_ANNO;//若接收到一个{ 则将状态转为INSIDE_ANNO

                    }else if(data.map.get(words[pos]+"") == 6){
                        statu = OTHER_ARRAY;//若接受一个. 则将状态转为OTHER_ARRAY

                    }else if(data.map.get(words[pos]+"") == 8){
                        statu = GETCHAR;//若接受一个‘ 将状态转换为GETCHAR

                    }else if(data.map.get(words[pos]+"")==9){
                        statu= START;
                        pos++;
                        start++;
                    }
                    else if(!data.map.containsKey(words[pos]+"")){
                        statu = ERROR;//若一开始就接收到其他字符直接报错
                        errorFlag = 1;
                        System.out.println("错误！！");
                    }
                    break;
                case NUM :
                    if(data.map.get(words[pos]+"") == 3){//若下一个字符也是数字则继续呆在NUM状态
                        statu = NUM;
                        pos++;
                        if(pos >= words.length){//若pos指到尽头，将start至pos全部加入res中，且置状态为END
                            statu = END;
                            pos--;
                            res.put(data.partOfArray(words,start,pos),"无符号整数");//左闭右开正好
                        }
                    }else{
                        //此时pos++过了就不用++了
                        statu = START;//如果不是数字了则返回到START状态
                        res.put(data.partOfArray(words,start,pos - 1),"无符号整数");
                        start = pos;
                    }
                    break;
                case CHAR :
                    //由于该状态可以接收大写字母，小写字母，数字，用三个条件或起来判断
                    if(data.map.get(words[pos]+"") == 3 ||data.map.get(words[pos]+"") == 1 ||data.map.get(words[pos]+"") == 2){
                        statu = CHAR;//继续呆在CHAR状态
                        pos++;
                        if(pos >= words.length){
                            pos--;
                            statu = END;//读头指向终点，置状态为END
                            if(data.isKeyWord(words,start,pos)){//这里判断是否为关键字若是则将其设为关键字，不是则为标识符
                                res.put(data.partOfArray(words,start,pos),"关键字");
                            }else{
                                res.put(data.partOfArray(words,start,pos),"标识符");
                            }
                        }
                    }else{
                        statu = START;//还没读完，转到START状态
                        if(data.isKeyWord(words,start,pos - 1)){//这里判断是否为关键字若是则将其设为关键字，不是则为标识符
                            res.put(data.partOfArray(words,start,pos - 1),"关键字");
                        }else{
                            res.put(data.partOfArray(words,start,pos - 1),"标识符");
                        }
                        start = pos;
                    }
                    break;
                case SINGLE_DOUBLE :
                    if(data.map.get(words[pos]+"") == 7){//若接收到一个:字符
                        pos++;
                        if(pos < words.length && words[pos]!='='){//若不为= 则是一个单分界符
                            res.put(data.partOfArray(words,start,pos - 1),"单分界符");//pos++需要-1获得单分节符号
                            statu = START;
                            start = pos;
                        }else if(pos == words.length){
                            res.put(data.partOfArray(words,start,pos-1),"单分界符");
                            statu = END;
                        }else{
                            //若为= 转向DOUBLE状态
                            statu = DOUBLE;
                        }

                    }
                    break;
                case DOUBLE :
                    //直接将:=加入res中
                    res.put(data.partOfArray(words,start,pos),"双分界符");
                    pos++;
                    statu = START;//转向START状态
                    start = pos;
                    break;
                case INSIDE_ANNO :
                    while(pos<words.length && words[pos]!='}'){//若不为}，则一直加，知道找到}的下标
                        pos++;
                    }
                    if(pos < words.length && words[pos] == '}'){
                        statu = ANNOTATION;//找到的时候转到ANNOTATIOn状态
                    }
                    break;

                case ANNOTATION :
                    if(pos == words.length - 1 && words[pos] == '}'){//若已经到终点，就不需要重新制定start，直接置状态为END
                        res.put(data.partOfArray(words,start,pos),"注释");
                        statu = END;
                    }else if(pos < words.length - 1 && words[pos] == '}'){
                        res.put(data.partOfArray(words,start,pos),"注释");//若没有到终点，就不需要重新制定start，直接置状态为END
                        pos++;
                        start = pos;
                        statu = START;
                    }
                    break;
                case OTHER_ARRAY :
                    System.out.println(words[pos]);
                    pos++;
                    if(pos >= words.length){//若第二个接收到的不为.，则报错
                        res.put(".","程序结束标志");
                        return;
                    }else if(words[pos] != '.'){
                        res.put(".","程序结束标志");
                        return;
                    } else{//若为. 则转入ARRAY状态
                        statu = ARRAY;
                    }
                    break;
                case ARRAY :
                    //直接加入res
                    res.put(data.partOfArray(words,start,pos),"数组下表界限符");
                    pos++;
                    start = pos;
                    statu = START;
                    break;
                case GETCHAR :
                    while(true){
                        //若不为’，则一直加，知道找到’的下标
                        if(pos < words.length && words[pos] == '’'){
                            break;
                        }else{
                            pos++;
                        }
                    }
                    if(words[pos] =='’'){
                        statu = STRING;//找到下标时将状态转为STRING
                    }
                    break;
                case STRING :
                    if(pos == words.length - 1){
                        res.put(data.partOfArray(words,start,pos),"字符串标识符");//不需要改变start直接加入
                        statu = END;
                    }else{
                        res.put(data.partOfArray(words,start,pos),"字符串标识符");//改变start
                        pos++;
                        start = pos;
                        statu = START;
                    }
                    break;
                case END :
                    return;//终止状态
                default :
                    errorFlag = 1;
                    return;//错误状态，一开始加入其他符号
            }
        }
    }
    public String Result(){
        JSONObject object=new JSONObject();
        Gson gson=new GsonBuilder().enableComplexMapKeySerialization().create();
        String jsonStr=gson.toJson(res);
        return jsonStr;
    }
}
