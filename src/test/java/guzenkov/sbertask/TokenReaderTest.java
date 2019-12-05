package guzenkov.sbertask;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Scanner;
import java.io.*;
import java.util.regex.Pattern;

public class TokenReaderTest {

    @Test
    public void tokensWithSameFielsdIsEquals(){
        Token first = new Token("word", TokenType.WORD);
        Token second = new Token("word", TokenType.WORD);

        assertTrue(first.equals(second));
    }

    @Test
    public void tokensWithDiferentTypeIsNotEqual(){
        Token first = new Token("word", TokenType.WORD);
        Token second = new Token("word", TokenType.DIGIT);

        assertFalse(first.equals(second));
    }

    @Test
    public void tokensWithDiferentValueIsNotEqual(){
        Token first = new Token("word", TokenType.WORD);
        Token second = new Token("digit", TokenType.WORD);

        assertFalse(first.equals(second));
    }


    @Test 
    public void onlyOneFieldInFile(){
        final LinkedList<Token> expectedTokens = new LinkedList<Token>();
        expectedTokens.add(new Token("guzenkov.sbertask.SomeClass.doubleField", TokenType.WORD));
        expectedTokens.add(new Token("=", TokenType.EQUAL));
        expectedTokens.add(new Token("10.1", TokenType.DIGIT));

        LinkedList<Token> actualTokens;
        final File file = new File("src/test/resources/onefield.properties");
        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter(Pattern.compile("\\b|\\p{javaWhitespace}+"));
            final TokenReader tkReader = new TokenReader(scanner);
            tkReader.load();
            actualTokens = tkReader.getTokens();

            assertTrue(expectedTokens.equals(actualTokens));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test 
    public void twoFieldInFile(){
        final LinkedList<Token> expectedTokens = new LinkedList<Token>();
        expectedTokens.add(new Token("guzenkov.sbertask.SomeClass.integerField", TokenType.WORD));
        expectedTokens.add(new Token("=", TokenType.EQUAL));
        expectedTokens.add(new Token("10", TokenType.DIGIT));
        expectedTokens.add(new Token("guzenkov.sbertask.SomeClass.doubleField", TokenType.WORD));
        expectedTokens.add(new Token("=", TokenType.EQUAL));
        expectedTokens.add(new Token("10.1", TokenType.DIGIT));

        LinkedList<Token> actualTokens;
        final File file = new File("src/test/resources/twofield.properties");
        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter(Pattern.compile("\\b|\\p{javaWhitespace}+"));
            final TokenReader tkReader = new TokenReader(scanner);
            tkReader.load();
            actualTokens = tkReader.getTokens();

            assertTrue(expectedTokens.equals(actualTokens));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void objectInFile(){
        final LinkedList<Token> expectedTokens = new LinkedList<Token>();
        expectedTokens.add(new Token("guzenkov.sbertask.SomeClass.userTypeField", TokenType.WORD));
        expectedTokens.add(new Token("=", TokenType.EQUAL));
        expectedTokens.add(new Token("{", TokenType.OBJECT_START));
        expectedTokens.add(new Token("integerNumber", TokenType.WORD));
        expectedTokens.add(new Token(":", TokenType.COLON));
        expectedTokens.add(new Token("99", TokenType.DIGIT));
        expectedTokens.add(new Token(",", TokenType.COMMA));
        expectedTokens.add(new Token("doubleNumber", TokenType.WORD));
        expectedTokens.add(new Token(":", TokenType.COLON));
        expectedTokens.add(new Token("0.1", TokenType.DIGIT));
        expectedTokens.add(new Token(",", TokenType.COMMA));
        expectedTokens.add(new Token("str", TokenType.WORD));
        expectedTokens.add(new Token(":", TokenType.COLON));
        expectedTokens.add(new Token("userType", TokenType.WORD));
        expectedTokens.add(new Token("}", TokenType.OBJECT_END));

        LinkedList<Token> actualTokens;
        final File file = new File("src/test/resources/oneobject.properties");
        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter(Pattern.compile("\\b|\\p{javaWhitespace}+"));
            final TokenReader tkReader = new TokenReader(scanner);
            tkReader.load();
            actualTokens = tkReader.getTokens();

            assertTrue(expectedTokens.equals(actualTokens));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}