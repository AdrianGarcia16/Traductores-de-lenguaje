/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.pkg1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import static practica.pkg1.LeerTemp.*;

/**
 *
 * @author adria
 */
public class GenerarObj {
    public static int aux = 0;
    public static int addres = Integer.parseInt(valor.get(0), 16);
    public static String sobrante = "";
    public void createObj(String arch){
        File file = new File("codigo.obj");
        try{
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            String name="";
            for(int i2 = 0;i2<arch.length();i2++){
                name=name + Integer.toHexString(arch.codePointAt(i2));
            }
            String name2 = Integer.toHexString(arch.length()+10) +"0000";
            if(arch.length()+10<16){
                name2 = "0" + Integer.toHexString(arch.length()+10) +"0000";
            }
            String s0 = "S0"+ name2 + "46696C653A20" + name.toUpperCase() +"0A\n";
            
            String s1 = "";
            while(!"ORG".equals(CODOP.get(aux))){
                aux++;
            }
            addres = Integer.parseInt(valor.get(aux), 16);
            while(aux<CODOP.size()-2){
                if(!CODOP.get(aux).equals("END")){
                    
                    s1=s1 + check(aux, addres, sobrante);
                }
                
            }
            System.out.println(s1);
            bw.write(s0.toUpperCase());
            bw.write(s1.toUpperCase());
            bw.write("S9030000FC");
            
            bw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static String check(int aux1, int value, String res){
        if(res != null){
            value = value - res.length()/2;
            sobrante = "";
        }
        String cadena=res;
        res = null;
        String complement = "";
        while(res == null){
            if(CODOP.get(aux1).equals("END")){
                aux1++;
                aux= aux1;
                return"";
            }
            else if(CODOP.get(aux1).equals("EQU")){
                aux1++;
            }
            else if(CodigoM.get(aux1).equals("NULL")){
                if(aux == aux1){
                    aux++;
                    return "";
                }
                aux1++;
                aux = aux1;
                addres = cadena.length()/2 + addres;
                if((cadena.length()/2)+6 <16){
                    complement = "0";
                }
                if(valor.size()-1 > aux1){
                    addres = Integer.parseInt(valor.get(aux1), 16);
                }
                
                String h = "";
                if(Integer.toHexString(value).length()+h.length() < 4){
                    for(int i = 0;i<4;i++){
                        if(Integer.toHexString(value).length()+h.length() < 4){
                            h = h + "0";
                        }
                    }
                }
                return "S1"+complement+ Integer.toHexString((cadena.length()/2)+3)+ h + Integer.toHexString(value) + cadena+ checkSum(complement+ Integer.toHexString((cadena.length()/2)+3)+h + Integer.toHexString(value) + cadena) +"\n";
            }
            else{
                if(cadena==""){
                    value=Integer.parseInt(valor.get(aux1), 16);
                }
                if(cadena.length() + CodigoM.get(aux1).length() >=32){
                    cadena = DivString(cadena, CodigoM.get(aux1));
                    aux1++;
                    aux = aux1;
                    if((cadena.length()/2)+6 <16){
                        complement = "0";
                    }
                    addres = Integer.parseInt(valor.get(aux), 16);
                    String h = "";
                    if(Integer.toHexString(value).length()+h.length() < 4){
                        for(int i = 0;i<4;i++){
                            if(Integer.toHexString(value).length()+h.length() < 4){
                                h = h + "0";
                            }
                        }
                    }
                    return "S1"+complement+ Integer.toHexString((cadena.length()/2)+3)+ h + Integer.toHexString(value)+ cadena + checkSum(complement+ Integer.toHexString((cadena.length()/2)+3)+h+ Integer.toHexString(value) + cadena) +"\n";
                }
                else{
                    cadena = cadena + CodigoM.get(aux1);
                    aux1++;
                }
                
            }
        }
        aux = aux1;
        if((cadena.length()/2)+6 <16){
            complement = "0";
        }
        String h = "";
        if(Integer.toHexString(value).length()+h.length() < 4){
            for(int i = 0;i<4;i++){
                if(Integer.toHexString(value).length()+h.length() < 4){
                    h = h + "0";
                }
            }
        }
        return "S1"+complement+ Integer.toHexString((cadena.length()/2)+3 )+ h +Integer.toHexString(value)+ cadena+ checkSum(complement+ Integer.toHexString((cadena.length()/2)+3)+h+ Integer.toHexString(value) + cadena) +"\n";
    }
    public static String DivString(String cadena, String s){
        int i2 = 0;
        for(int i = cadena.length();i<32 && i2<s.length();i++,i2++){
            cadena = cadena + s.charAt(i2);
        }
        String S1="";
        if(i2<s.length()){
            for(int i = i2;i<s.length();i++){
                S1 = S1 +s.charAt(i);
            }
            sobrante=S1;
        }
        
        return cadena;
    }
    public static String checkSum(String s){
        String [] cs = new String[(s.length()/2)+1];
        int i2 = 0;
        for(int i = 0;i<s.length();i= i+2,i2++){
            cs[i2]=String.valueOf(s.charAt(i));
            cs[i2] =cs[i2] + String.valueOf(s.charAt(i+1));
        }
        float cont = 0;
        for(int i=0;i<s.length()/2;i++){
            cont = cont + Integer.parseInt(cs[i], 16);
        }
        String contH = String.valueOf(cont);
        contH = contH.replace(".0", "");
        return c1(contH);
    }
    public static String c1(String s){
        s = Integer.toBinaryString(Integer.parseInt(s));
        String aux = "";
        for(int i = 0; i<16;i++){
            if(s.length() <16){
                s = "0"+s;
            }
        }
        for(int i = 0;i<s.length();i++){
            if(s.charAt(i) == '1'){
                aux = aux + "0";
            }
            if(s.charAt(i) == '0'){
                aux = aux + "1";
            }
        }
        
        
        
        String resH =(Integer.toHexString(Integer.parseInt(aux, 2)));
        return (String.valueOf(resH.charAt(resH.length()-2)) + String.valueOf(resH.charAt(resH.length()-1)));
    }
}
