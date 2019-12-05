package guzenkov.sbertask;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class PropertiesParcer{
    private HashMap<String, String> fieldsOfBaseType;
    private HashMap<String, HashMap<String, String>> fieldOfObjects;
    private LinkedList<Token> tokens;

    public PropertiesParcer(){
        fieldOfObjects = new HashMap<String, HashMap<String, String>>();
        fieldsOfBaseType = new HashMap<String, String>();
    }

    public HashMap<String, String> getFieldsOfBaseType(){
        return fieldsOfBaseType;
    }

    public HashMap<String, HashMap<String, String>> getFieldsOfObjects(){
        return fieldOfObjects;
    }

    public void parse(Scanner sc){
        tokens = getListOfTokens(sc);
        while(tokens.size() > 0){
            if(hasNextField()){
                parseField();
            }else if(hasNextObject()){
                parseObject();
            }
        }
    }

    private boolean hasNextField(){
        boolean isNextField = false;
        if(tokens.size() > 2){
            if(tokens.get(0).getTokenType() == TokenType.WORD
              && tokens.get(1).getTokenType() == TokenType.EQUAL
              && (tokens.get(2).getTokenType() == TokenType.WORD 
                || tokens.get(2).getTokenType() == TokenType.DIGIT)){
                isNextField = true;
            }
        }
        return isNextField;
    }

    private boolean hasNextObject(){
        boolean isNextObject = false;
        if(tokens.size() > 2){
            if(tokens.get(0).getTokenType() == TokenType.WORD
            && tokens.get(1).getTokenType() == TokenType.EQUAL
            && tokens.get(2).getTokenType() == TokenType.OBJECT_START){
                isNextObject = true;
            }
        }
        return isNextObject;
    }

    private void parseField(){
        Token name = tokens.get(0);
        Token delimeter = tokens.get(1);
        Token value = tokens.get(2);
        fieldsOfBaseType.put(name.getValue(), value.getValue());
        tokens.remove(name);
        tokens.remove(delimeter);
        tokens.remove(value);
    }

    private void parseObject(){
        Token name = tokens.get(0);
        Token delimeter = tokens.get(1);
        Token startObject = tokens.get(2);
        HashMap<String, String> fields = new HashMap<String, String>();
        while(tokens.get(3).getTokenType() != TokenType.OBJECT_END){
            parseObjectField(fields);
        }
        fieldOfObjects.put(name.getValue(), fields);
        tokens.remove(name);
        tokens.remove(delimeter);
        tokens.remove(startObject);
        tokens.remove(0);
    }

    private void parseObjectField(HashMap<String, String> fields){
        Token fieldName = tokens.get(3);
        Token column = tokens.get(4);
        Token fieldvalue = tokens.get(5);
        fields.put(fieldName.getValue(), fieldvalue.getValue());
        tokens.remove(fieldName);
        tokens.remove(column);
        tokens.remove(fieldvalue);
        if(tokens.get(3).getTokenType() == TokenType.COMMA){
            tokens.remove(3);
        }
    }

    private LinkedList<Token> getListOfTokens(Scanner sc){
        TokenReader tkReader = new TokenReader(sc);
        tkReader.load();
        return tkReader.getTokens();
    }
}
