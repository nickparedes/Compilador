package modelo.ADP;

public class TAS {
    private String [][] matrix;
    
    public String getProduction(String Vn, String Vt){
        String result = null;
        int a=0, b=0;
        //this.imprimir();
        for(int i=0; i<this.matrix.length; i++){
            //System.out.println(matrix[i][0].equals(Vn));
            if(matrix[i][0].equals(Vn)){
                a = i;
                break;
            }
        }

        for(int j=0; j<this.matrix[0].length; j++){
           
            //System.out.println(matrix[0][j].equals(Vt));
            if(matrix[0][j].equals(Vt)){
                b = j;
                break;
            }
        }

        if(a != 0 && b != 0)
            result = matrix[a][b];
        //System.out.println("esto retornamos: " + a + "   " + b);
        return result;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
        
    }

    public String[][] getMatrix() {
        return matrix;
    }
    
    public void imprimir(){
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[0].length; j++){
                System.out.printf("%s   ", matrix[i][j]);
            }
            System.out.println();
        }
    }
    
}
