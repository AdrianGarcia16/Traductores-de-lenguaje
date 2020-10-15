/*
 * PRACTICA 2
 */
package practica.pkg1;
import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author adrian
 */
public class Practica1 {

    /**
     * @param args the command line arguments
     */
        public static String etiqueta = null, codigo = null, operando = null;
   
    public static boolean salir = false;
    public static boolean agrLinea = true;                                      //para no agregar las lineas con aomentarios
    public static String mod = "";
    public static String rel = "";
    public static void main(String[] args) {
        automata au = new automata();
        au.init("");
        
    /*    System.out.println("Instrucción" +
                            "(CODOP)\t" +
                            "Operando (Sí" +
                            "No)\t\t" +
                            "Modo " +
                            "direccionamiento\t" +
                            "Código" +
                            "máquina (hex)\t" +
                            "Total de bytes" +
                            "calculados\t" +
                            "Total de bytes" +
                            "por calcular\t" +
                            "Suma total de" +
                            "bytes\t");*/
        Leer();                                                                 //llamamos al metodo leer para analizar el contenido del arvhivo
        if(au.estado() != 2){
            System.out.println(au.estado());
            System.out.println("ERROR DESCONOCIDO");
            System.exit(0);
        }
        LeerTemp lt = new LeerTemp();
        lt.LeerX();
    }
    public static void Leer(){                                                  //metodo para leer el archivo
        try {
            Scanner input = new Scanner(new File("P3ASM.txt"));                 //abrir archivo con la ruta especificada (la ruta es)
            int nLin = 1;
            while (input.hasNextLine()) {
                String line = input.nextLine();                                 //guardar linea completa del documento                
                
                separar(line, nLin);
                nLin++;
            }
            input.close();                                                      //cerrar archivo
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    
    public static void separar(String line, int Nlin){
        StringTokenizer st = new StringTokenizer(line, "\t");
        int Pseg=0;
        if(line.equals("")){
            return;
        }
        if(line.charAt(0) == '\t'){
                Pseg++;
            }
        
        while (st.hasMoreTokens()) {
            //System.out.println(st.nextToken());
            
            if((comprobar(st.nextToken(), Pseg, Nlin)) == false){
                System.exit(0);
            }
            Pseg++;
            if(salir==true){
                break;
            }
        }
        
        if(codigo==null && agrLinea==true){
            System.out.println("ERROR");
            System.out.println("No se encontro el codigo de operación");
            System.out.println("LINEA " + Nlin);
            System.exit(0);
        }
        
        
        
        if(agrLinea == true){
            /*System.out.println("ETIQUETA: " + etiqueta);
                System.out.println("CODOP: " + codigo);
                System.out.println("OPERANDO: "+ operando);
                System.out.println("");
                if(codigo.equals("END")){
                    System.exit(0);
                }*/
            boolean ban = false;
            try {
                    Scanner input = new Scanner(new File("TABOP.txt"));                 //abrir archivo con la ruta especificada 
                    boolean bandera = true;
                    boolean salir = false;
                    while (input.hasNextLine() && salir != true){
                        String tabopL = input.nextLine();                                 //guardar linea completa del documento                
                        Directivas dr = new  Directivas();
                        StringTokenizer stT = new StringTokenizer(tabopL, "\t");
                        
                        automata au = new automata();
                                    if(dr.direc(codigo, etiqueta, operando, busVal(operando, Nlin), Nlin)){                     //comprobar las directivas 
                                        if(codigo.equals("ORG")){
                                           au.inicio("ORG", Nlin, etiqueta, operando, codigo); 
                                           bandera = false;
                                          
                                           break;
                                        }
                                        else if(codigo.equals("END")){
                                           au.inicio("END", Nlin, etiqueta, operando, codigo); 
                                           bandera = false;
                                           
                                           break;
                                        }
                                        else if(codigo.equals("EQU")){
                                           
                                           au.inicio("EQU", Nlin, etiqueta, operando, codigo); 
                                           bandera = false;
                                           
                                           break;
                                        }
                                        au.inicio("DIR", Nlin, etiqueta, operando, codigo);
                                        bandera = false;
                                        
                                        break;
                                    }
                                    else if(codigo.toUpperCase().equals(stT.nextToken().toUpperCase())){
                                int cont = 0, cont2 = 0;
                                boolean TM = true;
                                while (stT.hasMoreTokens()) {
                                    mod = "";
                                    String s = (String) stT.nextElement();
                                    
                                     if("Operando".equals(s)){
                                        //System.out.print("\t\t\tSI\t");
                                        
                                        if(operando == null){
                                            System.out.println("\nERROR \nNO SE ENCONTRO OPERANDO");
                                            System.out.println("LINEA" + Nlin);
                                            System.exit(0);
                                        }
                                        cont++;
                                    }
                                    else if("No Operando".equals(s)){
                                        //System.out.print("\t\t\tNO\t");
                                        
                                        if(operando != null){
                                            System.out.println("\nERROR \nSE ENCONTRO OPERANDO");
                                            System.out.println("LINEA" + Nlin);
                                            System.exit(0);
                                        }
                                        //au.inicio("CODOP", Nlin, etiqueta, operando, codigo);   
                                        //System.out.print("INHERENTE 1 BYTE");                             *-+
                                        
                                        cont++;
                                    }
                                    else{
                                        int num = 0;
                                        
                                        switch (cont) {
                                            case 3:
                                                //System.out.print("\t\t\t"+s);
                                                cont2 = Integer.parseInt(s);
                                                cont++;
                                                break;
                                            case 4:
                                                cont2= cont2 + Integer.parseInt(s);
                                                //System.out.println("\t\t\t\t"+s+"    ");
                                                num = Integer.parseInt(s);
                                                if(ban == true){
                                                    String mod1 = " ";
                                                    if("rel".equals(rel)){
                                                        rel = "";
                                                        if(s=="2"){
                                                            mod1 = ") Relativos de 8 bits ";
                                                        }
                                                        else{
                                                            mod1 = ") Relativos de 16 bits ";
                                                        }
                                                    }
                                                    //System.out.println(mod1+s + " BYTES");
                                                    au.addmemo(Integer.parseInt(s));
                                                    ban = false;
                                                    salir = true;
                                                }
                                                //System.out.println("\t\t\t\t"+cont2+"    ");
                                                
                                                break;
                                            default:
                                                //
                                                cont++;
                                                String modo = null;
                                                int val = -1;
                                                val = busVal(operando, Nlin);
                                                modo = busLim(operando, Nlin, val, s);
                                                if(modo.equals(s)){mod = "";
                                                    ban = true;
                                                    au.inicio("CODOP", Nlin, etiqueta, operando, codigo);
                                                }
                                                else if(modo.equals("Etq")){
                                                    mod = "";
                                                    ban = true;
                                                    
                                                    au.inicio("CODOP", Nlin, etiqueta, operando, codigo);//*-+
                                                }
                                                break;
                                        }
                                        
                                    }
                                    
                                }
                                if(TM == false){
                                    break;
                                }
                                bandera = false;
                            }
                            /*else if(codigo.equals("END")){
                                bandera = false;
                                break;
                            }
                            
                            /*else if(codigo.equals("ORG")){
                                System.out.println(etiqueta+"\t\t" +codigo+"\t\t" +operando + "\t\tERROR");
                                bandera = false;
                                break;
                            }*/
                    }
                    if(bandera==true ){
                        System.out.println("ERROR");
                        System.out.println("No se encontro el CODOP12");
                        System.out.println("LINEA " + Nlin);
                        System.exit(0);
                    }
                    
                    input.close();                                                      //cerrar archivo
                }catch (Exception ex) {
                    ex.printStackTrace();
            }
            codigo = null; etiqueta = null; operando = null;
        }
        else{
            System.out.println("COMENTARIO");
            System.out.println("");
        }
        agrLinea=true;
        
    }
    
        public static String busLim(String val1, int nLin, int val, String Rel){
            
            try{
                if("null".equals(val1)){
                    return "Inherente";
                }
            String Seg = val1;
            if(Rel.equals("REL") && val1 != null){
                    if(!((Seg.charAt(0) < 65 || Seg.charAt(0) > 90)  && (Seg.charAt(0) <97 || Seg.charAt(0) > 122))){
                        if(Seg.length()>8){
                            System.out.println("ERROR\n ETIQUETA MUY LARGA");
                            System.out.println("LINEA " + nLin);
                            System.exit(0);
                        }
                        for(int i = 1; i<Seg.length();i++){
                            if(!((Character.isLetter(Seg.charAt(i))) || (Character.isDigit(Seg.charAt(i))) || (Seg.charAt(i) == 95))){
                                System.out.println("ERROR\n LA ETIQUETA CONTIENE CARACTERES INVALIDOS");
                                System.out.println("LINEA " + nLin);
                                System.exit(0);

                            }
                        }
                        rel = "rel";
                        return "REL";
                    }
                    else{
                        System.out.println("ERROR \nLA ETIQUETA CONTIENE NO CUMPLE CON SUS CARACTERISTICAS");
                        System.out.println("LINEA " + nLin);
                        System.exit(0);
                    }
            }
            else if(Rel.equals("Extendido")){
                if(Character.isLetter(val1.charAt(0))){
                    
                    if(!val1.contains(",")){
                        
                        if(!((Seg.charAt(0) < 65 || Seg.charAt(0) > 90)  && (Seg.charAt(0) <97 || Seg.charAt(0) > 122))){
                            if(Seg.length()>8){
                                System.out.println("ERROR\n ETIQUETA MUY LARGA");
                                System.out.println("LINEA " + nLin);
                                System.exit(0);
                            }
                            for(int i = 1; i<Seg.length();i++){
                                if(!((Character.isLetter(Seg.charAt(i))) || (Character.isDigit(Seg.charAt(i))) || (Seg.charAt(i) == 95))){
                                    System.out.println("ERROR\n LA ETIQUETA CONTIENE CARACTERES INVALIDOS");
                                    System.out.println("LINEA " + nLin);
                                    System.exit(0);

                                }
                            }
                            
                            return "Etq";
                        }
                        else{
                            System.out.println("ERROR \n LA ETIQUETA CONTIENE NO CUMPLE CON SUS CARACTERISTICAS");
                            System.out.println("LINEA " + nLin);
                            System.exit(0);
                        }
                    }
                }
            }
            else if (val1.contains(",")){
                String s[] = val1.split(",");
                if(!s[1].equals("X") && !s[1].equals("Y") &&!s[1].equals("SP") &&!s[1].equals("PC")){
                    if(!s[1].contains("-") && !s[1].contains("+") && !s[1].contains("]")){
                        System.out.println("ERROR  REGISTRO DE COMPUTADORA INVALIDO");
                        System.out.println("LINEA "+ nLin);
                        System.exit(0);
                    }
                }
            }
            if(val1.contains("#")){
                if(val1.contains(",")){
                    System.out.println("ERROR12");
                    System.out.println("LINEA "+ nLin);
                    System.exit(0);
                }
                return "Inmediato";
            }
            else if(val1.contains("[")){
                if(val1.charAt(1) == 'D' || val1.charAt(1) == 'd'){
                    String s[] = val1.split(",");
                    s[1] = s[1].replace("]", "");
                    if(s[1].length() > 2){
                        System.out.println("ERROR REGISTRO DE COMPUTADORA INVALIDO");
                        System.out.println("LINEA "+ nLin);
                        System.exit(0);
                    }
                    s[1] = s[1].toUpperCase();
                    if(!s[1].equals("X") && !s[1].equals("Y") &&!s[1].equals("SP") &&!s[1].equals("PC")){
                        System.out.println("ERROR  REGISTRO DE COMPUTADORA INVALIDO");
                        System.out.println("LINEA "+ nLin);
                        System.exit(0);
                    }
                    mod = ") indizado de acumulador indirecto";
                    return "[D,IDX]";
                }
                
                mod = ") Indirecto de 16 bits,";
                return "[IDX2]";
            }else{
                boolean ban = false;
                for(int i = 0;i<val1.length();i++){
                    if(!Character.isDigit(val1.charAt(i)) && val1.charAt(i) != '%' && val1.charAt(i) != '$' && val1.charAt(i) != '@'){
                        if(!Character.isLetter(val1.charAt(i))){
                            ban = true;
                        }
                    }
                }
                if(ban != true){
                    if(!Character.isLetter(val1.charAt(0))){
                        //System.out.println(val1);
                        if(val > -1 && val < 256){
                            return "Directo";
                        }
                        else if(val > 255 && val < 65536){
                            return "Extendido";
                        }
                    }else{
                        return "";
                    }
                }
                else{
                    if(val1.charAt(0) == ','){
                        val1 = "0" + val1;
                    }
                    else if(!Character.isDigit(val1.charAt(0)) && val1.charAt(0) != '-' && !val1.contains(",")){
                        val1 = "0," + val1;
                    }
                    String valorF[] = val1.split(",");
                    boolean reg = false;
                    
                    if(valorF[0].charAt(0) == 'A' || valorF[0].charAt(0) == 'a' || valorF[0].charAt(0) == 'B' || valorF[0].charAt(0) == 'b' || "D".equals(valorF[0]) || "d".equals(valorF[0])){
                        reg = true;
                    }
                    if(reg == true){
                        for(int i = 0;i<valorF[1].length();i++){
                        if(valorF[1].charAt(i) != 'X' && valorF[1].charAt(i) != 'Y' && valorF[1].charAt(i) != 'x'&& valorF[1].charAt(i) != 'y'){
                            if(valorF[1].charAt(i) != 'P' && valorF[1].charAt(i) != 'p'){
                                if(valorF[1].charAt(i) != 'S' && valorF[1].charAt(i) != 's'){
                                    if(valorF[1].charAt(i) != 'C' && valorF[1].charAt(i) != 'c'){
                                        System.out.println("ERROR12");
                                        System.out.println("LINEA " + nLin);
                                        System.exit(0);
                                    }
                                }
                            }
                            
                        }
                        mod = ") indizado de acumulador ";
                        return "IDX";
                    }
                    }
                    float aux = Float.parseFloat(valorF[0]);
                    if(aux<9 && aux>0){
                        if(valorF[1].contains("-") || valorF[1].contains("+")){
                            String aux2 = valorF[1].replace("+", "");
                            aux2 = aux2.replace("-", "");
                            if(!aux2.equals("X") &&!aux2.equals("Y") && !aux2.equals("SP") && !aux2.equals("PC")){
                                System.out.println(valorF[1]);
                                System.out.println("ERROR43");
                                System.out.println("LINEA " + nLin);
                                System.exit(0);
                            }
                            if(!Character.isDigit(valorF[1].charAt(0))){
                                if(valorF[1].charAt(0) == '+'){
                                    mod = " Indizado de pre incremento";
                                    return "IDX";
                                }
                                 if(valorF[1].charAt(0) == '-'){
                                    mod = " Indizado de pre decremento";
                                    return "IDX";
                                }
                            }
                            if(!Character.isDigit(valorF[1].charAt(valorF[1].length()-1))){
                                if(valorF[1].charAt(valorF[1].length()-1) == '+'){
                                    mod = " Indizado de post incremento";
                                    return "IDX";
                                }
                                 if(valorF[1].charAt(valorF[1].length()-1) == '-'){
                                    mod = " Indizado de post decremento";
                                    return "IDX";
                                }
                            }
                        }
                        
                    }
                    if(aux < 16 && aux > -17){
                        mod = ") Indizado de 5 bits";
                        return "IDX";
                    }
                    else if(aux < -16 && aux > -257){
                        mod = ") Indizado de 9 bits";
                        return "IDX1";
                    }
                    else if(aux > 15 && aux < 256){
                        mod = ") Indizado de 9 bits";
                        return "IDX1";
                    }
                    else if(aux > 255 && aux < 65536){
                        mod = ") Indizado de 16 bits,";
                        return  "IDX2";
                    }
                    else{
                        System.out.println("ERROR");
                        System.out.println("LINEA " + nLin);
                        System.exit(0);
                    }
                }
            }
            System.out.println("ERROR");
            System.out.println("LINEA " + nLin);
            System.exit(0);
            return null;
            }
            catch(Exception e){
                return "Inherente";
            }
    }
    
    public static int busVal(String val, int nLINE){
        boolean salir = false;
        try{
        for(int i = 0;i<val.length() && salir==false;i++){
            if(val.charAt(i) == ','){
                break;
            }
            if(!Character.isDigit(val.charAt(i))){
                salir=true;
            }
        }
        if(salir== true){
            if(val.contains("@")){
                String cadena[] = val.split("@");
                for(int i = 0;i<cadena[1].length();i++){
                    if(!Character.isDigit(cadena[1].charAt(i))){
                        System.out.println("\nERROR CARACTER INVALIDO EN EL SISTEMA NUMERICO");
                        System.out.println("Linea "+nLINE);
                        System.exit(0);
                    }
                    String aux = String.valueOf(cadena[1].charAt(i));
                    if(Integer.parseInt(aux) > 7 ){
                        System.out.println("\nERROR CARACTER INVALIDO EN EL SISTEMA NUMERICO");
                        System.out.println("Linea "+nLINE);
                        System.exit(0);
                    }
                }
                return Integer.parseInt(cadena[1], 8);
            }
            else if(val.contains("%")){
                String cadena[] = val.split("%");
                for(int i = 0;i<cadena[1].length();i++){
                    String aux = String.valueOf(cadena[1].charAt(i));
                    if(!Character.isDigit(cadena[1].charAt(i))){
                        System.out.println("\nERROR CARACTER INVALIDO EN EL SISTEMA NUMERICO");
                        System.out.println("Linea "+nLINE);
                        System.exit(0);
                    }
                    if(Integer.parseInt(aux) > 1 ){
                        System.out.println("\nERROR CARACTER INVALIDO EN EL SISTEMA NUMERICO");
                        System.out.println("Linea "+nLINE);
                        System.exit(0);
                    }
                }
                return Integer.parseInt(cadena[1], 2);
            }
            else if(val.contains("$")){
                String cadena[] = val.split("\\$");
                
                for(int i = 0;i<cadena[1].length();i++){
                    if(cadena[1].charAt(i) <48 || cadena[1].charAt(i) > 57){
                        if(cadena[1].charAt(i) < 65 || cadena[1].charAt(i) > 70){
                            System.out.println("\nERROR CARACTER INVALIDO EN EL SISTEMA NUMERICO");
                            System.out.println("Linea "+nLINE);
                            System.out.println(cadena[1].charAt(i));
                            System.exit(0);
                        }
                    }
                }
                return Integer.parseInt(cadena[1], 16);
            }
            else if(val.contains("[")){
                String s = val.replace("[", "");
                s = s.replace("]", "");
                String Sp[] = s.split(",");
                Sp[0] = Sp[0].toUpperCase();
                if(Sp[0].charAt(0) != 'D'|| (Sp[0].length() >1)){
                    for(int i = 0;i<Sp[0].length();i++){
                        if(!Character.isDigit(Sp[0].charAt(i))){
                            System.out.println("\nERROR CARACTER INVALIDO EN EL SISTEMA NUMERICO");
                            System.out.println("Linea "+nLINE);
                            System.exit(0);
                        }
                    
                    }
                }
                
            }
            else if(val.contains(",")){
                String Sp[] = val.split(",");
                Sp[0] = Sp[0].toUpperCase();
                if(Sp[0].charAt(0) != 'D' && Sp[0].charAt(0) != 'B' && Sp[0].charAt(0) != 'A' || (Sp[0].length() >1)){
                    for(int i = 0;i<Sp[0].length();i++){
                        if(!Character.isDigit(Sp[0].charAt(i))){
                            System.out.println("\nERROR CARACTER INVALIDO EN EL SISTEMA NUMERICO");
                            System.out.println("Linea "+nLINE);
                            System.exit(0);
                        }
                    
                    }
                }
                
            }
            
        }
        else{
            String aux[] = val.split(",");
            try{
                return Integer.parseInt(aux[0]);
            }
            catch(Exception e){
                System.out.println("\nERROR");
                System.out.println("Linea "+nLINE);
                System.exit(0);
            }
        }
        return 0;
        }
        catch(Exception e){
            return 0;
        }
    }

    
    public static boolean comprobar(String Seg, int Pseg, int linea){
        //System.out.println(Seg +" " + Pseg);
        if(Pseg == 0){
            if(Seg.charAt(0) == ';'){
                if(Seg.length() > 80){
                    System.out.println("ERROR\n COMENTARIO MUY LARGO");
                    System.out.println("LINEA " + linea);
                    System.exit(0);
                    return false;
                }
                agrLinea= false;
            }
            else if(!((Seg.charAt(0) < 65 || Seg.charAt(0) > 90)  && (Seg.charAt(0) <97 || Seg.charAt(0) > 122))){
                if(Seg.length()>8){
                    System.out.println("ERROR\n ETIQUETA MUY LARGA");
                    System.out.println("LINEA " + linea);
                    System.exit(0);
                    return false;
                }
                for(int i = 1; i<Seg.length();i++){
                    if(!((Character.isLetter(Seg.charAt(i))) || (Character.isDigit(Seg.charAt(i))) || (Seg.charAt(i) == 95))){
                        System.out.println("ERROR\n LA ETIQUETA CONTIENE CARACTERES INVALIDOS");
                        System.out.println("LINEA " + linea);
                        System.exit(0);
                        return false;
                    }
                }
                etiqueta = Seg.toUpperCase();
            }
            else{
                System.out.println("ERROR");
                System.out.println("LINEA " + linea);
                System.exit(0);
                    return false;
            }
            
        }
        if(Pseg == 1){
            
            Directivas dr = new  Directivas();
            
            if(dr.direc1(Seg, etiqueta, operando, busVal(operando, linea), linea)){                     //comprobar las directivas 
                codigo=Seg;
                return true;
            }
            
            if(!((Character.isLetter(Seg.charAt(0))) || (Character.isDigit(Seg.charAt(0))))){
                System.out.println("ERROR\n EL CODOP CONTIENE CARACTERES INVALIDOS");
                System.out.println("LINEA " + linea);
                System.exit(0);
            }
            
            for(int i = 0;i<Seg.length();i++){
                if(Seg.charAt(i) != '.'){
                    if(!((Character.isLetter(Seg.charAt(i))) || (Character.isDigit(Seg.charAt(i))))){
                        System.out.println("ERROR\n EL CODOP CONTIENE CARACTERES INVALIDOS");
                        System.out.println("LINEA " + linea);
                        System.exit(0);
                    }
                }
                
            }
            
            if(Seg.length() > 5){
                System.out.println("ERROR\n EL CODOP ES MUY LARGO");
                System.out.println("LINEA " + linea);
                System.exit(0);
                return false;
            }
            boolean b = true;
            for(int i = 1; i<Seg.length();i++){
                if(Seg.charAt(i) == '.'){
                    if(b == false){
                        System.out.println("ERROR\n EL CODOP CONTIENE MAS DE UN PUNTO");
                        System.out.println("LINEA " + linea);
                        System.exit(0);
                        return false;
                    }
                    b=false;
                }
            }
            codigo = Seg.toUpperCase();
        }
        if(Pseg ==2){
            if("END".equals(codigo)){
                System.out.println("ERROR SE ENCONTRO UN OPERANDO DESPUES DE CODOP END");
                System.out.println("LINEA " + linea);
                
                System.exit(0);
                salir = true;
            }
            operando = Seg.toUpperCase();
        }
        if(Pseg>2){
            System.out.println(Seg);
            System.out.println("ERROR\n SE ENCONTRO ALGO DESPUES DEL OPERANDO");
            System.out.println("LINEA " + linea);
            System.exit(0);
        }
        return true;
    }
    
}
