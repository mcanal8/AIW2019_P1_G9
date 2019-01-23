package enums;

public enum WekaAttributesTypes {
    NUMERIC("numeric"),
    NOMINAL("nominal"),
    STRING("string"),
    DATE("date"),
    RELATIONAL("relational");

    String value;

    public static WekaAttributesTypes fromValue(String aValue){
        for (WekaAttributesTypes t : values()){
            if(t.value().equals(aValue)){
                return t;
            }
        }
        return null;
    }

    WekaAttributesTypes(String t){
        this.value = t;
    }

    public String value(){
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
