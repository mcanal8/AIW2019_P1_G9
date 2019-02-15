package enums;

public enum Languages {
    ENGLISH("english"),
    SPANISH("spanish");

    String value;

    public static Languages fromValue(String aValue){
        for (Languages t : values()){
            if(t.value().equals(aValue)){
                return t;
            }
        }
        return null;
    }

    Languages(String t){
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
