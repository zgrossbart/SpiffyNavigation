/*******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package org.spiffyui.spiffynavigation.client;

import java.util.HashMap;

import org.spiffyui.client.JSUtil;
import org.spiffyui.client.MainFooter;
import org.spiffyui.client.MainHeader;
import org.spiffyui.client.nav.MainNavBar;
import org.spiffyui.client.nav.NavBarListener;
import org.spiffyui.client.nav.NavItem;
import org.spiffyui.client.nav.NavSection;
import org.spiffyui.client.nav.NavSeparator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * This class is the main entry point for our GWT module.
 */
public class Index implements EntryPoint, NavBarListener 
{
    private static final SpiffyUiHtml STRINGS = (SpiffyUiHtml) GWT.create(SpiffyUiHtml.class);

    private static Index g_index;
    
    private MainNavBar m_navBar;
    private final HashMap<NavItem, ComplexPanel> m_panels = new HashMap<NavItem, ComplexPanel>();
    
    /** NavItem ID for the first panel */
    public static final String PANEL1_NAV_ITEM_ID = "p1";
    
    /** NavItem ID for the second panel */
    public static final String PANEL2_NAV_ITEM_ID = "p2";
    
    /** NavItem ID for the third panel */
    public static final String PANEL3_NAV_ITEM_ID = "p3";

    /**
     * The Index page constructor
     */
    public Index()
    {
        g_index = this;
    }


    @Override
    public void onModuleLoad()
    {
        /*
         This is where we load our module and create our dynamic controls.  The MainHeader
         displays our title bar at the top of our page.
         */
        MainHeader header = new MainHeader();
        header.setHeaderTitle("Spiffy UI Navigation");
        
        m_navBar = new MainNavBar();
        
        /*
         Now we add our panels
         */
        NavItem item = new NavItem(PANEL1_NAV_ITEM_ID, "Panel 1", "This is the Panel 1 tooltip", "?" + PANEL1_NAV_ITEM_ID);
        m_navBar.add(item);
        m_panels.put(item, new Panel1());
        
        item = new NavItem(PANEL2_NAV_ITEM_ID, "Panel 2", "This is the Panel 2 tooltip", "?" + PANEL2_NAV_ITEM_ID);
        m_navBar.add(item);
        m_panels.put(item, new Panel2());
        
        /*
         Now we'll add a menu separator
         */
        m_navBar.add(new NavSeparator(HTMLPanel.createUniqueId()));
        
        /*
         * Now a collapsabla section
         */
        NavSection group1 = new NavSection("group1", "Group 1");
        m_navBar.add(group1);
        
        item = new NavItem(PANEL3_NAV_ITEM_ID, "Panel 3", "This is the Panel 3 tooltip", "?" + PANEL3_NAV_ITEM_ID);
        group1.add(item);
        m_panels.put(item, new Panel3());
        
        /*
         The main footer shows our message at the bottom of the page.
         */
        MainFooter footer = new MainFooter();
        footer.setFooterString("This sample was built with the <a href=\"http://www.spiffyui.org\">Spiffy UI Framework</a>");
        
        m_navBar.setBookmarkable(true);
        m_navBar.addListener(this);
        
        selectDefaultItem();
    }
    
    private void selectDefaultItem()
    {
        /*
         * If the user has loaded this application in their
         * current session then we want to bring them back to the
         * same page when the application reloads.
         */
        if (getIdFromHash() != null &&
            m_navBar.getItem(getIdFromHash()) != null) {
            selectItem(getIdFromHash());
        } else if (Window.Location.getParameterMap().size() > 0) {
            selectItem("" + Window.Location.getParameterMap().keySet().toArray()[0]);
        } else {
            selectItem(PANEL1_NAV_ITEM_ID);
        }
    }
    
    /**
     * This method finds the current panel ID from the hash code in the URL.  This only shows
     * up when we're using a browser that doesn't support HTML 5 history.
     * 
     * @return the panel ID from the hash code or null if it isn't there
     */
    private static String getIdFromHash()
    {
        if (Window.Location.getHash().length() < 2) {
            return null;
        }
        
        String hash = Window.Location.getHash();
        
        /*
         In HTML4 browsers the hash will look like this:  #?p2&_suid=777
         */
        
        int start = 2;
        int end = hash.indexOf('&');
        return hash.substring(start, end);
    }
    
    /**
     * Select the NavItem
     * @param itemId ID of the NavItem to select
     */
    public static void selectItem(String itemId)
    {
        NavItem item = g_index.m_navBar.getItem(itemId);
        g_index.m_navBar.selectItem(item);
    }
    
    @Override
    public boolean preItemSelected(NavItem item)
    {
        return true;
    }

    @Override
    public void itemSelected(NavItem item)
    {
        for (NavItem key : m_panels.keySet()) {
            /*
             We could hide and show these panels by just calling setVisible,
             but that causes a redraw bug in IE 8 where the body extends for
             for the total height of the page below the footer.
             */
            if (key.equals(item)) {
                JSUtil.show("#" + m_panels.get(key).getElement().getId());
            } else {
                JSUtil.hide("#" + m_panels.get(key).getElement().getId(), "fast");
            }
        }
    }
}
