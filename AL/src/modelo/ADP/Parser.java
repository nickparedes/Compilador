package modelo.ADP;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import modelo.AL.Token;
import modelo.Sistema;
import modelo.util.Excel;
import modelo.AL.Atributo;
import modelo.HashTable;

public class Parser {
    Queue <Token> entrada;
    Stack<String> stack;
    TAS tabla;
    public Parser(Queue<Token> entrada){
        //System.out.println(entrada);
        this.entrada = entrada;
        stack = new Stack();
        tabla = new TAS();
    }

    public boolean analizar(){
        boolean bandera = false;
        Atributo sentencia = new Atributo(), tipo=new Atributo(), declaracion=new Atributo(), id=new Atributo(), asignacion=new Atributo(), operacion=new Atributo(), T=new Atributo(), R=new Atributo(), T1 = new Atributo(), R1 = new Atributo(), F = new Atributo(), operador = new Atributo();
        
        Sistema.producciones = new ArrayList<Object[]>();
        int contador = 0;
        String [] produccion = null;
        
        boolean error = false;
        //Inicializacion de los primeros componentes lexicos
        stack.push("$");
        stack.push("programa");
        
        //cimaPila apunta al primer componenete léxico
        String cimaPila = stack.getLast(), primero;
        Token bufferEntrada;
        //Hasta que X = $
        while(!(stack.getLast()).equalsIgnoreCase("$") && !error){
            contador++;
            Object []salida = new Object[4];
            bufferEntrada = entrada.getFirst();
            primero = entrada.getFirst().getMask();
            
            salida[0] = contador;
            salida[1] = "" + this.stack.toString();
            salida[2] = "" + entrada.toString();
            cimaPila =(String) stack.pop();
            
            //Si X pertene a los Vt
            if(cimaPila.equals(primero)){
                salida[3] = "";
                
                /*
                sacar X de la Pila está implicito ya
                que cada vez que le pedimos un elemento a la Pila este 
                ya sale de la Pila
                */
                //Avanzar sim
                entrada.dequeue();
            
            //Sino si M[X, a] = --> Y1, Y2 ... Yk
            }else{
                
                String p;
                
                try{
                    p = tabla.getProduction(cimaPila, primero);
                    /*
                    sacar X de la Pila está implicito ya
                    que cada vez que le pedimos un elemento a la Pila este 
                    ya sale de la Pila
                    */
                    
                    produccion = p.split("\\|");
                    salida[3] = "";
                    
                    //meter Yk Yk-1 ... Y1 con Yi en la cima
                    for(int i=0; i<produccion.length; i++){
                        salida[3] += produccion[i] + "  ";
                    }
                    for(int i=produccion.length-1 ; i>=0 && !produccion[i].equalsIgnoreCase("&"); i--)
                    stack.push(produccion[i]);
                /*
                    En caso no pueda lograr esto se producira una excepcion con lo cual no es necesario
                    crear una condicional más como está en el algoritmo, sino solo debemos tratar la 
                    Excepcion generada.
                    */
                
                //Este bloque catch simboliza:
                //sino Error
                }catch(Exception e){
                    error = true;
                    //System.out.println(e);
                   // e.printStackTrace();
                }
                
                
            }
            
            String data = stack.last.data;
            
            try{
                if(bufferEntrada.getTipo().equals("TIPO_DATO") || bufferEntrada.getTipo().equals("TIPO_DEFINIDO")){
                    tipo.setTipo(bufferEntrada.getLexema());
                }else if(bufferEntrada.getLexema().equals(";")){
                    sentencia = new Atributo(); 
                    tipo=new Atributo(); 
                    declaracion=new Atributo();
                    id=new Atributo();
                    asignacion=new Atributo();
                    operacion=new Atributo();
                    T=new Atributo();
                    R=new Atributo();
                    T1 = new Atributo();
                    R1 = new Atributo();
                    F = new Atributo();
                    operador = new Atributo();
                }else if(bufferEntrada.getTipo().equals("ID") && tipo.getTipo().equals("ND")){
                    HashTable.Nodo identificador = Sistema.tablaSimbolos.searchDato(bufferEntrada.getLexema() + bufferEntrada.getNivel().split("\\.")[0]);
                    if(identificador == null){
                        JOptionPane.showMessageDialog(null, "Variable no declarada");
                        error = true;
                    }
                    tipo.setTipo(identificador.getDato().getTipo());
                }else if(bufferEntrada.getTipo().equals("ID") && !tipo.getTipo().equals("ND") ){
                    //System.out.println("Entramos");
                    HashTable.Nodo identificador = Sistema.tablaSimbolos.searchDato(bufferEntrada.getLexema() + bufferEntrada.getNivel().split("\\.")[0]);
                    if(identificador == null || !bufferEntrada.compararNivel(identificador.getDato().getNivel())){
                        JOptionPane.showMessageDialog(null, "Variable no declarada");
                        error = true;
                    }
                    //System.out.println("id que genera: "+identificador.getDato().getIdentificador());
                    if(identificador.getDato().getAcceso().equals("private") && operador.getTipo().equals("miembro")){
                        JOptionPane.showMessageDialog(null, "Error de acceso");
                        error = true;
                    }else{
                        
                    }
                    String type = identificador.getDato().getTipo();
                    System.out.println(entrada.first.next.data.getLexema());
                    if(tipo.getTipo().equals(identificador.getDato().getTipo() )){
                        
                    }else if(!tipo.getTipo().equals(identificador.getDato().getTipo()) && entrada.first.next.data.getLexema().equals(".")){
                        
                    }else if(!tipo.getTipo().equals(identificador.getDato().getTipo())){
                        JOptionPane.showMessageDialog(null, "Error de asignacion");
                        error = true;
                    }
                }else if(bufferEntrada.getTipo().equals("CONSTANTE_ENTERO")){
                    if(tipo.getTipo().equals("integer") || tipo.getTipo().equals("flot")){
                        
                    }else if(tipo.getTipo().equals("String") && operador.getTipo().equals("cadena")){
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Error de asignacion");
                        error = true;
                    }
                }else if(bufferEntrada.getTipo().equals("CONSTANTE_REAL")){
                    if(tipo.getTipo().equals("flot") || tipo.getTipo().equals("integer")){
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Error de asignacion");
                        error = true;
                    }
                }else if(bufferEntrada.getLexema().equals("TRUE") || bufferEntrada.getLexema().equals("FALSE")){
                    if(tipo.getTipo().equals("boolean")){
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Error de asignacion");
                        error = true;
                    }
                }else if(bufferEntrada.getLexema().equals("\"")){
                    
                    if(tipo.getTipo().equals("String")){
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Error de asignacion");
                        error = true;
                    }
                }else if(validar("\\+|-|\\*|/|\\*\\*", bufferEntrada.getLexema())){
                    
                    if(tipo.getTipo().equals("integer") || tipo.getTipo().equals("flot")){
                        operador.setTipo("aritmetico");
                    }else if(tipo.getTipo().equals("String")){
                        operador.setTipo("cadena");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Error de asignacion");
                        error = true;
                    }
                }else if(validar("!|not|and|or", bufferEntrada.getLexema())){
                    if(tipo.getTipo().equals("boolean")){
                        operador.setTipo("logico");
                    }else{
                        JOptionPane.showMessageDialog(null, "Error de asignacion");
                        error = true;
                    }
                }else if(bufferEntrada.getTipo().equals("OP_MIEMBRO")){
                   
                    operador.setTipo("miembro");
                }
            /*if(data.equals("sentencia")){
                if(bufferEntrada.getTipo().equals("TIPO_DATO")){
                    sentencia.setTipo(bufferEntrada.getLexema());
                }else if(bufferEntrada.getTipo().equals("ID")){
                    System.out.println(bufferEntrada.getLexema());
                    System.out.println("Datos de las hash");
                    Sistema.tablaSimbolos.imprimirDatos();
                    HashTable.Nodo identificador = Sistema.tablaSimbolos.searchDato(bufferEntrada.getLexema() + bufferEntrada.getNivel().split("\\.")[0]);
                    if(identificador != null && bufferEntrada.compararNivel(identificador.getDato().getNivel())){
                        System.out.println("lexeman buscado  " + bufferEntrada.getLexema() + bufferEntrada.getNivel().split("\\.")[0]+ "   tipo dato del identificador: ");
                    }else{
                        JOptionPane.showMessageDialog(null, "Identificador no declarado");
                        error = true;
                    }
                    
                    if(identificador == null){
                        System.out.println("No lo encontramos");
                    }
                    
                    //sentencia.setTipo(identificador.getDato().getTipo());
                    
                }
                
                //System.out.println("atributo rotado " + sentencia.getTipo());
            }else if(bufferEntrada.getTipo().equals("TIPO_DATO") && tipo.getTipo().equals("ND")){
                System.out.println("bien hecho");
                //tipo pasa a ser integer, flot, boolean o String
                tipo.setTipo(sentencia.getTipo());
                declaracion.setTipo(sentencia.getTipo());
                System.out.println("atributo tipo: " + tipo.getTipo());
                //====================================
            }else if(bufferEntrada.getTipo().equals("TIPO_DATO") && !tipo.getTipo().equals("ND")){
                System.out.println("Uso de la tabla hash");
                HashTable.Nodo identificador = Sistema.tablaSimbolos.searchDato(bufferEntrada.getLexema() + bufferEntrada.getNivel().split("\\.")[0]);
                tipo.setTipo(identificador.getDato().getTipo());
            }
            else if(bufferEntrada.getTipo().equals("ID") && operacion.getTipo().equals("")){
                id.setTipo(declaracion.getTipo());
                asignacion.setTipo(declaracion.getTipo());
                
            }else if(bufferEntrada.getTipo().equals("CONSTANTE_ENTERO") ){
                if(tipo.getTipo().equals("integer") || tipo.getTipo().equals("flot")){
                    operacion.setTipo(asignacion.getTipo());
                }else{
                    JOptionPane.showMessageDialog(null, "Error de asignacion");
                    error = true;
                }
            }else if(bufferEntrada.getTipo().equals("CONSTANTE_REAL")){
                if(tipo.getTipo().equals("flot")){
                    operacion.setTipo(asignacion.getTipo());
                }else{
                    JOptionPane.showMessageDialog(null, "Error de asignacion");
                    error = true;
                }
            }else if(bufferEntrada.getLexema().equals("TRUE") || bufferEntrada.getLexema().equals("FALSE")){
                if(tipo.getTipo().equals("boolean")){
                    operacion.setTipo(asignacion.getTipo());
                }else{
                    JOptionPane.showMessageDialog(null, "Error de asignacion");
                    error = true;
                }
            }else if(bufferEntrada.getLexema().equals("\"")){
                if(tipo.getTipo().equals("String")){
                    operacion.getTipo().equals(asignacion.getTipo());
                    T.setTipo(operacion.getTipo());
                    R.setTipo(operacion.getTipo());
                }else{
                    JOptionPane.showMessageDialog(null, "Error de asignacion");
                    error = true;
                }
                
            }else if(bufferEntrada.getTipo().equals("OPERADOR")){
                if(R.getTipo().equals("integer") || R.getTipo().equals("flot")){
                    //Validacion para operadores aritmeticos
                    if(validar("\\+|-|\\*|/|\\*\\*", bufferEntrada.getLexema())){
                        operador.setTipo("aritmetico");
                        T1.setTipo(R.getTipo());
                        R1.setTipo(R.getTipo());
                    }else{
                        JOptionPane.showMessageDialog(null, "Error de asignacion");
                        error = true;
                    }
                }else if(R.getTipo().equals("String")){
                    //Validarion para operadores de cadena
                    if(validar("\\+|\\*", bufferEntrada.getLexema())){
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Error de asignacion");
                        error = true;
                    }
                }else if(operacion.getTipo().equals("boolean")){
                    if(bufferEntrada.getLexema().equals("!")){
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Error de asignacion");
                        error = true;
                    }
                    
                }
            }else if(bufferEntrada.getLexema().equals(";")){
                sentencia = new Atributo(); 
                tipo=new Atributo(); 
                declaracion=new Atributo();
                id=new Atributo();
                asignacion=new Atributo();
                operacion=new Atributo();
                T=new Atributo();
                R=new Atributo();
                T1 = new Atributo();
                R1 = new Atributo();
                F = new Atributo();
                operador = new Atributo();
            }*/
            }catch(Exception e){
                
            } 
           
        Sistema.producciones.add(salida);
        
        }
        String []salida = new String[4];
        salida[0] = "" + (contador+1);
        salida[1] = "" + this.stack;
        salida[2] = "" + entrada;
        salida[3] = stack.getLast().equalsIgnoreCase(entrada.getFirst().getMask()) ? "Aceptacion" : "No aceptacion";
        Sistema.producciones.add(salida);
                
        return stack.getLast().equalsIgnoreCase("$") && entrada.getFirst().getLexema().equalsIgnoreCase("$");
    }
    
    public void setTas(String path){
        Excel xlsx = new Excel("TAS", new File("C:\\Users\\axell\\Desktop"));
        tabla.setMatrix(xlsx.cargarExcel(path));
        
    }
    
    private static boolean validar(String regex, String operador){
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(operador);
        if(mat.matches()){
            return true;
        }
        return false;
    }
    
    public static void main(String [] args){
        System.out.println(validar("\\+|-|\\*|/||\\+\\+||--||\\*\\*", "--"));
    }
    
    
    

}
