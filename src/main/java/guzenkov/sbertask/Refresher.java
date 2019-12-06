package guzenkov.sbertask;

import java.lang.reflect.*;
import java.lang.annotation.*;
import java.util.regex.Pattern;
import java.io.*;
import java.util.Scanner;
import java.util.HashMap;

public class Refresher{
    private HashMap<String, String> fieldsInFile = new HashMap<>();
    private HashMap<String, HashMap<String, String>> objectsInFile = new HashMap<>();

    Refresher(){

    }

    public void run(Object object, String fileName){
        File file = new File(fileName);
        try(Scanner scanner = new Scanner(file)){
            scanner.useDelimiter(Pattern.compile("\\b|\\p{javaWhitespace}+"));
            getFields(scanner);
            updateFields(object);
        }catch(FileNotFoundException e){
           e.printStackTrace();
        }catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(IllegalArgumentException e){
            e.printStackTrace();
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
                        f.set(object, (int)Integer.parseInt(fieldsInFile.get(nameFieldInFile)));
                    }else if(typeField.equals("double")){
                        f.setAccessible(true);
                        f.set(object, (double)Double.parseDouble(fieldsInFile.get(nameFieldInFile)));
                    }else if(typeField.equals("string")){
                        f.setAccessible(true);
                        f.set(object, fieldsInFile.get(nameFieldInFile));
                    }
                    //TODO Логировать ошибку не распознанный тип.
                }else{//Установить значения по умолчанию
                    if(typeField.equals("integer")){
                        f.setAccessible(true);
                        f.set(object, 0);
                    }else if(typeField.equals("double")){
                        f.setAccessible(true);
                        f.set(object, 0.0);
                    }else if(typeField.equals("string")){
                        f.setAccessible(true);
                        f.set(object, "default");
                    }
                }
                
            }
        }
    }

    private void updateObjects(Class<?> cl) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        Field[] fields = cl.getDeclaredFields();
        for(Field f : fields){
            // В данном задании подразумеваю, что есть только одна аннотация
            String objectName = "";
            String typeField = "";

            Annotation[] anns = f.getDeclaredAnnotations();
            if(anns.length > 0){
                for(Annotation a : anns){
                    if(a.annotationType() == Property.class){
                        objectName = f.getAnnotation(Property.class).name();
                        typeField = f.getAnnotation(Property.class).type();
                    }
                }
                
                if(typeField.equals("object")){
                    if(objectsInFile.containsKey(objectName)){
                        Class<?> c = Class.forName(objectName);
                        Object object = c.getDeclaredConstructor().newInstance();
                        Field[] objfields = c.getDeclaredFields();
                    }else{//Установить значения по умолчанию
                    }
                } //TODO Логировать ошибку не верный тип.
            }
        }
    }
}