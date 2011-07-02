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
import org.spiffyui.client.JSONUtil;
import org.spiffyui.client.MessageUtil;
import org.spiffyui.client.rest.RESTCallback;
import org.spiffyui.client.rest.RESTException;
import org.spiffyui.client.rest.RESTility;
import org.spiffyui.client.widgets.LongMessage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class Panel1 extends HTMLPanel implements ClickHandler, KeyPressHandler
{
    private static final SpiffyUiHtml STRINGS = (SpiffyUiHtml) GWT.create(SpiffyUiHtml.class);
    
    private TextBox m_text = new TextBox();
    private LongMessage m_longMessage = new LongMessage("longMsgPanel");
    
    /**
     * Creates a new panel
     */
    public Panel1()
    {
        super("div", STRINGS.Panel1_html());
        
        getElement().setId("panel1");
        
        RootPanel.get("mainContent").add(this);
        
        setVisible(true);
        
        /*
         These dynamic controls add interactivity to our page.
         */
        add(m_longMessage, "longMsg");
        add(m_text, "nameField");
        final Button button = new Button("Submit");
        add(button, "submitButton");
        
        button.addClickHandler(this);
        m_text.addKeyPressHandler(this);
    }
    
    @Override
    public void onLoad()
    {
        super.onLoad();
        /*
         Let's set focus into the text field when the page first loads
         */
        m_text.setFocus(true);
    }
    
    @Override
    public void onClick(ClickEvent event)
    {
        sendRequest();
    }

    @Override
    public void onKeyPress(KeyPressEvent event)
    {
        /*
         We want to submit the request if the user pressed enter
         */
        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
            sendRequest();
        }
    }
    
    /**
     * Send the REST request to the server and read the response back.
     */
    private void sendRequest()
    {
        String q = m_text.getValue().trim();
        if (q.equals("")) {
            MessageUtil.showWarning("Please enter your name in the text field.", false);
            return;
        }
        RESTility.callREST("simple/" + q, new RESTCallback() {
            
            @Override
            public void onSuccess(JSONValue val)
            {
                showSuccessMessage(val);
            }
            
            @Override
            public void onError(int statusCode, String errorResponse)
            {
                MessageUtil.showError("Error.  Status Code: " + statusCode + " " + errorResponse);
            }
            
            @Override
            public void onError(RESTException e)
            {
                MessageUtil.showError(e.getReason());
            }
        });
        
    }
    
    /**
     * Show the successful message result of our REST call.
     * 
     * @param val    the value containing the JSON response from the server
     */
    private void showSuccessMessage(JSONValue val)
    {
        JSONObject obj = val.isObject();
        String name = JSONUtil.getStringValue(obj, "user");
        String browser = JSONUtil.getStringValue(obj, "userAgent");
        String server = JSONUtil.getStringValue(obj, "serverInfo");
        
        String message = "Hello, " + name + "!  <br/>You are using " + browser + ".<br/>The server is " + server + ".";
        m_longMessage.setHTML(message);
    }
}
