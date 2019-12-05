package guzenkov.sbertask;

import org.junit.Test;

import jdk.jfr.Timestamp;

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
}