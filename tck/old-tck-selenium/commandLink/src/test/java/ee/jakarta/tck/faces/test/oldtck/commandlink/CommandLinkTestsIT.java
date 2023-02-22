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

package ee.jakarta.tck.faces.test.oldtck.commandlink;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

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
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

import jakarta.faces.component.html.HtmlInputText;

@RunWith(Arquillian.class)
public class CommandLinkTestsIT {

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

     /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /**
   * @testName: clinkRenderEncodeTest
   * 
   * @assertion_ids: PENDING
   * 
   * @test_Strategy: Ensure proper CommandLink rendering: - case 1: Anchor has
   *                 href value of '#' Anchor display value is "Click Me1"
   *                 Anchor onclick attribute value has a non-zero length
   * 
   *                 - case 2: Anchor has href value of '#' Anchor display value
   *                 is "Click Me2" The styleClass tag attribute is rendered as
   *                 the class attribute on the rendered anchor Anchor onclick
   *                 attribute value has a non-zero length
   * 
   *                 - case 3: Anchor has href value of '#' Anchor has display
   *                 value of "Click Me3" which is specified as a nested
   *                 HtmlOutput tag. Anchor onclick attribute value has a
   *                 non-zero length
   * 
   *                 - case 4: REMOVED CODE DUE TO BUG ID:6460959
   * 
   *                 - case 5: CommandLink has the disabled attribute set to
   *                 true. Ensure that: A span element is rendered instead of an
   *                 anchor The span has no onclick content The display value of
   *                 the span is 'Disabled Link' The styleClass tag attribute is
   *                 rendered as the class attribute on the rendered span
   *                 element
   * 
   *                 - case 6: CommandLink has the disabled attribute set to
   *                 true with a nested output component as the link's textual
   *                 value.
   * 
   *                 - case 7: CommandLink is tied to a backend bean via the
   *                 "binding" attribute. Test to make sure that the "title",
   *                 "shape" & "styleclass" are set and rendered correctly.
   * 
   * @since 1.2
   */
  @Test
  public void clinkRenderEncodeTest() throws Exception {

    HtmlPage page = webClient.getPage(webUrl + "/faces/encodetest_facelet.xhtml");

    HtmlAnchor link1 = (HtmlAnchor) page.getElementById("form:link1"); // form:link1? 
    assertNotNull(link1);
    assertEquals("#",link1.getHrefAttribute());
    assertEquals("Click Me1",link1.asNormalizedText());
    assertFalse(link1.getOnClickAttribute().length() < 0);

    HtmlAnchor link2 = (HtmlAnchor) page.getElementById("form:link2"); // form:link1? 
    assertNotNull(link2);
    assertEquals("#",link1.getHrefAttribute());
    assertEquals("Click Me2",link2.asNormalizedText());
    assertEquals("sansserif", link2.getAttribute("class"));
    assertFalse(link2.getOnClickAttribute().length() < 0);

    HtmlAnchor link3 = (HtmlAnchor) page.getElementById("form:link3"); // form:link1? 
    assertNotNull(link2);
    assertEquals("#",link3.getHrefAttribute());
    assertEquals("Click Me3",link3.asNormalizedText());
    assertFalse(link3.getOnClickAttribute().length() < 0);

    HtmlSpan link5 = (HtmlSpan) page.getElementById("form:link5"); // form:link1? 
    assertNotNull(link5);
    assertEquals("sansserif", link5.getAttribute("class"));
    assertEquals("Disabled Link",link5.asNormalizedText());
    assertFalse(link5.getOnClickAttribute().length() < 0);

    HtmlSpan span2 = (HtmlSpan) page.getElementById("form:link6"); // form:link1? 
    assertNotNull(span2);
    assertEquals("Disabled Link(Nested)",span2.asNormalizedText());

    HtmlAnchor span7 = (HtmlAnchor) page.getElementById("form:link7"); // form:link1? 
    assertNotNull(span7);
    assertEquals("sansserif",span7.getAttribute("class"));
    assertEquals("rectangle",span7.getShapeAttribute());
    assertEquals("gone",span7.getAttribute("title"));
  } // END clinkRenderEncodeTest

  /**
   * @testName: clinkRenderDecodeTest
   * 
   * @assertion_ids: PENDING
   * 
   * @test_Strategy:
   * 
   * @since 1.2
   */
  public void clinkRenderDecodeTest() throws Fault {
    StringBuilder messages = new StringBuilder(64);
    Formatter formatter = new Formatter(messages);

    List<HtmlPage> pages = new ArrayList<HtmlPage>();
    pages.add(getPage(CONTEXT_ROOT + "/faces/decodetest_facelet.xhtml"));

    for (HtmlPage page : pages) {
      HtmlAnchor link1 = (HtmlAnchor) getElementOfTypeIncludingId(page, "a",
          "link1");
      try {
        HtmlPage postBack = (HtmlPage) link1.click();
        // Check to see if an error header was returned
        String msgHeader = postBack.getWebResponse()
            .getResponseHeaderValue("actionEvent");
        if (msgHeader != null) {
          formatter.format(msgHeader + "%n");
        } else {
          // Check for non-error header
          msgHeader = postBack.getWebResponse()
              .getResponseHeaderValue("actionEventOK");
          if (msgHeader == null) {
            formatter.format("ActionListener was not invoked "
                + "when CommandButton 'command1' was clicked.%n");
          }

        }
      } catch (IOException e) {
        throw new Fault(e);
      }

      handleTestStatus(messages);
    }

  } // END clinkRenderDecodeTest

  /**
   * @testName: clinkRenderPassthroughTest
   * 
   * @assertion_ids: PENDING
   * 
   * @test_Strategy: Ensure those attributes marked as passthrough are indeed
   *                 visible in the rendered markup as specified in the JSP
   *                 page.
   * 
   * @since 1.2
   */
  public void clinkRenderPassthroughTest() throws Fault {

    TreeMap<String, String> control = new TreeMap<String, String>();
    control.put("accesskey", "U");
    control.put("charset", "ISO-8859-1");
    control.put("coords", "31,45");
    control.put("dir", "LTR");
    control.put("hreflang", "en");
    control.put("lang", "en");
    control.put("onblur", "js1");
    control.put("ondblclick", "js3");
    control.put("onfocus", "js4");
    control.put("onkeydown", "js5");
    control.put("onkeypress", "js6");
    control.put("onkeyup", "js7");
    control.put("onmousedown", "js8");
    control.put("onmousemove", "js9");
    control.put("onmouseout", "js10");
    control.put("onmouseover", "js11");
    control.put("onmouseup", "js12");
    control.put("rel", "somevalue");
    control.put("rev", "revsomevalue");
    control.put("shape", "rect");
    control.put("style", "Color: red;");
    control.put("tabindex", "0");
    control.put("title", "sample");
    control.put("type", "type");

    TreeMap<String, String> controlSpan = new TreeMap<String, String>();
    controlSpan.put("accesskey", "U");
    controlSpan.put("dir", "LTR");
    controlSpan.put("lang", "en");
    controlSpan.put("onblur", "js1");
    controlSpan.put("ondblclick", "js3");
    controlSpan.put("onfocus", "js4");
    controlSpan.put("onkeydown", "js5");
    controlSpan.put("onkeypress", "js6");
    controlSpan.put("onkeyup", "js7");
    controlSpan.put("onmousedown", "js8");
    controlSpan.put("onmousemove", "js9");
    controlSpan.put("onmouseout", "js10");
    controlSpan.put("onmouseover", "js11");
    controlSpan.put("onmouseup", "js12");
    controlSpan.put("style", "Color: red;");
    controlSpan.put("tabindex", "0");
    controlSpan.put("title", "sample");
 

    StringBuilder messages = new StringBuilder(64);
    Formatter formatter = new Formatter(messages);

    List<HtmlPage> pages = new ArrayList<HtmlPage>();
    pages.add(getPage(CONTEXT_ROOT + "/faces/passthroughtest.xhtml"));
    pages.add(getPage(CONTEXT_ROOT + "/faces/passthroughtest_facelet.xhtml"));

    for (HtmlPage page : pages) {

      // Facelet Specific PassThrough options
      if (page.getTitleText().contains("facelet")) {
        control.put("foo", "bar");
        control.put("singleatt", "singleAtt");
        control.put("manyattone", "manyOne");
        control.put("manyatttwo", "manyTwo");
        control.put("manyattthree", "manyThree");
        controlSpan.put("foo", "bar");
        controlSpan.put("singleatt", "singleAtt");
        controlSpan.put("manyattone", "manyOne");
        controlSpan.put("manyatttwo", "manyTwo");
        controlSpan.put("manyattthree", "manyThree");
      }

      HtmlAnchor anchor = (HtmlAnchor) getElementOfTypeIncludingId(page, "a",
          "link1");
      HtmlSpan span = (HtmlSpan) getElementOfTypeIncludingId(page, "span",
          "link2");

      if (anchor == null) {
        formatter.format("Unable to find rendered anchor with ID "
            + "containing 'link1' %n");
      }

      if (span == null) {
        formatter.format(
            "Unable to find rendered span with ID " + "containing 'link2' %n");
      }

      if (span == null || anchor == null) {
        return;
      }

      validateAttributeSet(control, anchor,
          new String[] { "name", "id", "value", "href", "onclick" }, formatter);

      validateAttributeSet(controlSpan, span,
          new String[] { "name", "id", "value", "href", "onclick" }, formatter);

      handleTestStatus(messages);
    }

  } // END clinkRenderPassthroughTest

  protected void validateAttributeSet(TreeMap<String, String> control,
    HtmlElement underTest, String[] ignoredAttributes, Formatter formatter) {

    Arrays.sort(ignoredAttributes);
    TreeMap<String, String> fromPage = new TreeMap<String, String>();
    for (Iterator i = underTest.getAttributesMap().values().iterator(); i
        .hasNext();) {
      DomAttr domEntry = (DomAttr) i.next();
      String key = domEntry.getName();
      if (Arrays.binarySearch(ignoredAttributes, key) > -1) {
        continue;
      }
      // fromPage.put(key, entry.getValue());
      fromPage.put(key, domEntry.getValue());
    }

    if (!control.equals(fromPage)) {
      formatter.format("%n Unexpected result when validating "
          + "passthrough attributes received for the rendered "
          + "'%s' in the response.%n", underTest.getTagName());
      formatter.format("%nExpected attribute key/value pairs:%n%s",
          control.toString());
      formatter.format(
          "%nAttribute key/value pairs from the " + "response:%n%s",
          fromPage.toString());
    }

  } // END validateAttributeSet

}
