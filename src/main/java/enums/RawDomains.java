package enums;

public enum RawDomains {
    AVIATION_ACCIDENT("avion"),
    EARTHQUAKE("terremoto"),
    TRAIN_ACCIDENT("tren"),
    TERRORIST_ATTACK("atentado");

    String value;

    public static RawDomains fromValue(String aValue){
        for (RawDomains t : values()){
            if(t.value().equals(aValue)){
                return t;
            }
        }
        return null;
    }

    RawDomains(String t){
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
