package tetris;

import java.awt.Color;
import static java.awt.Color.black;
import static java.awt.Color.blue;
import javax.swing.BorderFactory;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GameArea extends JPanel{
    private int gridRows;
    private int gridColumns;
    private int gridCellSize;
    private Color[][] background;
    
    
    //hacemos los bloques de tetris, intentar hacerlo en una clase aparte después
    private TetrisBlock block;
            
    public GameArea(JPanel placeholder, int columns){
        placeholder.setVisible(false);
        this.setBounds(placeholder.getBounds());
        this.setBackground(placeholder.getBackground());
        this.setBorder(placeholder.getBorder());
        this.setBorder(BorderFactory.createLineBorder(black));
        
        gridColumns = columns;
        gridCellSize = this.getBounds().width / gridColumns;
        gridRows = this.getBounds().height/gridCellSize;
        background = new Color[gridRows][gridColumns];
       
    }
    
    public void spawnBlock(){
        block = new TetrisBlock(new int[][]{{1,0},{1,0},{1,1}}, Color.blue); 
        block.spawn(gridColumns);
    }
    
    public boolean moveBlockDown(){
        if(checkBottom() == false){
            moveBlockToBackground();
            return false;}
        block.moveDown();
        repaint(); 
        return true;
    }

    private boolean checkBottom(){
        if(block.getBottomEdge() == gridRows){
            return false;
        }
        return true;
    }
    
    private void moveBlockToBackground(){
       
        int h = block.getHeight();
        int w = block.getWidth();
        int[][] shape = block.getShape();
        int xPos = block.getX();
        int yPos = block.getY();
        Color color = block.getColor();
        
          for(int r= 0; r < h; r++){
              for(int c = 0; c<w; c++){
                  
                  if(shape[r][c]==1){
// Si el elemento del bloque es igual a 1 significa que está coloreado, es decir que es parte del bloque.
//En las coordenadas que corresponden a r + yPos,
                      background[r+ yPos][c+ xPos]=color;
                  }
              }
          }
    }
    private void drawBlock(Graphics g){
        //Esto lo hace para mejorar el código
        int h = block.getHeight();
        int w = block.getWidth();
        Color color = block.getColor();
        int[][]shape = block.getShape();
        
        for(int row = 0; row < h; row++){
            for(int col = 0; col < w; col++){
                if(shape[row][col]==1){
                    
                    int x = (block.getX() + col) * gridCellSize;
                    int y = (block.getY() + row) * gridCellSize;
                    drawGridSquare(g,color,x,y);
                }
            }
        }
    }
    
    private void drawBackground(Graphics g){
        
        Color color;
        for(int r=0; r<gridRows;r++){
            for(int c=0; c<gridColumns; c++){
                color = background[r][c];
                
                if(color !=null){
                    
                    int x = c*gridCellSize;
                    int y = r*gridCellSize;
                    drawGridSquare(g,color,x,y);
                }
            }
        }
    }
    
    private void drawGridSquare(Graphics g, Color color, int x, int y){
        
         g.setColor(color);
         g.fillRect(x,y, gridCellSize, gridCellSize);
         g.setColor(black);
         g.drawRect(x,y, gridCellSize, gridCellSize);
    }
    @Override
    protected void paintComponent(Graphics g){
      super.paintComponent(g);
      drawBackground(g);
      drawBlock(g);
    }
}
