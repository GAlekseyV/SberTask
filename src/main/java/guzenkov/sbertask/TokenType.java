package guzenkov.sbertask;

public enum TokenType{
    WORD,   // слово
    DIGIT,  // число
    NAME,   
    VALUE,
    OBJECT_START,   // идентификатор начала объекта
    OBJECT_END,     // идентификатор конца объекта
    DOT,            // точка "."
    COMMA,          // запятая ","
    EQUAL,          // равно "="
    COLON,          // двоеточие ":"
    UNDEFINED,      // тип не определен
    EOF             // конец файла
}