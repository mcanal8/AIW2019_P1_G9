package enums;

public enum Domains {
    AVIATION_ACCIDENT("airplane"),
    EARTHQUAKE("earthquake"),
    TRAIN_ACCIDENT("train"),
    TERRORIST_ATTACK("attack");

    String value;

    public static Domains fromValue(String aValue){
        for (Domains t : values()){
            if(t.value().equals(aValue)){
                return t;
            }
        }
        return null;
    }

    Domains(String t){
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
