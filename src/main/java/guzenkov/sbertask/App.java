/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package guzenkov.sbertask;

import java.lang.reflect.*;
import java.lang.annotation.*;
import java.util.regex.Pattern;
import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;

public class App {
    public static void main(String[] args) {
        // extracted(app);
        File file = new File("src/main/resources/onefield.properties");
        try(Scanner scanner = new Scanner(file)){
            scanner.useDelimiter(Pattern.compile("\\b|\\p{javaWhitespace}+"));
            TokenReader tkReader = new TokenReader(scanner);
            tkReader.load();
            System.out.println(tkReader.getTokens());
        }catch(FileNotFoundException e){
           e.printStackTrace();
        } 
    }

    private static void extracted(App app) {
        Class<?> cl;
           try{
               cl = Class.forName("guzenkov.sbertask.SomeClass");
               app.printFields(cl);
           }catch(ClassNotFoundException e){
               e.printStackTrace();
           }
    }

    private void printFields(Class<?> cl){
        Field[] fields = cl.getDeclaredFields();
        for(Field f : fields){
            printAnnotations(f);
            Class<?> type = f.getType();
            String name = f.getName();
            String modifiers = Modifier.toString(f.getModifiers());
            if(modifiers.length() > 0){
                System.out.print(modifiers + " ");
            }
            System.out.println(type.getName() + " " + name + ";");
        }
    }

    private void printAnnotations(Field f){
        Annotation[] anns = f.getDeclaredAnnotations();
        for(Annotation a : anns){
            String name = a.annotationType().getName();
            String nameField = "";
            String typeField = "";
            if(a.annotationType() == Property.class){
                nameField = f.getAnnotation(Property.class).name();
                typeField = f.getAnnotation(Property.class).type();
            }
            System.out.println(name + ": " + nameField + ", " + typeField);
        }
    }
}