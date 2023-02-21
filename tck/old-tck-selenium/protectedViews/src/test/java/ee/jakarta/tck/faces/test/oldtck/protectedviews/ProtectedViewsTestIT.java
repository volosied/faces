/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.oldtck.protectedviews;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import jakarta.faces.component.html.HtmlInputText;

@RunWith(Arquillian.class)
public class ProtectedViewsTestIT {

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
     * @see HtmlInputText#getType()
     * @see https://github.com/jakartaee/faces/issues/1560
     */
    @Test
    public void test_public_view() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/views/public.xhtml");
        assertTrue("Expected text not found!", page.asNormalizedText().contains("This is a Public View!"));
    }

    /**
     * @see HtmlInputText#getType()
     * @see https://github.com/jakartaee/faces/issues/1560
     */
    @Test
    public void test_protected_view() throws Exception {
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        HtmlPage page = webClient.getPage(webUrl + "faces/views/protected.xhtml");
        System.out.println(page.asNormalizedText());
        HtmlAnchor anchor = (HtmlAnchor) page.getElementById("messOne");
        System.out.println(anchor + " <- anchor");
        assertTrue("Expected a ProtectedViewException when accessing a protected view", page.asNormalizedText().contains("jakarta.faces.application.ProtectedViewException"));
    }

}
