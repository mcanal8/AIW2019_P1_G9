package enums;

public enum WekaAnnotations {
    RELATION("@relation"),
    ATTRIBUTE("@attribute"),
    DATA("@data");

    String value;

    public static WekaAnnotations fromValue(String aValue){
        for (WekaAnnotations t : values()){
            if(t.value().equals(aValue)){
                return t;
            }
        }
        return null;
    }

    WekaAnnotations(String t){
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
