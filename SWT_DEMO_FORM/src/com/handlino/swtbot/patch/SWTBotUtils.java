package com.handlino.swtbot.patch;

import java.util.List;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.finders.MenuFinder;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.hamcrest.Matcher;


public class SWTBotUtils {
	
	public static SWTBotMenu findSwtBotMenuWitchMatcherByMenu(final Menu menu, final Matcher matcher, final int index){
		MenuItem menuItem = UIThreadRunnable.syncExec(new WidgetResult<MenuItem>() {
			public MenuItem run() {
				List<MenuItem> menus = new MenuFinder().findMenus(menu, matcher, true);
				if (!menus.isEmpty())
					return menus.get(index);
				return null;
			}
		});
		return new SWTBotMenu(menuItem, matcher);
	}
	
	public static SWTBotMenu findSwtBotMenuByMenu(final Menu menu, final String menuName, final int index){
		final Matcher<? extends Widget> matcher = WidgetMatcherFactory.withMnemonic(menuName);
		return findSwtBotMenuWitchMatcherByMenu(menu, matcher, index);
	}
	
	public static SWTBotMenu findSwtBotMenuByMenu(final Menu menu, final String menuName) {
		return findSwtBotMenuByMenu(menu, menuName, 0);
	}
}
