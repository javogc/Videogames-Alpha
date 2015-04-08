import javax.swing.JFrame;

/**
 *This is the main class
 * @author Natalia Garcia & Any Landeros
 */
public class SpaceInvaders extends JFrame implements Commons {
        
        public SpaceInvaders()
        {
                add(new Board());
                setTitle("Space Invaders");
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setSize(iBOARD_WIDTH, iBOARD_HEIGTH);
                setLocationRelativeTo(null);
                setVisible(true);
                setResizable(false);
        }
        
        public static void main(String[] args) {
                new SpaceInvaders();
        }
}