package guzenkov.sbertask;

import java.lang.reflect.*;
import java.lang.annotation.*;
import java.util.regex.Pattern;

import java.io.*;
import java.util.Scanner;
import java.util.HashMap;
import java.util.logging.*;

public class Refresher{
    public static final Logger logger = Logger.getLogger("guzenkov.sbertask.App");

    private HashMap<String, String> fieldsInFile = new HashMap<>();
    private HashMap<String, HashMap<String, String>> objectsInFile = new HashMap<>();

    Refresher(){

    }

    public void run(Object object, String fileName){
        File file = new File(fileName);
        try(Scanner scanner = new Scanner(file)){
            scanner.useDelimiter(Pattern.compile("\\b|\\p{javaWhitespace}+"));
            getFields(scanner);
        }catch(FileNotFoundException e){
            logger.log(Level.WARNING, "File \"" + fileName + "\" is not found. Set values by defaul.");
        }

        try{
            updateFields(object);
        }catch (IllegalAccessException e) {
            logger.log(Level.SEVERE, "Can't refresh fields of \"" + this.getClass().getCanonicalName() + "\" class.");
            logger.log(Level.FINE, "Can't update field.", e);
        }catch(NumberFormatException e){
            logger.log(Level.SEVERE, "Can't refresh fields of \"" + this.getClass().getCanonicalName() + "\" class.");
            logger.log(Level.FINE, "Can't update field.", e);
        }catch(IllegalArgumentException e){
            logger.log(Level.SEVERE, "Can't refresh fields of \"" + this.getClass().getCanonicalName() + "\" class.");
            logger.log(Level.FINE, "Can't update field.", e);
        }
        
    }

    private void getFields(Scanner sc){
        PropertiesParcer parser = new PropertiesParcer();
        parser.parse(sc);
        fieldsInFile = parser.getFieldsOfBaseType();
        objectsInFile = parser.getFieldsOfObjects();
    }

    private void updateFields(Object object) throws IllegalArgumentException, IllegalAccessException {
        Class<?> cl = object.getClass();
        Field[] fields = cl.getDeclaredFields();
        for(Field f : fields){
            // В данном задании подразумеваю, что есть только одна аннотация
            String nameFieldInFile = "";
            String defaulValue = "";

            Annotation[] anns = f.getDeclaredAnnotations();
            if(anns.length > 0){
                for(Annotation a : anns){
                    if(a.annotationType() == Property.class){
                        nameFieldInFile = f.getAnnotation(Property.class).name();
                        defaulValue = f.getAnnotation(Property.class).defaultValue();
                    }
                }
                
                if(fieldsInFile.containsKey(nameFieldInFile)
                    || objectsInFile.containsKey(nameFieldInFile)){ // Поле есть в файле
                    if(isIntegerField(f)){
                        f.setAccessible(true);
                        f.set(object, (int)Integer.parseInt(fieldsInFile.get(nameFieldInFile)));
                    }else if(isDoubleField(f)){
                        f.setAccessible(true);
                        f.set(object, (double)Double.parseDouble(fieldsInFile.get(nameFieldInFile)));
                    }else if(isStringField(f)){
                        f.setAccessible(true);
                        f.set(object, fieldsInFile.get(nameFieldInFile));
                    }else if(isObjectField(f)){
                        f.setAccessible(true);
                        Object obj = f.get(object);
                        updateObjectFields(obj, nameFieldInFile);
                    }
                }else{//Установить значения по умолчанию
                    if(isIntegerField(f)){
                        f.setAccessible(true);
                        f.set(object, (int)Integer.parseInt(defaulValue));
                    }else if(isDoubleField(f)){
                        f.setAccessible(true);
                        f.set(object, (double)Double.parseDouble(defaulValue));
                    }else if(isStringField(f)){
                        f.setAccessible(true);
                        f.set(object, defaulValue);
                    }
                }
            }
        }
    }

    private void updateObjectFields(Object object, String fieldName)
            throws NumberFormatException, IllegalArgumentException, IllegalAccessException {
        Class<?> cl = object.getClass();

        if(objectsInFile.containsKey(fieldName)){
            Field[] fields = cl.getDeclaredFields();
            for(Field f : fields){
                String nameField = f.getName();
                if(objectsInFile.get(fieldName).containsKey(nameField)){ // Поле есть в файле
                    String value = objectsInFile.get(fieldName).get(nameField);
                    if(isIntegerField(f)){
                        f.setAccessible(true);
                        f.set(object, (int)Integer.parseInt(value));
                    }else if(isDoubleField(f)){
                        f.setAccessible(true);
                        f.set(object, (double)Double.parseDouble(value));
                    }else if(isStringField(f)){
                        f.setAccessible(true);
                        f.set(object, value);
                    }
                }                  
            }
        }
    }

    private boolean isIntegerField(Field f){
        String typeName = f.getType().getName();
        return typeName.equals("int") || typeName.equals("long");
    }

    private boolean isDoubleField(Field f){
        String typeName = f.getType().getName();
        return typeName.equals("float") || typeName.equals("double");
    }

    private boolean isStringField(Field f){
        String typeName = f.getType().getName();
        return typeName.equals("java.lang.String");
    }

    private boolean isObjectField(Field f){
        return !isIntegerField(f) && !isDoubleField(f) && !isStringField(f);
    }
}