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

    public void load(Scanner scanner){
        Token curTk = getToken();
        while(curTk.getTokenType() != TokenType.EOF){
            tokens.add(curTk);
            curTk = getToken();
        }
    }

    public void setScanner(Scanner sc){
        fScanner = sc;
    }

    private Token getToken(){
        Token tk = new Token();
        String tkString;
        if(fScanner.hasNext()){
            tkString = fScanner.next();
            if(tkString == "."){
                tk.setTokenType(TokenType.DOT);
            }else if(tkString == "{"){
                tk.setTokenType(TokenType.OBJECT_START);
            }else if(tkString == "}"){
                tk.setTokenType(TokenType.OBJECT_END);
            }else if(tkString == "="){
                tk.setTokenType(TokenType.EQUAL);
            }else if(tkString == ":"){
                tk.setTokenType(TokenType.COLON);
            }else if(tkString == ","){
                tk.setTokenType(TokenType.COMMA);
            }else{
                tk.setTokenType(TokenType.UNDEFINED);
            }
            tk.setValue(tkString);
        }else{
            tk.setTokenType(TokenType.EOF);
        }
        return tk;
    }
}

class Token{
    private TokenType tkType;
    private String value;

    Token(){
        value = "";
        tkType = TokenType.UNDEFINED;
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
}