/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;


import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import modelo.ADP.Parser;
import modelo.AL.AFD;
import modelo.AL.Lector;
import modelo.Sistema;
import modelo.AL.Token;
import vista.SymbolTable;
import vista.Vista;
import vista.VistaProducciones;

/**
 *
 * @author axell
 */
public class CtrlVista {
    private final Vista vs;
    private DefaultTableModel dt;
    private AFD afd;
    
    public CtrlVista(Vista vs){
        this.vs = vs;
        propiedades();
        eventos();
    }
    
    private void propiedades(){
        vs.setVisible(true);
        vs.setLocationRelativeTo(null);
        vs.setSize(1000, 700);
        vs.setTitle("Proyecto - Lenguajes y Compiladores");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            
        } catch (InstantiationException ex) {
            
        } catch (IllegalAccessException ex) {
           
        } catch (UnsupportedLookAndFeelException ex) {
            
        }
        
        
    }
    
    private void eventos(){
        EJFileChooser();
        EValidar();
        EVerTable();
        ESintactico();
        EVerTAS();
    }
    
    private void EVerTAS(){
        vs.TAS.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    Desktop.getDesktop().open(new File("./TAS.xlsx"));
                } catch (IOException ex) {
                    
                }
            }
        });
    }
    
    private void EVerTable(){
        vs.tokens.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    CtrlSymbolTable ct = new CtrlSymbolTable(new SymbolTable(), afd.getTabla());
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Error al cargar la tabla de simbolos");
                }
                
            }
            
        });
    }
    
    private void EJFileChooser(){
        vs.jf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                int op = jf.showOpenDialog(vs);
                File file;
                if(op == JFileChooser.APPROVE_OPTION){
                    file = jf.getSelectedFile();
                    try{
                        FileReader f = new FileReader(file);
                        BufferedReader bf = new BufferedReader(f);
                        String s, texto = "";
                        while((s = bf.readLine()) != null){
                            texto += s + "\n";
                        }

                        vs.textArea.setText(texto);
                    }catch(FileNotFoundException ex){
                        System.err.println(ex);
                    } catch (IOException ex) {
                        System.err.println(ex);
                    }
                    

                }
            }
        });
    }
    
    private void EValidar(){
        vs.validar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Sistema.reservar();
                Lector lc = new Lector();
                lc.validarCodigo(vs.textArea.getText());
                afd = new AFD();
                afd.validarPalabras(lc.getPalabras());
                ArrayList<Token> a = afd.getTokens();
                //Guarda tokens
                Sistema.tokens = a;
                Object [][] data= new Object[a.size()][Sistema.columns.length];
                for(int i=0; i<a.size(); i++){
                    /*try{
                        if(a.get(i-1).getLexema().equals("Abstract") && a.get(i).getTipo().equals("ID")){
                            Token t = a.get(i);
                            a.get(i).setTipo("TIPO_DEFINIDO");
                            for(int j= i+1; j<a.size(); j++){
                                Token t2 = a.get(j);
                                if(t2.getLexema().equals(t.getLexema())){
                                    t2.setTipo("TIPO_DEFINIDO");
                                }
                            }
                        }
                    }catch(Exception ex){
                    }*/
                    //for(int j=0; j<Sistema.columns.length; j++){
                        
                        data[i][0] = a.get(i).getTipo();
                        data[i][1] = a.get(i).getPalabra();
                        data[i][2] = a.get(i).getId()*Token.buffer + a.get(i).getOrden();
                   // }
                }
                
                
                
                dt = new DefaultTableModel(data, Sistema.columns);
                vs.table.setModel(dt);
                vs.table.setVisible(true);
            }
            
        });
    }
    
    private void ESintactico(){
        vs.sintactico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Sistema.error101 && !Sistema.error102 && !Sistema.error103){
                try{
                    
                    Parser parser = new Parser(Sistema.getCola());
                    
                    parser.setTas("./TAS.xlsx");
                    
                    JOptionPane.showMessageDialog(null, parser.analizar() ? "La cadena es aceptada" : "La cadena no es aceptada");
                    
                    ArrayList<Object[]> a = Sistema.producciones;
                    
                    Object [][] data= new Object[a.size()][Sistema.columnas.length];
                    
                    for(int i=0; i<a.size(); i++){
                        

                            data[i][0] = a.get(i)[0];
                            data[i][1] = a.get(i)[1];
                            data[i][2] = a.get(i)[2];
                            data[i][3] = a.get(i)[3];
                        
                    }
                    CtrlProducciones ctrl = new CtrlProducciones(new VistaProducciones(), new DefaultTableModel(data, Sistema.columnas));
                    
                }catch(Exception ex){
                    System.err.println(ex);
                    ex.printStackTrace();
                }  
                }
                
                if(Sistema.error101){
                    JOptionPane.showMessageDialog(null, "Error de asignacion");
                    try{
                    throw new ArrayIndexOutOfBoundsException();
                }catch(ArrayIndexOutOfBoundsException exx){
                    exx.printStackTrace();
                }
                    
                }else if(Sistema.error102){
                    JOptionPane.showMessageDialog(null, "Variable no declarada");
                    try{
                    throw new ArrayIndexOutOfBoundsException();
                }catch(ArrayIndexOutOfBoundsException exx){
                    exx.printStackTrace();
                }
                }else if(Sistema.error103){
                    JOptionPane.showMessageDialog(null, "Error de acceso");
                    try{
                    throw new ArrayIndexOutOfBoundsException();
                }catch(ArrayIndexOutOfBoundsException exx){
                    exx.printStackTrace();
                }
                }else if(Sistema.error104){
                    JOptionPane.showMessageDialog(null, "variable ya declarada");
                    try{
                    throw new ArrayIndexOutOfBoundsException();
                }catch(ArrayIndexOutOfBoundsException exx){
                    exx.printStackTrace();
                }
                }
                
                
            }
        });
    }
    
}
