/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.pkg1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
/**
 *
 * @author adria
 */
public class automata {
    public static int cont = 0;
    public static String pila[] = new String[10];
    public static int contPila = 0;
    public static String[] contenido = new String[256];
    public static String[] tabsin = new String[256];
    public static String[] ETQ = new String[256];
    public static int auxETQ = 0;
    public static int contTab = 0;
    public static int contCon;
    public static String memo;
    public static String memoA;
    public static String EquVal;
    public int estado(){
        return cont;
    }
    public void init(String s){
        automata.cont = 0;
        automata.contPila=0;
        automata.pila[0] =s;
    }
    
    public void setmemo(String x){
        automata.memo = x;
        memoA=x;
    }
    
    public void setEquVal(int s){
        
        EquVal = Integer.toHexString(s);
    }
    
    public void addmemo(int x){
        memoA = memo;
        int aux = Integer.parseInt(memo, 16);
        aux = x + aux;
        
        memo = String.valueOf(Integer.toHexString(aux));
    }
    
    public void inicio(String s, int Nlin, String etq, String operando, String codigo){
        if(memo != null){
            for(int i = memo.length();i<4;i++){
                memo = "0" + memo;
            }
        }
        if(memoA != null){
            for(int i = memoA.length();i<4;i++){
                memoA = "0" + memoA;
            }
        }
        if(EquVal != null){
            for(int i = EquVal.length();i<4;i++){
                EquVal = "0" + EquVal;
            }
        }
        s = s.toUpperCase();
        if(memo != null){
            memo=memo.toUpperCase();
        }
        
        if(memoA != null){
            memoA=memoA.toUpperCase();
        }
        switch(cont){
            case 0:
                if(s.equals("ORG")){
                    if(operando == null){
                        System.out.println("ERROR");
                        System.out.println("LINEA " + Nlin);
                        System.exit(0);
                    }
                    if(pila[contPila].equals("") || pila[contPila].equals("EQU")){
                        contCon++;
                        contenido[contCon] = "DIR_INIC\t"+ memo +"\t" + etq +"\t" + codigo +"\t"+operando +"\n";
                        
                        pila[contPila] = s;
                        cont++;
                    }
                }
                else if(s.equals("EQU")){
                    if(pila[contPila].equals("") || pila[contPila].equals("EQU")){
                        contCon++;
                        contenido[contCon] = "VALOR EQU\t"+ EquVal +"\t" + etq +"\t" + codigo +"\t"+operando +"\n";
                        
                        pila[contPila] = s;
                        
                    }
                }
                else{
                    System.out.println("ERROR");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
                break;
            case 1:{
                
                switch (s) {
                    
                    case "CODOP":
                        if(pila[contPila].equals("ORG") || pila[contPila].equals("F")){
                            contCon++;
                            contenido[contCon] = "CONTLOC\t"+ memo +"\t" + etq +"\t" + codigo +"\t"+operando +"\n";
                            pila[contPila] = "F";
                        }
                        break;
                    case "DIR":
                        if(pila[contPila].equals("ORG") || pila[contPila].equals("F")){
                            
                            contCon++;
                            contenido[contCon] = "CONTLOC\t"+ memoA +"\t" + etq +"\t" + codigo +"\t"+operando +"\n";
                            
                            pila[contPila] = "F";
                        }
                        break;
                    case "EQU":
                        if(pila[contPila].equals("ORG") || pila[contPila].equals("F")){
                            contCon++;
             /*XDXD*/       contenido[contCon] = "VALOR EQU\t"+ EquVal +"\t" + etq +"\t" + codigo +"\t" +operando +"\n";                            
                            pila[contPila] = "F";

                        }
                        break;
                    case "CODOPE":
                        //if(pila[contPila].equals("ORG") || pila[contPila].equals("F")){
                          //  contTab++;
                            //tabsin[contTab] = "CONTLOC(ETIQUETA_RELATIVA)\t" + etq+ "\t" + memo+"\n";
                        //}
                        break;
                    case "END":
                        
                        if(pila[contPila].equals("ORG") || pila[contPila].equals("F")){
                            cont++;
                            
                            contCon++;
                            contenido[contCon] = "CONTLOC\t"+ memo +"\t" + etq +"\t" + codigo +"\t" +operando +"\n";
                            
                            cargarDatos();
                        }
                        break;
                    default:
                        System.out.println("ERROR");
                        System.out.println("LINEA " + Nlin);
                        System.exit(0);
                }
                    break;
            }
            default:
                break;
        }
        if(etq != null){
            for(int i = 0;i<auxETQ;i++){
                if(etq.equals(ETQ[i])){
                    System.out.println("ERROR");
                    System.out.println("ETIQUETA REPETIDA");
                    System.out.println("LINEA " + Nlin);
                    System.exit(0);
                }
            }
            if(s.equals("EQU")){
                contTab++;
                tabsin[contTab] = "EQU(ETIQUETA_ABSOLUTA)\t" + etq+ "\t" +EquVal +"\n";
            }
            else{
                contTab++;
                tabsin[contTab] = "CONTLOC(ETIQUETA_RELATIVA)\t" + etq+ "\t" + memo+"\n";
                
            }
            ETQ[auxETQ] = etq;
            auxETQ++;
        }
        
    }
    
    public static void cargarDatos(){
        File file = new File("TEMPORAL.txt");
        File file2 = new File("TABSIM.txt");
        try {
            //contenido[0] = "\t\tVALOR\tETQ\tCODOP\tOPERANDO\n";
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 1;i<contCon+1;i++){
                bw.write(contenido[i]);
            }
            bw.close();
            
            //tabsin[0]= "\t\t\tETIQUETA\tVALOR\n";
            if (!file2.exists()) {
                file2.createNewFile();
            }
            FileWriter fw2 = new FileWriter(file2);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            for(int i = 1;i<contTab+1;i++){
                bw2.write(tabsin[i]);
            }
            bw2.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
