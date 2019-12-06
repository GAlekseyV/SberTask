package guzenkov.sbertask;

class SomeClass{
    private static final SomeClass INSTANCE = new SomeClass();

    @Property(name="guzenkov.sbertask.SomeClass.integerField", type="integer")
    private int integerField = 0;

    @Property(name="guzenkov.sbertask.SomeClass.doubleField", type="double")
    private double doubleField = 0.0;

    @Property(name="guzenkov.sbertask.SomeClass.stringField", type="string")
    private String stringField = "init";

    @Property(name="guzenkov.sbertask.SomeClass.userTypeField", type="object")
    private UserType userTypeField = new UserType();

    private String filenameForRefresh = "src/main/resources/someClass.properties";
    
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

    public void doRefresh(){
        Refresher refresher = new Refresher();
        refresher.run(this, filenameForRefresh);
    }

    public void setFileForRefresh(String filename){
        filenameForRefresh = filename;
    }
    
}

class UserType{
    @Property(name="guzenkov.sbertask.userTypeField.integerNumber", type="integer")
    private int integerNumber = 0;

    @Property(name="guzenkov.sbertask.userTypeField.doubleNumber", type="double")
    private double doubleNumber = 0.0;

    @Property(name="guzenkov.sbertask.userTypeField.str", type="string")
    private String str = "init";

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
