/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.AL;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ADP.Queue;
import modelo.HashTable;
import modelo.Sistema;


/**
 *
 * @author axell
 */
public class AFD {
    
    private ArrayList<Token> tokens = new ArrayList<Token>();
    private Queue<Token> colaTokens = new Queue<Token>();
    
    public void validarPalabras(ArrayList<String> palabras){
        Sistema.tablaSimbolos = new HashTable(524288);
        tokens = new ArrayList<Token>(palabras.size());
        
        String lexema;
        Token t;
        boolean bandera;
        int estado, contador_op, c_suma, c_resta, c_multi;
        for(int i=0; i<palabras.size(); i++){
            estado = 0; 
            contador_op = 0; 
            c_suma = 0; 
            c_resta = 0; 
            c_multi = 0;
            
            //Obtenemos la palabra i
            lexema = palabras.get(i);
            
            bandera = false;
            //for para ver si es reservada
            for(int j=0; j<Sistema.reservadas.length; j++){
           
                if(lexema.equals(Sistema.reservadas[j])){
                    
                    estado = 1;
                    bandera = true;
                    
                    if(lexema.equals("mod") || lexema.equals("or") || lexema.equals("and") || lexema.equals("not")){
                        estado = 3;
                    }else if(lexema.equals("to")){
                        estado = 8;
                        
                    }else if(lexema.equals(Sistema.reservadas[3]) || lexema.equals(Sistema.reservadas[1]) || lexema.equals(Sistema.reservadas[2]) || lexema.equals(Sistema.reservadas[0])){
                        //Estado para TIPO_DATO
                        estado = 27;
                    }
                    
                    
                }
            }
            //Si no es reservada
            if(!bandera){
                char c;
                for(int k=0; k<lexema.length(); k++){
                    c = lexema.charAt(k);
                    
                    if(c >= 'A' && c <='Z' || c >= 'a' && c<='z' || c == '_'){
                        switch(estado){
                            case 0: estado = 2;break;
                            case 2: estado = 2; break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            default: estado = 24; break;
                        }
                            
                    }
  
                    else if(c == ';'){
                        switch(estado){
                            case 0: estado = 26; break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            default: estado = 24;
                        }
                    }
                    
                    
                    
                    else if(c == '='){
                        switch(estado){
                            case 0: estado = 4; break;
                            case 3: estado = 24; break;
                            case 4: 
                                estado = 3; 
                                //contador_op++;
                                break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            default: estado = 24;
                        }
                    }
                    
                    
                    else if(c == '+'){
                        switch(estado){
                            case 0: 
                                estado = 3;
                                c_suma++;
                                break;
                            case 3:
                                if(c_suma == 1){
                                    estado = 3;
                                    c_suma++;
                                }else{
                                    estado = 24;
                                }
                                break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;    
                            default: estado = 24;
                        }
                        
                    }else if(c == '-'){
                        switch(estado){
                            case 0: 
                                estado = 3;
                                c_resta++;
                                break;
                            case 3:
                                if(c_resta == 1){
                                    estado = 3;
                                    c_resta++;
                                }else{
                                    estado = 24;
                                }
                                break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;    
                            default: estado = 24;
                        }
                    }else if(c == '*'){
                        switch(estado){
                            case 0: 
                                estado = 3;
                                c_multi++;
                                break;
                            case 3:
                                if(c_multi == 1){
                                    estado = 3;
                                    c_multi++;
                                }else{
                                    estado = 24;
                                }
                                break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;    
                            default: estado = 24;
                        }
                    }
                    
                    
                    
                    else if(c == '/' || c == '>' || c == '<' || c == ':' || c == '.' || c == '#' || c == ',' || c == '!'){
                        switch(estado){
                            case 0: if(c == '.') estado = 5; else estado = 3; break;
                            case 3: estado = 24; break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 18: if(c == '.') estado  = 19; else estado = 24; break;
                            case 19: estado = 24; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            case 25: if(c == '.') estado = 19; else estado = 24; break;
                            default: estado = 24;
                            
                        }
                    }
                    
                    
                    else if(c == '('){
                        switch(estado){
                            case 0: estado = 9; break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            default: estado = 24;
                        }
                    }
                    
                    else if(c == ')'){
                        switch(estado){
                            case 0: estado = 10; break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            default: estado = 24;
                        }
                    }
                    else if(c == '['){
                        switch(estado){
                            case 0: estado = 11; break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            default: estado = 24;
                        }
                    }
                    
                    else if(c == ']'){
                        switch(estado){
                            case 0: estado = 12; break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            default: estado = 24;
                        }
                    }
                    else if(c == '{'){
                        switch(estado){
                            case 0: estado = 13; break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            default: estado = 24;
                        }
                    }
                    
                    else if(c == '}'){
                        switch(estado){
                            case 0: estado = 14; break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            default: estado = 24;
                        }
                    }else if(c == '"'){
                        switch(estado){
                            case 0:estado = 15; break;
                            case 15:estado = 17;break;
                            case 16:estado = 17;break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            default: estado = 24;
                        }
                        
                        
                    }
                    else if(c == '0'){
                        switch(estado){
                            case 0: estado = 25; break;
                            case 2: estado = 2; break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 19: estado = 19; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            case 25: estado = 24; break;
                            default: estado = 24;
                        }
                        
                    }
                    
                    
                    else if(c >= '1' && c <= '9'){
                        switch(estado){
                            case 0: estado = 18; break;
                            case 2: estado = 2; break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 18: estado =18; break;
                            case 19: estado = 19; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            case 25: estado = 24; break;
                            default: estado = 24; break;
                        }
                    }
                    
                    else if( c == '%'){
                        switch(estado){
                            case 0: estado = 20;break;
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 21;break;
                            case 23: estado = 21;break;
                            default: estado = 24;
                        }
                    }else if(c == ' '){
                        switch(estado){
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            default: estado = 24;
                        }
                    }else if(c == 9){
                        switch(estado){
                            case 15: estado = 16; break;
                            case 16: estado = 16; break;
                            case 20: estado = 23; break;
                            case 23: estado = 23; break;
                            default: estado = 24;
                        }
                    }
                    else{
                        if(estado != 16 && estado != 15)
                            estado = 24;
                    }
                    //System.out.println("Lexema a validar: " + lexema + "Caracter: " + c + "   estado: " + estado);
                }
            }
            
            
            if(estado != 24){
                if(estado != 15 && estado != 17 && estado != 16 && estado != 21 && estado != 20 && estado != 23 ){
                    t = new Token(lexema, estado);
                    tokens.add(t);
                    colaTokens.enqueue(t);
                }else if(estado < 20){
                    t = new Token("\"", 15);
                    tokens.add(t);
                    colaTokens.enqueue(t);
                    if( estado == 16 || estado == 17){
                        try{
                            t = new Token(lexema.split("\"")[1], 16);
                            tokens.add(t);
                            colaTokens.enqueue(t);
                        }catch(Exception e){
                            //System.out.println("Se capturo la excepcion cuando la cadena esta vacia");
                        }
                        
                        
                        if(estado == 17){
                            t = new Token("\"", 17);
                            tokens.add(t);
                            colaTokens.enqueue(t);
                        }
                    }
                }else{
                    t = new Token(lexema, 23);
                    tokens.add(t);
                    colaTokens.enqueue(t);
                }  
            }else{
                //System.out.println("Modo panico");
                t = new Token(lexema, estado);
                if(isGramatic(lexema))
                    modoPanico(t);
                else{
                    tokens.add(t);
                    colaTokens.enqueue(t);
                    //JOptionPane.showMessageDialog(null, "Tokens invalidos");
                    System.err.println("Token invalido");
                }
            }
        }
        
                
                for(int i=0; i<tokens.size(); i++){
                    try{
                        
                        if(tokens.get(i-1).getLexema().equals("Abstract") && tokens.get(i).getTipo().equals("ID")){
                            Token t3 = tokens.get(i);
                            tokens.get(i).setTipo("TIPO_DEFINIDO");
                            for(int j= i+1; j<tokens.size(); j++){
                                Token t2 = tokens.get(j);
                                if(t2.getLexema().equals(t3.getLexema())){
                                    t2.setTipo("TIPO_DEFINIDO");
                                }
                            }
                        }
                    }catch(Exception ex){
                    }
                    //for(int j=0; j<Sistema.columns.length; j++){
                        
         
                   // }
                }
        
        
        addHashTable();
        Sistema.entrada = colaTokens;
        
    /*for(int i=0; i<tokens.size(); i++){
        System.out.println(tokens.get(i));
    } */   
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    
    public  void modoPanico(Token t){
        //System.out.println("Modo Panico");
        String p = t.getPalabra();
        String actual = "";
        String anterior;
        char c = '\0';
        int contador = 0;
        boolean isC = false, isN = false;
        for(int i=0; i<p.length(); i++){
            c = p.charAt(i);
            anterior = actual;
            actual += c;
            
            if(c >= 97  && c <= 122 || c >= 65 && c <= 90 || c == '_'){
                isC = true;
                if(isN){
                   if(!isReal(actual)){
                       int e = 19;
                       if(isEntero(anterior))
                           e = 18;
                        Token t1 = new Token(anterior, e);
                        tokens.add(t1);
                        colaTokens.enqueue(t1);
                        actual = "" + c;
                        isN = false;
                    } 
                }
                
                for(String s: Sistema.reservadas){
                    if(s.equals(actual)){
                        Token t1 = new Token(actual, 1);
                        this.tokens.add(t1);
                        colaTokens.enqueue(t1);
                        actual = "";
                    }
                }
                if(i == p.length() -1 && actual.length() > 1){
                    Token t1 = new Token(actual, 2);
                    this.tokens.add(t1);
                    colaTokens.enqueue(t1);
                }
                
                
                
            }
            else if(c== '=' || c == '+' || c == '-' || c == '*' || c == '/' || c == '<' || c == '>' || c == '!'){
                contador++;
                if(contador == 2){
                    Token t1 = new Token(actual, 3);
                    this.tokens.add(t1);
                    colaTokens.enqueue(t1);
                    actual = "";
                    contador = 0;
                }
            }
            else if(c >= 48 && c <= 57 || c == '.'){
                isN = true;
                if(isC){
                    Token t1 = new Token(anterior, 2);
                    this.tokens.add(t1);
                    colaTokens.enqueue(t1);
                    actual = "" + c;
                    isC = false;
                    
                }else{
                    if(!isReal(actual) || i == p.length()-1){
                        if(!isReal(actual) && anterior.length() > 0){
                            Token t1 = new Token(anterior, 19);
                            this.tokens.add(t1);
                            colaTokens.enqueue(t1);
                            actual = "" + c;
                            isN = false;
                            try{
                                if((p.charAt(i+1) >= 'a' && p.charAt(i+1) <= 'z') || p.charAt(i+1) >= 'A' && p.charAt(i+1) <= 'Z'){
                                    Token t2 = new Token(actual, 5);
                                    this.tokens.add(t2);
                                    colaTokens.enqueue(t2);
                                    actual = "";
                                }
                            }catch(Exception e){
                                
                            }
                        }else if(i == p.length()-1){
                            //actual = "" + c;
                            int e = 19;
                            if(isEntero(actual))
                                e = 18;
                            Token t1 = new Token(actual, e);
                            //System.out.println("Se agrego: " + actual + "cuando: " + (i == p.length()-1));
                            this.tokens.add(t1);
                            colaTokens.enqueue(t1);
                            actual = "";
                        }
                        
                    }
                    
                }
            }    
        }
        if(actual.length() == 1)
            if(c == '.'){
                Token t1 = new Token("" + c, 5);
                this.tokens.add(t1);
                colaTokens.enqueue(t1);
            }else if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_'){
                Token t1 = new Token("" + c, 2);
                this.tokens.add(t1);
                colaTokens.enqueue(t1);
            }else if(c >= '0' && c <= '9'){
                Token t1 = new Token("" + c, 18);
                this.tokens.add(t1);
                colaTokens.enqueue(t1);
            }else if(c== '=' || c == '+' || c == '-' || c == '*' || c == '/' || c == '<' || c == '>'){
                int estado = 3;
                if(c == '=')
                    estado = 4;
                Token t1 = new Token("" + c, estado);
                this.tokens.add(t1);
                colaTokens.enqueue(t1);
            }
        
    }
    
    private boolean isReal(String s){
        int estado = 0;
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(c >= 49 && c <= 57){
                switch(estado){
                    case 0: estado = 1; break;
                    case 1: estado = 1; break;
                    case 3: estado = 3; break;
                    case 4: estado = 3; break;
                    default: estado = 5;
                }
            }else if(c == '0'){
                switch(estado){
                    case 0: estado = 2; break;
                    case 1: estado = 1; break;
                    case 3: estado = 3; break;
                    case 4: estado = 3; break;
                    default: estado = 5;
                }
            }else if(c == '.'){
                switch(estado){
                    case 0: estado = 4; break;
                    case 1: estado = 3; break;
                    case 2: estado = 3; break;
                    case 3: estado = 5; break;
                    default: estado = 5;
                }
            }else{
                estado = 5;
            }
        }
        return estado == 3 || estado == 1 || estado == 2;
    }
    public boolean isEntero(String s){
        int estado = 0;
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(c >= 49 && c <= 57){
                switch(estado){
                    case 0: estado = 1; break;
                    case 1: estado = 1; break;
                    default: estado = 5;
                }
            }else if(c == '0'){
                switch(estado){
                    case 0: estado = 3; break;
                    case 1: estado = 1; break;
                    default: estado = 4;
                }
            
            }else{
                estado = 5;
            }
        }
        return estado == 3 || estado == 1;
    }
    
    private void setNiveles(){
        int funcion = 0;
        int nivel = 0;
        int linea = 0;
        for(Token e: tokens){
            String lexema = e.getLexema();
            if(lexema.equals("func") || lexema.equals("Abstract") || lexema.equals("main")){
                funcion++;
            }else if(lexema.equals("{")){
                nivel++;
            }else if(lexema.equals("}")){
                nivel--;
            }
                linea++;
            
            
            e.setNivel(funcion + "." + nivel + "." + linea);
            
            
        }
    }
    
    public void addHashTable(){
        
        setNiveles();
        boolean error = false;
        Token ant1 = null, ant2 = null, ant3 = null;
        String acceso = "", atributo = "";
        for(int i=0; i<tokens.size() ; i++){
            try{
            //System.out.println(acceso + " aja " + atributo);
           if(tokens.get(i).getLexema().equals("public") || tokens.get(i).getLexema().equals("private") || tokens.get(i).getLexema().equals("protected")){
               acceso = tokens.get(i).getLexema();
           }
           
           if(tokens.get(i).getLexema().equals("static")){
               atributo = tokens.get(i).getLexema();
           }
            
            if(tokens.get(i).getTipo().equals("TIPO_DEFINIDO") && tokens.get(i-1).getLexema().equals("Abstract")){
                String herencia = "";
                if(tokens.get(i+2).getTipo().equals("ID")){

                        HashTable.Nodo identificador = Sistema.tablaSimbolos.searchDato(tokens.get(i+1).getLexema() + tokens.get(i+2).getNivel().split("\\.")[0]);
                        if(identificador != null){
                            herencia = identificador.getDato().getIdentificador();
                        }else{
                            JOptionPane.showMessageDialog(null, "Herencia no declarada");
                        }
                   
                }
                if(Sistema.tablaSimbolos.addDato("Abstract", tokens.get(i).getLexema(), herencia, "", tokens.get(i).getNivel())){
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Clase ya definida");
                }
            }
            //System.out.println("Valor boolean: " + tokens.get(i).getTipo().equals("TIPO_DEFINIDO") + "       TIPO: " + tokens.get(i).getTipo() );
            if(tokens.get(i).getTipo().equals("TIPO_DATO") || tokens.get(i).getTipo().equals("TIPO_DEFINIDO")){
                ant1 = tokens.get(i);
          
            }
            
            else if(tokens.get(i).getTipo().equals("ID") && (ant1.getTipo().equals("TIPO_DATO") || ant1.getTipo().equals("TIPO_DEFINIDO"))){
                ant2 = tokens.get(i);
                if(Sistema.tablaSimbolos.addDato(ant1.getLexema(), ant2.getLexema() + ant2.getNivel().split("\\.")[0], atributo, acceso, ant2.getNivel())){
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Variable ya definida");
                }
            }else if(tokens.get(i).getLexema().equals(";") || tokens.get(i).getLexema().equals(")") || tokens.get(i).getLexema().equals("=")){
                ant1 = null;
                ant2 = null;
                ant3 = null;
                acceso = "";
                atributo = "";
            }
            
        }
        catch(Exception e){
                
                }
        
    }}
    
    
    
    public DefaultTableModel getTabla(){
        DefaultTableModel df = null;
        Object [] columns = {"TIPO ","NOMBRE", "ATRIBUTO", "ACCESO", "NIVEL"};
        Object [][]data;
        
        ArrayList<Object[]> aux = new ArrayList<Object[]>();
        setNiveles();
        for(int i=0; i<tokens.size(); i++){
            boolean agregado = false;
            Token ant1 = null, ant2 = null, ant3 = null;
            if(i > 0){
                ant1 = tokens.get(i-1);
            }
            
            if(i > 1){
                ant2 = tokens.get(i-2);
            }
            
            if(i > 2){
                ant3 = tokens.get(i-3);
            }
            Token t = tokens.get(i);
            
            for(int j=0; j<aux.size(); j++){
                String aux2 = (String) aux.get(j)[1];
                //System.out.println(aux2 + "  ==  " + t.getLexema());
                if(aux2.equals(t.getLexema())){
                    agregado = true;
                    break;
                }
            }
         
            if(ant1 != null){

                if((!agregado || i == 0) && (t.getTipo().equals("ID") && ant1 != null && (ant1.getTipo().equals("TIPO_DATO") || ant1.getTipo().equals("TIPO_DEFINIDO")))){

                    Object [] row = {ant1.getLexema(), t.getPalabra(), ant2 != null && (ant2.getLexema().equals("static")) ? ant2.getLexema() : "", ant3 != null && (ant3.getLexema().equals("private") || ant3.getLexema().equals("public") || ant3.getLexema().equals("protected")) ? ant3.getLexema() : "", t.getNivel()};
                    aux.add(row);

                    //Sistema.tablaSimbolos.addDato(ant1.getLexema(), t.getPalabra(), ant2 != null && (ant2.getLexema().equals("static")) ? ant2.getLexema() : "", ant3 != null && (ant3.getLexema().equals("private") || ant3.getLexema().equals("public") || ant3.getLexema().equals("protected")) ? ant3.getLexema() : "", t.getNivel());
                }

                if((!agregado || i == 0) && (t.getTipo().equals("TIPO_DEFINIDO") && ant1 != null && (ant1.getLexema().equals("Abstract")))){
                    Object [] row = {ant1.getLexema(), t.getPalabra(), ant2 != null && (ant2.getLexema().equals("static")) ? ant2.getLexema() : "", ant3 != null && (ant3.getLexema().equals("private") || ant3.getLexema().equals("public") || ant3.getLexema().equals("protected")) ? ant3.getLexema() : "", t.getNivel()};
                    aux.add(row);

                    //Sistema.tablaSimbolos.addDato(ant1.getLexema(), t.getPalabra(), ant2 != null && (ant2.getLexema().equals("static")) ? ant2.getLexema() : "", ant3 != null && (ant3.getLexema().equals("private") || ant3.getLexema().equals("public") || ant3.getLexema().equals("protected")) ? ant3.getLexema() : "", t.getNivel());
                }
            }
            
        }
        
        data = new Object[aux.size()][3];
        for(int i=0; i<data.length; i++){
            data[i] = aux.get(i);
        }
        df = new DefaultTableModel(data, columns);
        Sistema.tablaSimbolos.imprimirDatos();
        return df;
    }


    
    private boolean isGramatic(String palabra){
        boolean result = false;
        for(int i=0; i<palabra.length(); i++){
            char c = palabra.charAt(i);
            if((c >= 48 && c <= 57 || c == '.' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c== '=' || c == '+' || c == '-' || c == '*' || c == '/' || c == '<' || c == '>' || c == '_')){
                result = true;
                break;
            }
        }
        
        return result;
    }
    
    
    
    
}
