
// Purpose: A Graphical User Interface for a Five-In-A-Row game.
//          This class implements the user interface (view and controller),
//          and the logic (model) is implemented in a separate class that
//          knows nothing about the user interface.
// Applet tag: <applet code="fiveinarow.Five.class" 
//                     archive="fiveinarow.jar" width="270" height="326"></applet>

// Author : Fred Swartz - 2006-12-01 - Placed in public domain.



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

///////////////////////////////////////////////////////////////////// class Five
public class Five extends JApplet {
    //================================================================ constants
    private static final int ROWS = 9;
    private static final int COLS = 9;
    private static final Color[]  PLAYER_COLOR = {null, Color.BLACK, Color.WHITE};
    private static final String[] PLAYER_NAME  = {null, "BLACK", "WHITE"};
    
    //======================================================= instance variables
    private GameBoard  _boardDisplay;
    private JTextField _statusField = new JTextField();
    private FiveModel  _gameLogic   = new FiveModel(ROWS, COLS);
    
    //============================================================== method main
    // If used as an applet, main will never be called.
    // If used as an application, this main will be called and it will use 
    //         the applet for the content pane.
    public static void main(String[] args) {
        JFrame window = new JFrame("Five in a Row");
        window.setContentPane(new Five());  // Make applet content pane.
        window.pack();                      // Do layout
        //System.out.println(window.getContentPane().getSize());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null); // Center window.
        window.setResizable(false);
        window.setVisible(true);            // Make window visible
    }
    
    //============================================================== constructor
    public Five() {
        //--- Create some buttons
        JButton newGameButton = new JButton("New Game");
        JButton undoButton    = new JButton("Undo");
        
        //--- Create control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(newGameButton);
        controlPanel.add(undoButton);
        
        //--- Create component to display board.
        _boardDisplay = new GameBoard();
        
        //--- Set the layout and add the components
        this.setLayout(new BorderLayout());
        this.add(controlPanel , BorderLayout.NORTH);
        this.add(_boardDisplay, BorderLayout.CENTER);
        this.add(_statusField , BorderLayout.SOUTH);
        
        //-- Add action listener to New Game button.
        newGameButton.addActionListener(new NewGameAction());
    }
    
    ////////////////////////////////////////////////// inner class NewGameAction
    private class NewGameAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            _gameLogic.reset();
            _boardDisplay.repaint();
        }
    }
    
    ////////////////////////////////////////////////////// inner class GameBoard
    // This is defined inside outer class to use things from the outer class:
    //    * The logic (could be passed to the constructor).
    //    * The number of rows and cols (could be passed to constructor).
    //    * The status field - shouldn't really be managed here.
    //      See note on using Observer pattern in the model.
    class GameBoard extends JComponent implements MouseListener {
        //============================================================ constants
        private static final int CELL_SIZE = 30; // Pixels
        private static final int WIDTH  = COLS * CELL_SIZE;
        private static final int HEIGHT = ROWS * CELL_SIZE;
        
        //========================================================== constructor
        public GameBoard() {
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            this.addMouseListener(this);  // Listen to own mouse events.
        }
        
        //======================================================= paintComponent
        @Override public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            
            //... Paint background
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(0, 0, WIDTH, HEIGHT);
            
            //... Paint grid (could be done once and saved).
            g2.setColor(Color.BLACK);
            for (int r=1; r<ROWS; r++) {  // Horizontal lines
                g2.drawLine(0, r*CELL_SIZE, WIDTH, r*CELL_SIZE);
            }
            for (int c=1; c<COLS; c++) {
                g2.drawLine(c*CELL_SIZE, 0, c*CELL_SIZE, HEIGHT);
            }
            
            //... Draw player pieces.
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    int x = c * CELL_SIZE;
                    int y = r * CELL_SIZE;
                    int who = _gameLogic.getPlayerAt(r, c);
                    if (who != _gameLogic.EMPTY) {
                        g2.setColor(PLAYER_COLOR[who]);
                        g2.fillOval(x+2, y+2, CELL_SIZE-4, CELL_SIZE-4);
                    }
                }
            }
        }
        
        //================================================ listener mousePressed
        // When the mouse is pressed (would released be better?),
        //       the coordinates are translanted into a row and column.
        // Setting the status field in here isn't really the right idea.
        //       Instead the model should notify those who have registered.
        public void mousePressed(MouseEvent e) {
            int col = e.getX() / CELL_SIZE;
            int row = e.getY() / CELL_SIZE;
            
            boolean gameOver = _gameLogic.getGameStatus() != 0;
            int currentOccupant = _gameLogic.getPlayerAt(row, col);
            if (!gameOver && currentOccupant == _gameLogic.EMPTY) {
                //... Make a move.
                _gameLogic.move(row, col);
                
                //... Report what happened in status field.
                switch (_gameLogic.getGameStatus()) {
                    case 1:
                        //... Player one wins.  Game over.
                        _statusField.setText("BLACK WINS");
                        break;
                    case 2:
                        //... Player two wins.  Game over.
                        _statusField.setText("WHITE WINS");
                        break;
                        
                    case FiveModel.TIE:  // Tie game.  Game over.
                        _statusField.setText("TIE GAME");
                        break;
                        
                    default:
                        _statusField.setText(PLAYER_NAME[_gameLogic.getNextPlayer()]
                                + " to play");
                }
                
            } else {  // Not legal - clicked non-empty location or game over.
                Toolkit.getDefaultToolkit().beep();
            }
            
            this.repaint();  // Show updated board
        }
        
        //================================================== ignore these events
        public void mouseClicked(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
    }    
}