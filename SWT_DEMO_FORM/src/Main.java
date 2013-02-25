
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTrayItem;


public class Main {
	
	public static void main(String[] args){
		new Main();
	}
	
	
	Menu menu ;

	public Main() {
		Display display = new Display();
		
		Shell shell = GUI(display);

		
		shell.open();
		try {
			testGUI(shell);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		while (!shell.isDisposed())
            if (!display.readAndDispatch()) {
                display.sleep();
            }
            
        display.dispose();
        
		
	}
	

	public void testGUI(Shell shell){
		SWTBotPreferences.PLAYBACK_DELAY = 100;
		SWTBot bot = new SWTBot(shell);
		
		// all tray
		for (SWTBotTrayItem trayitem : bot.trayItems()) {
			System.out.println(trayitem);
		}

		SWTBotUtils.findSwtBotMenuByMenu(menu, "Q1").click();
		SWTBotUtils.findSwtBotMenuByMenu(menu, "Q2").menu("Q2-2").click();
		bot.button("Button").click();
		
		
		
	}
	

	
	public Shell GUI(Display display) {

		
		// Shell shell = new Shell(display, SWT.ON_TOP|SWT.MODELESS);
		Shell shell = new Shell(display);
		shell.setSize(400, 300);
		
		
		// new a button
		final Button button = new Button(shell, SWT.PUSH);
		button.setText("Button");
		button.addListener(SWT.MouseDown, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				System.out.println("Click "+button.getText());
				
			}
		});
		button.pack();
		

		// bar menu
		Menu menu_bar = new Menu(shell, SWT.BAR);
		MenuItem file_item = add_menu_item("File", null, menu_bar, SWT.CASCADE);
		Menu test_submenu = new Menu(menu_bar);
		file_item.setMenu(test_submenu);
		add_menu_item("Open", null, test_submenu, SWT.CASCADE);
		shell.setMenuBar(menu_bar);
		
		
		// system tray
		menu = new Menu(shell, SWT.POP_UP);
		TrayItem  system_tray;
		system_tray = new TrayItem(display.getSystemTray(), SWT.NONE);
		system_tray.setImage(create_image("icon.png"));
		system_tray.setText("SwtBot - Test");
		
		system_tray.addListener(SWT.MenuDetect, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				menu.setVisible(true);
			}
		});
		
		
		
		// system tray menu
		MenuItem Q1 = add_menu_item("Q1", null, menu, SWT.PUSH);
		MenuItem Q2 = add_menu_item("Q2", null, menu, SWT.CASCADE);
		Menu Q2_menu = new Menu(menu);
		Q2.setMenu(Q2_menu);
		add_menu_item("Q2-1", null, Q2_menu, SWT.PUSH);
		add_menu_item("Q2-2", null, Q2_menu, SWT.PUSH);
		add_menu_item("Q2-3", null, Q2_menu, SWT.PUSH);
		
		MenuItem Q3 = add_menu_item("Q3", null, menu, SWT.PUSH);
		
		return shell;
		
	}
	
	public Image create_image(String file) {
		try {
			return new Image(Display.getCurrent(), new FileInputStream("../images/"+file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public MenuItem add_menu_item(final String label, Listener selection_handler, Menu menu, int item_type) {
		//int item_type = SWT.PUSH;
		
		MenuItem menuitem = new MenuItem(menu, item_type);
		menuitem.setText(label);
		
		if(selection_handler != null)
			menuitem.addListener(SWT.Selection, selection_handler);
		else 
			menuitem.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event e) {
					System.out.println("Click "+label);
				}
			});
		
		return menuitem;
	}
	
}
