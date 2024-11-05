package acz.co.zw.way_finder.enums;

public enum EmployeePosition {

        DutyManager("DutyManager"),
        Supervisor("Supervisor"),
        Stuff("Stuff");

        private final String code;
        EmployeePosition(String code){
            this.code=code;
        }
        public String getCode(){
            return code;
        }
    }



