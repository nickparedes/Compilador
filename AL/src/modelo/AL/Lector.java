/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.AL;


import java.util.ArrayList;
import modelo.Sistema;

/**
 *
 * @author axell
 */
public class Lector {
    private ArrayList<String> palabras;
    
    public void validarCodigo(String s){
        //Separamos todas las lineas
        String [] lines = s.split("\n");
        
        palabras = new ArrayList<String>(Sistema.buffers[1]);
        
        char c;
        int contador;
        boolean inicio_cadena = false;
        boolean inicio_comentario = false;
        String aux = "";
        for(int k=0; k<lines.length; k++){
            //System.out.println("Numero de lineas: " + lines.length);
            contador = 0;
            String [] p = Split.split(lines[k]);
            //String [] p = lines[k].split(" ");
            
            for(int j=0; j<p.length; j++){
                
                //Validacion para la palabra p[j]
                
                String palabra = p[j];
               //System.out.println("Validacion para la palabra: " + palabra);
                //Recorremos la palabra para dividirla en sus potenciales tokens
                for(int i=0; i<palabra.length(); i++){
                    //Analizamos caracter a caracter
                    
                    c = palabra.charAt(i);
                    
                    if(!inicio_cadena && !inicio_comentario){
                        //[A-Z] || [a-z] || [0-9] || _
                        if((c <= 90 && c >=65) || (c <= 122 && c >= 97) || (c <= 57 && c >= 48) || c == 95){
                            aux += c;
                            
                            contador++;
                            
                            //Validacion si la linea completa es una palabra
                            if(i == palabra.length()-1){
                                palabras.add(aux);
                                aux = "";
                                contador = 0;
                            }
                        }                    
                    
                        else{
                            if(contador > 0){
                                if(c == '.' && (palabra.charAt(i-1) <= 57 && palabra.charAt(i-1) >=48 )){
                                    aux += c;
                                    if(i == palabra.length()-1){
                                        
                                        palabras.add(aux);
                                        aux = "";
                                    }    
                                }
                                else {
                                    
                                    palabras.add(aux);
                                    contador = 0;
                                    aux = "";
                                }
                            }          
                            // c == "
                            if(c == '"'){
                                aux += c;
                                inicio_cadena = !inicio_cadena;
                                if(i == palabra.length()-1 ){
                                    palabras.add(aux);
                                    aux = "";
                                }

                            }
                            // c == %
                            else if(c == '%'){
                                aux += c;
                                inicio_comentario = !inicio_comentario;
                                if(k == lines.length -1 && palabra.length() - 1 == i && p.length - 1 == j){
                                    palabras.add(aux);
                                    aux = "";
                                }
                            }     
                            //c == =
                            else if(c == 61){
                                aux +=c;
                                
                                if(aux.equals("<") || aux.equals(">") || aux.equals("!")){
                                    aux += c;
                                    palabras.add(aux);
                                    aux = "";
                                }
                                
                                else if(i == palabra.length()-1 || palabra.charAt(i+1) != 61){
                                    contador = 0;
                                    palabras.add(aux);
                                    aux = "";
                                }
                            }
                            else if(c == '!'){
                                aux +=c;
                                //System.out.println("entramos: " + aux + " siguiente: " + palabra.charAt(i+1));
                                if(i == palabra.length()-1 || palabra.charAt(i+1) != '='){
                                    //System.out.println("entramos: " + aux);
                                    contador = 0;
                                    palabras.add(aux);
                                    aux = "";
                                }
                            }
                            /*
                            else if(c == '='){
                                aux += c;
                                System.out.println("entramos: " + aux);
                                if(i > 0 && palabra.charAt(i-1) == '!'){
                                    contador = 0;
                                    palabras.add(aux);
                                    aux = "";
                                }
                                else if(i == palabra.length()-1 || palabra.charAt(+1) != '='){
                                    contador = 0;
                                    palabras.add(aux);
                                    aux = "";
                                }
                            }*/
                            
                            else if(c == '+'){
                                aux +=c;
                                if(i == palabra.length()-1 || palabra.charAt(i+1) != '+'){
                                    contador = 0;
                                    palabras.add(aux);
                                    aux = "";
                                }
                            }
                            
                            else if(c == '-'){
                                aux +=c;
                                if(i == palabra.length()-1 || (palabra.charAt(i+1) != '-') ){
                                    contador = 0;
                                    palabras.add(aux);
                                    aux = "";
                                }
                            }
                            
                            else if(c == '*'){
                                aux +=c;
                                if(i == palabra.length()-1 || palabra.charAt(i+1) != 42){
                                    contador = 0;
                                    palabras.add(aux);
                                    aux = "";
                                }
                            }
                            //c == <
                            else if(c == 60){
                                aux +=c;
                                if(i == palabra.length()-1 || palabra.charAt(i+1) != 61){
                                    contador = 0;
                                    palabras.add(aux);
                                    aux = "";
                                }
                            }
                            // c == >
                            else if(c == '>'){
                                aux +=c;
                                if(i == palabra.length()-1 || palabra.charAt(i+1) != 61){
                                    contador = 0;
                                    palabras.add(aux);
                                    aux = "";
                                }
                            }else if(c == '.' && contador == 0){
                                if(i < palabra.length()-1 && palabra.charAt(i+1) <= 57 && palabra.charAt(i+1) >= 48){
                                    aux += c;
                                }else{
                                    palabras.add("" + c);
                                    aux = "";
                                }
                            }
                            else if(c != 10 && c != 32 && c!= 9 && c != '.'){
                                palabras.add("" + c);
                               
                            }


                        }
                    }
                    else{
                        //c == "
                        if(c == 34 && inicio_cadena){
                            inicio_cadena = !inicio_cadena;
                            aux += c;
                            palabras.add(aux);
                            aux = "";
                        }
                        //c == %
                        else if( c == 37 && inicio_comentario){
        
                            inicio_comentario = !inicio_comentario;
                            aux += c;
                            palabras.add(aux);
                            aux = "";
                        }
                        else if(inicio_comentario || k == lines.length-1){
                            
                            aux += c;
           
                            if(palabra.length()-1 == i && k<lines.length-1){
                                aux += " ";
                            }else if(j == p.length-1 && palabra.length()-1 == i){
                      
                                palabras.add(aux);
                            }
                        }
                        
                        
                        else if(j < p.length-1 && inicio_cadena){
                            
                            aux += c;

                            if(palabra.length()-1 == i){
                 
                                aux += " ";
                            }
                        }
                        

                        else if(j == p.length -1){
                            if(i < palabra.length() -1){
                                aux += c;
                            }
                            else {
                                aux += c;
                                
                                palabras.add(aux);
                                inicio_comentario = false;
                                inicio_cadena = false;
                                aux = ""; 
                            }
                            
                        }  
                    }                   
                }       
            }
        }  
        
        /*for(String ss: palabras)
            System.out.println(ss);*/

    }

    public ArrayList<String> getPalabras() {
        return palabras;
    }
    
    
    
    
}
