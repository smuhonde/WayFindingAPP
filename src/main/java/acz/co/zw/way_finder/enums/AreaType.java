package acz.co.zw.way_finder.enums;

public enum AreaType {
    Gates("Gates"),
    Lounges("Lounges"),
    Other("Other");

    private final String code;
    AreaType(String code){
        this.code=code;
    }
    public String getCode(){
        return code;
    }
}

