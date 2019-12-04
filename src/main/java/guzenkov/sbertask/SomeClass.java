package guzenkov.sbertask;

class SomeClass{
    private static final SomeClass INSTANCE = new SomeClass();

    public static SomeClass getInstance(){
        return INSTANCE;
    }

    @Property(name="integerField", type="integer")
    private int integerField;

    @Property(name="doubleField", type="double")
    private double doubleField;

    @Property(name="stringField", type="string")
    private String stringField;

    @Property(name="userTypeField", type="object")
    private UserType userTypeField;

    public void doRefresh(){

    }
}

class UserType{
    private int integerNumber;
    private double doubleNumber;
    private String str;

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
}
