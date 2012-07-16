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
package org.atatec.trugger.test.bind;

import static org.atatec.trugger.bind.Bind.newBinder;
import static org.junit.Assert.assertEquals;
import org.atatec.trugger.bind.PostBind;
import org.atatec.trugger.element.impl.TruggerElementSelector;
import org.atatec.trugger.element.impl.TruggerElementsSelector;
import org.atatec.trugger.reflection.impl.TruggerFieldSelector;
import org.atatec.trugger.reflection.impl.TruggerFieldsSelector;

import org.junit.Test;

/**
 * A class for testing bind operations.
 *
 * @author Marcelo Varella Barca Guimarães
 */
public class BinderTest {

  public class PostBindTest {

    private int count;

    @PostBind
    private void postBind() {
      count++;
    }

    @PostBind
    private void postBind(String string) {
      throw new Error();
    }

  }

  @Test
  public void testSelectors() {
    // these selectors are tested in another test class
    // just check the types to ensure they will work
    assertEquals(TruggerElementsSelector.class, newBinder().use(null).toElements().getClass());
    assertEquals(TruggerElementSelector.class, newBinder().use(null).toElement("name").getClass());

    assertEquals(TruggerFieldSelector.class, newBinder().use(null).toField("name").getClass());
    assertEquals(TruggerFieldsSelector.class, newBinder().use(null).toFields().getClass());
  }

  @Test
  public void testPostBind() {
    PostBindTest object = new PostBindTest(){};
    newBinder().applyBinds(object);
    assertEquals(1, object.count);
  }

}