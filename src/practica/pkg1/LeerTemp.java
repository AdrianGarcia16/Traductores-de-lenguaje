/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.pkg1;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import static practica.pkg1.Practica1.*;

/**
 *
 * @author adria
 */
public class LeerTemp {
    
    public static ArrayList<String> tipo = new ArrayList<String>();
    public static ArrayList<String> valor = new ArrayList<String>();
    public static ArrayList<String> etq = new ArrayList<String>();
    public static ArrayList<String> CODOP = new ArrayList<String>();
    public static ArrayList<String> operando = new ArrayList<String>();
    public static ArrayList<String> CodigoM = new ArrayList<String>();
    
    public void LeerX(){                                                  //metodo para leer el archivo
        try {
            Scanner input = new Scanner(new File("TEMPORAL.txt"));                 //abrir archivo con la ruta especificada (la ruta es)
            while (input.hasNextLine()) {
                String line = input.nextLine();                                 //guardar linea completa del documento                
                StringTokenizer st = new StringTokenizer(line, "\t");
                int Pseg = 0;
                while (st.hasMoreTokens()) {
                    Pseg++;
                    switch(Pseg){
                        case 1:
                            tipo.add(st.nextToken());
                            break;
                        case 2:
                            valor.add(st.nextToken());
                            break;
                        case 3:
                            etq.add(st.nextToken());
                            break;
                        case 4:
                            CODOP.add(st.nextToken());
                            
                            break;
                        case 5:
                            operando.add(st.nextToken());
                            break;
                    }
                }
                
            }
            input.close();                                                      //cerrar archivo
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Directivas d = new Directivas();
        for(int i = 0;i<tipo.size();i++){
            if(!d.direc(CODOP.get(i), etq.get(i), operando.get(i), busVal(operando.get(i), i), i)){
                Iden(i);
            }
            else{
                if(CODOP.get(i).equals("FCC")){
                    String name="";
                    for(int i2 = 1;i2<operando.get(i).length()-1;i2++){
                        name=name + Integer.toHexString(operando.get(i).codePointAt(i2));
                    }
                    CodigoM.add(i, String.valueOf(name.toUpperCase()));
                }
                else if(CODOP.get(i).equals("ORG")){
                    CodigoM.add("0");
                }
                else if(CODOP.get(i).equals("END")){
                    CodigoM.add("0");
                }
                else if(!"-1".equals(d.direcVal(CODOP.get(i),Integer.parseInt(operando.get(i))))){
                    CodigoM.add(i, d.direcVal(CODOP.get(i),Integer.parseInt(operando.get(i))).toUpperCase());
                }
            }
            if(CODOP.get(i).equals("END")){
                CodigoM.add(i,"0");
            }
        }
        for(int i = 0;i<tipo.size();i++){
            
            System.out.print(valor.get(i) +"\t\t");
            System.out.print(etq.get(i) +"\t\t");
            System.out.print(CODOP.get(i) +"\t\t");
            System.out.print(operando.get(i) +"\t\t");
            System.out.println(CodigoM.get(i));
        }
    }
    
    public void Iden(int p){
        String aux = "";
        int val1;
        try {
            Scanner tab = new Scanner(new File("TABOP.txt"));                 //abrir archivo con la ruta especificada (la ruta es)

            String modo2 ="";
            while (tab.hasNextLine()) {
                String linea = tab.nextLine();                                 //guardar linea completa del documento                
                StringTokenizer stT = new StringTokenizer(linea, "\t");
                int Tseg=0;
                boolean ban = true;
                modo2="";
                while (stT.hasMoreTokens()){
                    String s = (String) stT.nextElement();
                    Tseg++;
                    switch(Tseg){
                        case 1:
                            if(CODOP.get(p).equals((s.toUpperCase()))){
                                modo2=s;
                            }
                           break;
                        case 3:
                            if(CODOP.get(p).equals(modo2.toUpperCase())){                                                                                                                                                     
                                String modo = null;
                                int val = busVal(operando.get(p), p);
                                modo = busLim(operando.get(p), p, val, s);
                                if(s.equals(modo)){
                                    switch (modo) {
                                        case "Directo":
                                            aux = (String) stT.nextElement();
                                            aux = aux + Integer.toHexString(val);
                                            String s1 = aux;
                                            for(int i = 0;i<4;i++){
                                                if(s1.length() < 4){
                                                    s1 = "0" + s1;
                                                }
                                            }
                                            s1=s1.toUpperCase();
                                            CodigoM.add(p, s1);
                                            break;
                                        case "Inmediato":
                                            aux = (String) stT.nextElement();
                                            
                                            int bit8 = 4;
                                            if(val>256){
                                                bit8=6;
                                            }
                                            if(val<0 || val> 65535){
                                                System.out.println("ERROR");
                                                System.out.println("OPERANDO NO CONCUERDA CON LOS LIMITES");
                                                System.exit(0);
                                            }
                                            aux = aux + Integer.toHexString(val);
                                            String s6 = aux;
                                            for(int i = 0;i<4;i++){
                                                if(s6.length() < bit8){
                                                    s6 = "0" + s6;
                                                }
                                            }
                                            s6=s6.toUpperCase();
                                            CodigoM.add(p, s6);
                                            break;
                                        case "Inherente":
                                            aux = (String) stT.nextElement();
                                            String s2 = aux;
                                            CodigoM.add(p, s2);
                                            break;
                                        case "Extendido":
                                            String aux1E = operando.get(p);
                                            aux = (String) stT.nextElement();
                                            if(aux1E.charAt(0) != '$'){
                                                if(Character.isAlphabetic(aux1E.charAt(0))){
                                                    LeerTABSIM l = new LeerTABSIM();
                                                    String s3E = l.Buscar(operando.get(p));
                                                    if(!s3E.equals("")){
                                                        CodigoM.add(p, (aux+s3E));
                                                    }
                                                    break;
                                                }
                                            }
                                            String s3 = Integer.toHexString(val);
                                            for(int i = 0;i<6;i++){
                                                if(s3.length()+aux.length() < 6){
                                                    aux =  aux + "0";
                                                }
                                            }
                                            
                                            s3 = aux+s3;
                                            s3=s3.toUpperCase();
                                            CodigoM.add(p, s3);
                                            break;
                                        case "IDX":
                                            String[] E = operando.get(p).split(",");
                                            
                                            aux = (String) stT.nextElement();
                                            if(E[1].contains("-") || E[1].contains("+")){
                                                CodigoM.add(p, aux+PrePost(operando.get(p)).toUpperCase());
                                                break;
                                            }
                                            if(E[0].equals("A") || E[0].equals("B") || E[0].equals("D")){
                                                 CodigoM.add(p, aux + Integer.toHexString(Integer.parseInt("111"+CalcularRR(operando.get(p))+"1"+CalcularRR(","+E[0]), 2)).toUpperCase());
                                            }
                                            else{
                                                String registro = operando.get(p);
                                                String calN =Integer.toBinaryString(val);
                                                for(int i = 0;i<6;i++){
                                                    if(calN.length() < 6){
                                                        calN = "0" + calN ;
                                                    }
                                                }
                                                String[] y = operando.get(p).split(",");
                                                float valF = 1;
                                                if(operando.get(p).charAt(0) != ','){
                                                    valF =Float.parseFloat(y[0]);
                                                }
                                                if(valF<0){
                                                    String z = Integer.toBinaryString((int)valF *-1);
                                                    for(int i = 0;i<5;i++){
                                                        if(z.length() < 5){
                                                            z = "0" +z;
                                                        }
                                                    }
                                                    calN = C2(z, 4);
                                                }
                                                String Formula = CalcularRR(registro) + "0" + calN;
                                                int FormulaH = Integer.parseInt(Formula, 2);
                                                String aux2 = (Integer.toHexString(FormulaH));
                                                for(int i = 0;i<3;i++){
                                                    if(aux2.length() < 2){
                                                        aux2 = "0" + aux2 ;
                                                    }
                                                }
                                                aux = aux + aux2;
                                                String s5 = aux;
                                                for(int i = 0;i<4;i++){
                                                    if(s5.length() < 4){
                                                        s5 = s5 + "0";
                                                    }
                                                }
                                                s5=s5.toUpperCase();
                                                CodigoM.add(p, s5);
                                            }
                                            
                                            break;
                                        case "IDX1":
                                            String[] registro1 = operando.get(p).split(",");
                                            String aux1="00", aus1a = Integer.toHexString(Integer.parseInt(registro1[0]));
                                            if(Integer.parseInt(registro1[0])<0){
                                                aux1="01";
                                                String c2 = Integer.toBinaryString(Integer.parseInt(registro1[0])*-1);
                                                for(int i = 0;i<9;i++){
                                                    if(c2.length() < 9){
                                                        c2 ="0"+ c2 ;
                                                    }
                                                }
                                                String h=C2(c2,8);
                                                aus1a = Integer.toHexString(Integer.parseInt(h, 2));
                                                aus1a = String.valueOf(aus1a.charAt(1)) + String.valueOf(aus1a.charAt(2));
                                            }
                                            aux1= "111"+CalcularRR(operando.get(p))+"0"+aux1;
                                            String aux3;
                                            aux1=Integer.toHexString(Integer.parseInt(aux1, 2));
                                            aux1=(String) stT.nextElement() + aux1 + aus1a;
                                            CodigoM.add(p, aux1.toUpperCase());
                                            break;
                                        case "IDX2":
                                            aux = (String) stT.nextElement();
                                            String[] g = operando.get(p).split(",");
                                            aux = aux + String.valueOf(Integer.toHexString(Integer.parseInt("111"+CalcularRR(operando.get(p))+"010",2))) + Integer.toHexString(Integer.parseInt(g[0]));
                                            CodigoM.add(p, aux.toUpperCase());
                                            break;
                                        case "[IDX2]":
                                            aux = (String) stT.nextElement();
                                            String s71 = operando.get(p).replace("]", "");
                                            String s7 = Integer.toHexString(Integer.parseInt("111"+CalcularRR(s71)+"011",2));
                                            s71 = operando.get(p).replace("[", "");
                                            String[] s72 = s71.split(",");
                                            s71 = Integer.toHexString(Integer.parseInt(s72[0]));
                                            for(int i = 0;i<4;i++){
                                                if(s71.length() < 4){
                                                    s71 = "0" + s71;
                                                }
                                            }
                                            s7 = s7 + s71;
                                            CodigoM.add(p, aux + s7.toUpperCase());
                                            break;
                                        case "[D,IDX]":
                                            aux = (String) stT.nextElement();
                                            String s8;
                                            s8=Integer.toHexString(Integer.parseInt("111"+CalcularRR(operando.get(p).replace("]", ""))+"111",2));
                                            CodigoM.add(p, aux + s8.toUpperCase());
                                            break;
                                        case "REL":
                                            aux = (String) stT.nextElement();
                                            String s9="";
                                            if(CODOP.get(p).toUpperCase().charAt(0) == 'B'){
                                                String sRel = Rel(p, 8);
                                                for(int i = 0;i<2;i++){
                                                    if(sRel.length() < 2){
                                                        sRel = "0" + sRel;
                                                    }
                                                }
                                                CodigoM.add(aux + sRel.toUpperCase());
                                                break;
                                            }
                                            else{
                                                String sRel = Rel(p, 16);
                                                for(int i = 0;i<4;i++){
                                                    if(sRel.length() < 4){
                                                        sRel = "0" + sRel;
                                                    }
                                                }
                                                CodigoM.add(aux + sRel.toUpperCase());
                                            }
                                            break;
                                    }
                                }
                                else if(modo.equals("Etq")){
                                    aux = (String) stT.nextElement();
                                    String s3 = agregarValorEtq(operando.get(p));
                                    if(s3.equals("")){
                                        System.out.println("ERROR");
                                        System.out.println("LA ETIQUETA NO SE ENCONTRO");
                                        System.exit(0);
                                    }
                                    for(int i = 0;i<6;i++){
                                        if(s3.length()+aux.length() < 6){
                                            aux =  aux + "0";
                                        }
                                    }
                                    s3 = aux+s3;
                                    s3=s3.toUpperCase();
                                    CodigoM.add(p, s3);
                                }
                                
                            }
                            break;
                    }
                }
            }
            tab.close();                                                      //cerrar archivo
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static String Rel(int position, int limit){
        LeerTABSIM l = new LeerTABSIM();
        String s = l.Buscar(operando.get(position));
        
        if(Integer.parseInt(valor.get(position+1), 16) < Integer.parseInt(s, 16)){
            int aux = Integer.parseInt(s, 16) - Integer.parseInt(valor.get(position+1), 16);
            if(chaeckLim(aux, limit)){
                
                return Integer.toHexString(aux);
            }
        }
        else{
            int aux = Integer.parseInt(valor.get(position+1), 16) - Integer.parseInt(s, 16);
            if(chaeckLim(aux, limit)){
                String s1 = String.valueOf(aux);
                for(int i = 0;i<limit;i++){
                    if(s1.length() < limit){
                        s1 = "0" + s1;
                    }
                }
                return Integer.toHexString(Integer.parseInt(C2(s1, limit-1),2));
            }
        }
        return s;
    }
    public static boolean chaeckLim(int valor, int limit){
        if(limit == 8){
            if(valor<-128 || valor>127){
                System.out.println(valor);
                System.out.println("“RANGO DEl DESPLAZAMIENTO NO VÁLIDO");
                System.exit(0);
            }
        }
        else{
            if(valor<-32768 || valor>32767){
                System.out.println("“RANGO DEl DESPLAZAMIENTO NO VÁLIDO");
                System.exit(0);
            }
        }
        
        return true;
    }
    
    public static String PrePost(String s){
        String[] g = s.split(",");
        
        if(g[1].charAt(0) == '+'){                                              //pre incremento
            String aux = g[1].replace("+", "");
            String h = Integer.toBinaryString(Integer.parseInt(g[0])-1);
            
            for(int i = 0;i<4;i++){
                if(h.length() < 4){
                    h ="0"+ h;
                }
            }
            
            String fin=CalcularRR(","+aux)+"10"+h;
            return Integer.toHexString(Integer.parseInt(fin, 2));
        }
        else if(g[1].charAt(0) == '-'){                                         //pre decremento
            String aux = g[1].replace("-", "");
            
            String h = Integer.toBinaryString(Integer.parseInt(g[0]));
            
            for(int i = 0;i<4;i++){
                if(h.length() < 4){
                    h ="0"+ h;
                }
            }
            String fin=CalcularRR(","+aux)+"10"+C2(h, 3);
            return Integer.toHexString(Integer.parseInt(fin, 2));
        }
        else if(g[1].charAt(g[1].length()-1) == '+'){                           //post incremento
            String aux = g[1].replace("+", "");
            String h = Integer.toBinaryString(Integer.parseInt(g[0])-1);
            
            for(int i = 0;i<4;i++){
                if(h.length() < 4){
                    h ="0"+ h;
                }
            }
            String fin=CalcularRR(","+aux)+"11"+h;
            if(CalcularRR(","+aux).equals("11")){
                System.out.println("SE ENCONTRO UN PC EN POST-INC");
                System.exit(0);
            }
            return Integer.toHexString(Integer.parseInt(fin, 2));
        }
        else if(g[1].charAt(g[1].length()-1) == '-'){                           //post decremento
            String aux = g[1].replace("-", "");
            
            String h = Integer.toBinaryString(Integer.parseInt(g[0]));
            
            for(int i = 0;i<4;i++){
                if(h.length() < 4){
                    h ="0"+ h;
                }
            }
            
            String fin=CalcularRR(","+aux)+"11"+C2(h, 3);
            return Integer.toHexString(Integer.parseInt(fin, 2));
        }
        else{
            System.out.println("ERROR DESCONOCIDO");
            System.exit(0);
        }
        return"";
    }
    
    public static String C2(String val, int c){
        String aux="";
        boolean ban= false;
        for(int i = c;i>-1;i--){
            if(ban == true){
                if(val.charAt(i) == '1'){
                    aux="0"+aux;
                }
                else{
                    aux="1"+aux;
                }
            }
            else{
                if(val.charAt(i) == '1'){
                    aux="1"+aux;
                    ban = true;
                }
                else{
                    aux="0"+aux;
                }
            }
        }
        return aux;
    }
    
    public static String CalcularRR(String s){
        String[] s1 = s.split(",");
        s = s1[1];
        switch(s){
            case "X":
                return "00";
            case "Y":
                return "01";
            case "SP":
                return "10";
            case "PC":
                return "11";
            case "A":
                return "00";
            case "B":
                return "01";
            case "D":
                return "10";
            default:
                System.out.println(s);
                System.out.println("ERROR NO SE ENCONTRO X, Y, SP, O PC");
                System.exit(0);
        }
        return "";
    }
    
     public static String agregarValorEtq(String S){
        try {
            Scanner input = new Scanner(new File("TABSIM.txt"));                 //abrir archivo con la ruta especificada (la ruta es)
            while (input.hasNextLine()) {
                String line = input.nextLine();                                 //guardar linea completa del documento                
                StringTokenizer stT = new StringTokenizer(line, "\t");
                int x = 1;
                while(stT.hasMoreTokens() && x<4){
                    if(x == 1){
                        stT.nextToken();
                        if(S.equals(stT.nextToken())){
                            
                            x=3;
                        }
                        else{
                            x=4;
                        }
                    }
                    if(x == 3){
                        return stT.nextToken();
                    }
                    
                }
            }
            input.close();                                                      //cerrar archivo
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
