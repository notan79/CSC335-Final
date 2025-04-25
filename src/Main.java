import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.PalaceGameGUI;

public class Main {
	
	public static void main(String[] args) {
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
