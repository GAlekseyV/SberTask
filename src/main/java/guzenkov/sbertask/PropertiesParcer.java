package guzenkov.sbertask;

import java.util.HashMap;
import java.util.LinkedList;

public class PropertiesParcer{
    private HashMap<String, String> fieldsOfBaseType;
    private HashMap<String, LinkedList<HashMap<String, String>>> fieldOfObjects;

    public HashMap<String, String> getFieldsOfBaseType(){
        return fieldsOfBaseType;
    }

    public HashMap<String, LinkedList<HashMap<String, String>>> getFieldsOfObjects(){
        return fieldOfObjects;
    }

    public void parse(String filename){

    }
}
