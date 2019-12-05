package guzenkov.sbertask;

import java.util.Scanner;
import java.util.LinkedList;

public class TokenReader{
    private Scanner fScanner;
    private LinkedList<Token> tokens;

    public TokenReader(Scanner sc){
        tokens = new LinkedList<Token>();
        setScanner(sc);
    }

    public void load(){
        Token curTk = getToken();
        while(curTk.getTokenType() != TokenType.EOF){
            tokens.add(curTk);
            curTk = getToken();
        }
        joinTokensSplitedByDot();
    }

    public void load(Scanner sc){
        fScanner = sc;
        load();
    }

    public void setScanner(Scanner sc){
        fScanner = sc;
    }

    public LinkedList<Token> getTokens(){
        return tokens;
    }

    private void joinTokensSplitedByDot(){
        int indexOfDot = fidnIndexOfDotToket();
        while(indexOfDot != 0){
            Token dotTk = tokens.get(indexOfDot);
            Token prevTk = tokens.get(indexOfDot - 1);
            Token nextTk = tokens.get(indexOfDot + 1);
            String joinValue = prevTk.getValue() + dotTk.getValue() + nextTk.getValue();
            prevTk.setValue(joinValue);
            tokens.remove(dotTk);
            tokens.remove(nextTk);
            indexOfDot = fidnIndexOfDotToket();
        }
    }

    private int fidnIndexOfDotToket(){
        int index = 0;
        for(Token tk : tokens){
            if(tk.getTokenType() == TokenType.DOT){
                index = tokens.indexOf(tk);
                break;
            }
        }
        return index;
    }

    private Token getToken(){
        Token tk = new Token();
        String tkString;
        if(fScanner.hasNext()){
            tkString = fScanner.next();
            tkString = tkString.trim();
            if(tkString.equals(".")){
                tk.setTokenType(TokenType.DOT);
            }else if(tkString.equals("{")){
                tk.setTokenType(TokenType.OBJECT_START);
            }else if(tkString.equals("}")){
                tk.setTokenType(TokenType.OBJECT_END);
            }else if(tkString.equals("=")){
                tk.setTokenType(TokenType.EQUAL);
            }else if(tkString.equals(":")){
                tk.setTokenType(TokenType.COLON);
            }else if(tkString.equals(",")){
                tk.setTokenType(TokenType.COMMA);
            }else if(isDigit(tkString)){
                tk.setTokenType(TokenType.DIGIT);
            }else if(isWord(tkString)){
                tk.setTokenType(TokenType.WORD);
            }else{
                tk.setTokenType(TokenType.UNDEFINED);
            }
            tk.setValue(tkString);
        }else{
            tk.setTokenType(TokenType.EOF);
        }
        return tk;
    }

    private boolean isDigit(String s){
        boolean isDigit = true;
        for(char c : s.toCharArray()){
            if(!Character.isDigit(c)){
                isDigit = false;
                break;
            }
        }
        return isDigit;
    }

    private boolean isWord(String s){
        boolean isWord = true;
        for(char c : s.toCharArray()){
            if(!Character.isLetter(c) && !Character.isDigit(c)){
                isWord = false;
                break;
            }
        }
        return isWord;
    }
}

class Token{
    private TokenType tkType;
    private String value;

    Token(){
        value = "";
        tkType = TokenType.UNDEFINED;
    }

    Token(String s, TokenType type){
        value = s;
        tkType = type;
    }

    public void setTokenType(TokenType type){
        tkType = type;
    }

    public void setValue(String value){
        this.value = value;
    }

    public TokenType getTokenType(){
        return tkType;
    }

    public String getValue(){
        return this.value;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }

        if(obj == null || getClass() != obj.getClass()){
            return false;
        }

        Token token = (Token) obj;
        return value.equals(token.getValue()) && this.tkType == token.getTokenType();
    }
}