package view;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
	
	public static void main(String[] args) {
        resetGame();
    }
	
	public static void resetGame() {
		SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new PalaceGameGUI().setVisible(true);
        });
	}
}
