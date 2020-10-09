/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.pkg1;

/**
 *
 * @author adria
 */
public class Directivas {

    public boolean direc1(String DIR, String ETQ, String OP, int val, int Nlin){
        switch(DIR){
            case "DW":
                if(val <0 || val >65535 ){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                return true;
            case "DB":
                if(val <0 || val >255 ){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                return true;
            case "DC.W":
                if(val <0 || val >65535 ){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                return true;
            case "DC.B":
                if(val <0 || val >255 ){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                return true;
            case "FCB":
                if(val <0 || val >255 ){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                return true;
            case "FDB":
                if(val <0 || val >65535 ){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                return true;
            case "FCC":
                return true;
            case "ORG":
                if(val <0 || val >65535 ){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                return true;
            case "END":
                return true;
            case "DS":
                if(val <0 || val > 65535){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                return true;
            case "DS.B":
                if(val <0 || val > 65535){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                return true;
            case "DS.W":
                if(val <0 || val > 65535){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                return true;
            case "RMB":
                if(val <0 || val > 65535){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                return true;
            case "RMW":
                if(val <0 || val > 65535){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }   
                return true;
            case "EQU":
                if(val <0 || val > 65535){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                
                return true;
        }
        return false;
    }
    
    public boolean direc(String DIR, String ETQ, String OP, int val, int Nlin){
        automata au = new automata();
        switch(DIR){
            case "DW":
                if(val <0 || val >65535 ){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                au.addmemo(2);
                return true;
            case "DB":
                if(val <0 || val >255 ){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                au.addmemo(1);
                return true;
            case "DC.W":
                if(val <0 || val >65535 ){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                au.addmemo(2);
                return true;
            case "DC.B":
                if(val <0 || val >255 ){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                au.addmemo(1);
                return true;
            case "FCB":
                if(val <0 || val >255 ){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                au.addmemo(1);
                return true;
            case "FDB":
                if(val <0 || val >65535 ){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                au.addmemo(2);
                return true;
            case "FCC":
                au.addmemo(OP.length()-2);
                return true;
            case "ORG":
                if(val <0 || val >65535 ){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                au.setmemo(Integer.toHexString(val).toUpperCase());
                return true;
            case "END":
                return true;
            case "DS":
                if(val <0 || val > 65535){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                au.addmemo(val);
                return true;
            case "DS.B":
                if(val <0 || val > 65535){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                au.addmemo(val);
                return true;
            case "DS.W":
                if(val <0 || val > 65535){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                au.addmemo(val*2);
                return true;
            case "RMB":
                if(val <0 || val > 65535){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                au.addmemo(val);
                return true;
            case "RMW":
                if(val <0 || val > 65535){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                au.addmemo(val*2);
                return true;
            case "EQU":
                if(OP == null || ETQ == null){
                    System.out.println("ERROR");
                    System.out.println("EQU NO CUMPLE CON SUS CARACTERISTICAS");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                if(val <0 || val > 65535){
                    System.out.println("ERROR");
                    System.out.println("OPERANDO DEMACIADO GRANDE");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                au.setEquVal(val);
                return true;
        }
        return false;
    }
}
