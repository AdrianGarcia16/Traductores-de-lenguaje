/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.pkg1;

import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author adria
 */
class LeerTABSIM {
    
    public String Buscar(String etq){ 
        try {
            Scanner input = new Scanner(new File("TABSIM.txt"));                 //abrir archivo con la ruta especificada (la ruta es)
            while (input.hasNextLine()) {
                String line = input.nextLine();                                 //guardar linea completa del documento                
                StringTokenizer st = new StringTokenizer(line, "\t");
                st.nextToken();
                if(st.nextToken().equals(etq)){
                    return st.nextToken();
                }
            }
            System.out.println("NO SE ENCONTRO LA ETIQUETA");
            System.exit(0);
            input.close();                                                      //cerrar archivo
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
