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
    
    public static void main(String[] args) {
        System.out.println("Instrucción" +
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
                            "bytes\t");
        Leer();                                                                 //llamamos al metodo leer para analizar el contenido del arvhivo
        
  
    }
    public static void Leer(){                                                  //metodo para leer el archivo
        try {
            Scanner input = new Scanner(new File("P2ASM.txt"));                 //abrir archivo con la ruta especificada (la ruta es)
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
            
            try {
                    Scanner input = new Scanner(new File("TABOP.txt"));                 //abrir archivo con la ruta especificada 
                    boolean bandera = true;
                    while (input.hasNextLine()){
                        String tabopL = input.nextLine();                                 //guardar linea completa del documento                
                        StringTokenizer stT = new StringTokenizer(tabopL, "\t");
                            if(codigo.equals(stT.nextToken())){
                                System.out.print(codigo);
                                int cont = 0, cont2 = 0;
                                while (stT.hasMoreTokens()) {
                                    String s = (String) stT.nextElement();
                                    if("Operando".equals(s)){
                                        System.out.print("\t\t\tSI\t");
                                        if(operando == null){
                                            System.out.println("\nERROR \nNO SE ENCONTRO OPERANDO");
                                            System.out.println("LINEA" + Nlin);
                                            System.exit(0);
                                        }
                                        cont++;
                                    }
                                    else if("No Operando".equals(s)){
                                        System.out.print("\t\t\tNO\t");
                                        if(operando != null){
                                            System.out.println("\nERROR \nSE ENCONTRO OPERANDO");
                                            System.out.println("LINEA" + Nlin);
                                            System.exit(0);
                                        }
                                        cont++;
                                    }
                                    else{
                                        switch (cont) {
                                            case 3:
                                                System.out.print("\t\t\t"+s+"    ");
                                                cont2 = Integer.parseInt(s);
                                                cont++;
                                                break;
                                            case 4:
                                                cont2= cont2 + Integer.parseInt(s);
                                                System.out.print("\t\t\t\t"+s+"    ");
                                                System.out.print("\t\t\t\t"+cont2+"    ");
                                                break;
                                            default:
                                                System.out.print("\t\t "+s+"    ");
                                                cont++;
                                                break;
                                        }
                                        
                                    }
                                    
                                }
                                System.out.println("");
                                
                                bandera = false;
                            }
                            else if(codigo.equals("END")){
                                bandera = false;
                                break;
                            }
                            else if(codigo.equals("ORG")){
                                bandera = false;
                                break;
                            }
                    }
                    if(bandera==true ){
                        System.out.println("ERROR");
                        System.out.println("No se encontro el CODOP");
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
                etiqueta = Seg;
            }
            else{
                System.out.println("ERROR \n LA ETIQUETA CONTIENE NO CUMPLE CON SUS CARACTERISTICAS");
                System.out.println("LINEA " + linea);
                System.exit(0);
                    return false;
            }
            
        }
        if(Pseg == 1){
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
            codigo = Seg;
        }
        if(Pseg ==2){
            if("END".equals(codigo)){
                System.out.println("ERROR SE ENCONTRO UN OPERANDO DESPUES DE CODOP END");
                System.out.println("LINEA " + linea);
                
                System.exit(0);
                salir = true;
            }
            operando = Seg;
        }
        if(Pseg>2){
            System.out.println(Seg);
            System.out.println("ERROR\n SE ENCONTRO ALG DESPUES DEL OPERANDO");
            System.out.println("LINEA " + linea);
            System.exit(0);
        }
        return true;
    }
    
}
