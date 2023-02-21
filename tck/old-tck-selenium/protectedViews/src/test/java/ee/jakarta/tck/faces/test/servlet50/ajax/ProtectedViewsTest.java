/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package ee.jakarta.tck.faces.test.servlet50.ajax;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;

import ee.jakarta.tck.faces.test.util.selenium.BaseArquilianRunner;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.behavior.AjaxBehavior;

@RunWith(BaseArquilianRunner.class)
public class ProtectedViewsTest {

    @ArquillianResource
    private URL webUrl;
    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return create(ZipImporter.class, getProperty("finalName") + ".war")
                .importFrom(new File("target/" + getProperty("finalName") + ".war"))
                .as(WebArchive.class);
    }

    @Before
    public void setUp() {
        webClient = new WebClient();
    }

    @After
    public void tearDown() {
        webClient.close();
    }

    /**
     * @see AjaxBehavior#getExecute()
     * @see UIComponent#getCompositeComponentParent(UIComponent)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5032
     */
    // @Test
    // public void testImplicitThis() throws Exception {
    //     HtmlPage page = webClient.getPage(webUrl + "issue5032IT.xhtml");

    //     HtmlTextInput form1input2 = (HtmlTextInput) page.getElementById("form1:inputs:input2");
    //     form1input2.setValueAttribute("1");
    //     form1input2.fireEvent("change");
    //     webClient.waitForBackgroundJavaScript(3000);
    //     String form1messages = page.getElementById("form1:messages").asNormalizedText();
    //     assertEquals("there are no validation messages coming from required field form1:input1", "", form1messages);
    // }

    /**
     * @see AjaxBehavior#getExecute()
     * @see UIComponent#getCompositeComponentParent(UIComponent)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5032
     */
    @Test
    public void viewProtectedViewNonAccessPointTest_new() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "/faces/views/protected.xhtml");

        assertEquals("Expected a ProtectedViewException when accessing a protected view", "", page.asNormalizedText().contains("jakarta.faces.application.ProtectedViewException"));
    
    }

    public void viewProtectedViewNonAccessPointTest() throws Exception {
        // StringBuilder messages = new StringBuilder(64);
        // Formatter formatter = new Formatter(messages);
    
        // throwExceptionOnFailingStatusCode = false;
    
        // HtmlPage page = getPage(new WebClient(),
        //     CONTEXT_ROOT + "/faces/views/protected.xhtml");

        HtmlPage page = webClient.getPage(webUrl + "/faces/views/protected.xhtml");

        HtmlAnchor anchor = (HtmlAnchor) page.getElementById("messOne");
        // HtmlAnchor anchor = (HtmlAnchor) getElementOfTypeIncludingId(page, "a",
        //     "linkOne");
    
        // if (validateExistence("linkOne", "a", anchor, formatter)) {
        //   formatter.format("We should not be able to gain access to a "
        //       + "Protected View from outside of the Protected View's " + "webapp!");
        //   handleTestStatus(messages);
        //   formatter.close();
        //   return;
        }
    
        // formatter.close();
    
      //} // END viewProtectedViewNonAccessPointTest
    
      /**
       * @testName: viewProtectedViewSameWebAppAccessTest
       * 
       * @assertion_ids: PENDING
       * 
       * @test_Strategy: Validate that we are able to gain access to a protected
       *                 view from inside the same web-app through a non-protected
       *                 view.
       * 
       * @since 2.2
       */
    //   public void viewProtectedViewSameWebAppAccessTest() throws Fault {
    //     StringBuilder messages = new StringBuilder(64);
    //     Formatter formatter = new Formatter(messages);
    //     String result;
    //     String expected = "This is a Protected View!";
    
    //     HtmlPage page = getPage(new WebClient(),
    //         CONTEXT_ROOT + "/faces/views/public.xhtml");
    
    //     HtmlAnchor anchor = (HtmlAnchor) getElementOfTypeIncludingId(page, "a",
    //         "linkOne");
    
    //     if (!validateExistence("linkOne", "a", anchor, formatter)) {
    //       handleTestStatus(messages);
    //       return;
    //     }
    
    //     try {
    //       result = anchor.click().getWebResponse().getContentAsString();
    
    //       if (!result.contains(expected)) {
    //         formatter.format("Error occured during clicking of Link!");
    //         handleTestStatus(messages);
    //       }
    
    //     } catch (IOException e) {
    //       formatter.format("Error occured during clicking of Link!");
    //       e.printStackTrace();
    //       handleTestStatus(messages);
    //       formatter.close();
    //     }
    
    //     formatter.close();
    
    //   } // END viewProtectedViewNonAccessPointTest

}
