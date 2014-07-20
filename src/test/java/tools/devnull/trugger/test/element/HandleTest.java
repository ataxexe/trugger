/*
 * Copyright 2009-2012 Marcelo Varella Barca Guimarães
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tools.devnull.trugger.test.element;

import tools.devnull.trugger.HandlingException;
import tools.devnull.trugger.ValueHandler;
import tools.devnull.trugger.element.Element;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Set;

import static tools.devnull.trugger.element.Elements.elements;
import static tools.devnull.trugger.element.Elements.handle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/** @author Marcelo Varella Barca Guimarães */
public class HandleTest {

  public static class TestObject {

    String a;
    String b;
    String c;
    String d;

    static String e;
    static String f;
    static String g;
    static String h;

  }

  @Before
  public void before() {
    TestObject.e = null;
    TestObject.f = null;
    TestObject.g = null;
    TestObject.h = null;
  }

  @Test
  public void testHandleForSpecificElements() {
    Set<Element> elements = elements().ofType(String.class).specific().in(TestObject.class);
    ValueHandler valueHandler = handle(elements);
    Collection<String> strings = valueHandler.value();
    assertEquals(4, strings.size());
    for (String string : strings) {
      assertNull(string);
    }
    valueHandler.value("value");
    valueHandler = handle(elements);
    strings = valueHandler.value();
    for (String string : strings) {
      assertEquals("value", string);
    }
  }

  @Test
  public void testHandlerForNonSpecificElements() {
    Set<Element> elements = elements().ofType(String.class).nonSpecific().in(TestObject.class);
    TestObject target = new TestObject();
    ValueHandler valueHandler = handle(elements, target);
    Collection<String> strings = valueHandler.value();
    assertEquals(4, strings.size());
    for (String string : strings) {
      assertNull(string);
    }
    valueHandler.value("value2");
    valueHandler = handle(elements, target);
    strings = valueHandler.value();
    for (String string : strings) {
      assertEquals("value2", string);
    }
  }

  @Test(expected = HandlingException.class)
  public void testHandlingError() {
    Set<Element> elements = elements().ofType(String.class).nonSpecific().in(TestObject.class);
    ValueHandler valueHandler = handle(elements);
    valueHandler.value("");
  }

}
