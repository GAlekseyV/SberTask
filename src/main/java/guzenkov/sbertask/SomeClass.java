package guzenkov.sbertask;

import java.lang.reflect.*;
import java.lang.annotation.*;
import java.util.HashMap;
import java.util.Scanner;

class SomeClass{
    private static final SomeClass INSTANCE = new SomeClass();

    @Property(name="guzenkov.sbertask.SomeClass.integerField", type="integer")
    private int integerField = 0;

    @Property(name="guzenkov.sbertask.SomeClass.doubleField", type="double")
    private double doubleField = 0.0;

    @Property(name="guzenkov.sbertask.SomeClass.stringField", type="string")
    private String stringField = "";

    @Property(name="guzenkov.sbertask.SomeClass.userTypeField", type="object")
    private UserType userTypeField = new UserType();

    private HashMap<String, String> nameToTypeOfBaseFields = new HashMap<String, String>();

    private SomeClass(){
       
    }

    public static SomeClass getInstance(){
        return INSTANCE;
    }

    @Override
    public String toString(){
        return "Integer field: " + this.integerField + ", " + "Double field: " + this.doubleField + ", " +
                "string field: " + this.stringField + ". " + userTypeField.toString();
    }

    public void doRefresh(Scanner sc){
        HashMap<String, String> fieldsOfBaseType = getFieldsOfBaseType(sc);

        Class<?> cl;
        try{
            cl = Class.forName("guzenkov.sbertask.SomeClass");
            updateAllFields(cl, fieldsOfBaseType);
        }catch(ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    private HashMap<String, String> getFieldsOfBaseType(Scanner sc){
        PropertiesParcer parser = new PropertiesParcer();
        parser.parse(sc);
        return parser.getFieldsOfBaseType();
    }

    private void updateAllFields(Class cl, HashMap<String, String> fieldsInFile)
            throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = cl.getDeclaredFields();
        for(Field f : fields){
            // В данном задании подразумеваю, что есть только одна аннотация
            String nameFieldInFile = "";
            String typeField = "";

            Annotation[] anns = f.getDeclaredAnnotations();
            if(anns.length > 0){
                for(Annotation a : anns){
                    if(a.annotationType() == Property.class){
                        nameFieldInFile = f.getAnnotation(Property.class).name();
                        typeField = f.getAnnotation(Property.class).type();
                    }
                }
                
                if(fieldsInFile.containsKey(nameFieldInFile)){
                    if(typeField.equals("integer")){
                        f.setAccessible(true);
                        f.set(this, (int)Integer.parseInt(fieldsInFile.get(nameFieldInFile)));
                    }else if(typeField.equals("double")){
                        f.setAccessible(true);
                        f.set(this, (double)Double.parseDouble(fieldsInFile.get(nameFieldInFile)));
                    }else if(typeField.equals("string")){
                        f.setAccessible(true);
                        f.set(this, fieldsInFile.get(nameFieldInFile));
                    }else if(typeField.equals("object")){
    
                    }
                    //TODO Логировать ошибку не распознанный тип.
                }else{
                    if(typeField.equals("integer")){
                        f.setAccessible(true);
                        f.set(this, 0);
                    }else if(typeField.equals("double")){
                        f.setAccessible(true);
                        f.set(this, 0.0);
                    }else if(typeField.equals("string")){
                        f.setAccessible(true);
                        f.set(this, "default");
                    }else if(typeField.equals("object")){
    
                    }
                }
                
            }
        }
    }
}

class UserType{
    private int integerNumber = 0;
    private double doubleNumber = 0.0;
    private String str = "";

    public void setIntegerNumber(int number){
        integerNumber = number;
    }

    public void setDoubleNumber(double number){
        doubleNumber = number;
    }

    public void setString(String someStr){
        str = someStr;
    }

    public int getIntegerNumber(){
        return integerNumber;
    }

    public double getDoubleNumber(){
        return doubleNumber;
    }

    public String getString(){
        return str;
    }

    @Override
    public String toString(){
        return "UserType. " + "IntegerNumber: " + this.integerNumber + ", " + 
                "doubleNumber: " + this.doubleNumber + ", " + "String: " + this.str + ".";
    }
}
