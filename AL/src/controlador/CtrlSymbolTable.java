/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import vista.SymbolTable;

/**
 *
 * @author axell
 */
public class CtrlSymbolTable {
    private SymbolTable st;
    private DefaultTableModel dt;
    
    public CtrlSymbolTable(SymbolTable st, DefaultTableModel dt){
        this.st = st;
        this.dt = dt;
        propiedades();
        st.table.setModel(dt);
        
    }
    
    
    private void propiedades(){
        st.setVisible(true);
        st.setLocationRelativeTo(null);
        st.setSize(1200, 700);
        st.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        st.setTitle("Tabla de simbolos");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            
        } catch (InstantiationException ex) {
            
        } catch (IllegalAccessException ex) {
           
        } catch (UnsupportedLookAndFeelException ex) {
            
        }
    }
}
