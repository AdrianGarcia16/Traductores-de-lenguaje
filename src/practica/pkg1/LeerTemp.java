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
                CodigoM.add("0");
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
                                            aux = (String) stT.nextElement();
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
                                        default:
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
