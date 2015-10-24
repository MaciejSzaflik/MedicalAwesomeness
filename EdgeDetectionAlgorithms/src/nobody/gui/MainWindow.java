package nobody.gui;

import java.awt.EventQueue;

public class MainWindow {
	
	private static MainWindow instance;
	public static MainWindow getInstance()
	{
		if(instance == null)
			instance = new MainWindow();
		return instance;
	}
	
	private DisplayFrame display;
	
	private MainWindow() {
		display = new DisplayFrame();
		
	}
	public void Init()
	{
		display.setVisible(true);
	}
	public void Repaint()
	{
		display.PaintCurrentState();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow.getInstance().Init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

}


