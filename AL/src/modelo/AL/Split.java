package modelo.AL;


import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author axell
 */
public class Split {
    public static String [] split(String s){
        String [] result = null;
        boolean cadena = false;
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i) == '"'){
                cadena = true;
                break;
            }
        }
        if(cadena){
            ArrayList<String> palabras  = new ArrayList<String>();
            String anterior = "";
            String actual = "";
            boolean modocadena = false;
            for(int i=0; i<s.length(); i++){
                char c = s.charAt(i);
                anterior = actual;
                actual += c;
                //System.out.println(actual);
                if(c == '"' || (modocadena && i == s.length()-1)){
                    modocadena = !modocadena;
                    //actual += c;
                    //System.out.println(i == s.length()-1);
                    if(modocadena == false || i == s.length()-1){
                        
                        palabras.add(actual);
                        //System.out.println(actual);
                        actual = "";
                    }
                }
                else if(c == ' ' && modocadena == false){
                    palabras.add(anterior);
                    //System.out.println(anterior);
                    actual = "";
                    
                }else if(i == s.length()-1){
                    //System.out.println(actual);
                    palabras.add(actual);
                }
            }
            result = new String[palabras.size()];
            for(int i=0; i<palabras.size(); i++){
                result[i] = palabras.get(i);
            }
        }else{
            result = s.split(" ");
        }
        return result;
    }
}
