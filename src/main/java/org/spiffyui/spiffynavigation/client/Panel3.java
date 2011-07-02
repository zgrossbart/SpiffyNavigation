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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class Panel3 extends HTMLPanel
{
    private static final SpiffyUiHtml STRINGS = (SpiffyUiHtml) GWT.create(SpiffyUiHtml.class);
    
    /**
     * Creates a new panel
     */
    public Panel3()
    {
        super("div", STRINGS.Panel3_html());
        
        getElement().setId("panel3");
        
        RootPanel.get("mainContent").add(this);
        
        setVisible(false);
        
        Anchor panel1 = new Anchor("Panel 1", "#");
        panel1.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event)
            {
                event.preventDefault();
                Index.selectItem(Index.PANEL1_NAV_ITEM_ID);
            }
        });
        add(panel1, "panel1Link");
        
        Anchor panel2 = new Anchor("Panel 2", "#");
        panel2.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event)
            {
                event.preventDefault();
                Index.selectItem(Index.PANEL2_NAV_ITEM_ID);
            }
        });
        add(panel2, "panel2Link");
    }
}
