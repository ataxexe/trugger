/*
 * The Apache License
 *
 * Copyright 2009 Marcelo "Ataxexe" Guimarães <ataxexe@devnull.tools>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tools.devnull.trugger;

import org.junit.Before;
import org.junit.Test;
import tools.devnull.trugger.util.OptionalFunction;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OptionalFunctionTest {

  private Function mainFunction;
  private Function alternativeFunction;

  private Object mainReturn;
  private Object mainObject;
  private Object alternativeReturn;
  private Object alternativeObject;

  @Before
  public void initialize() {
    mainFunction = mock(Function.class);
    alternativeFunction = mock(Function.class);

    mainReturn = new Object();
    alternativeReturn = new Object();
    mainObject = new Object();
    alternativeObject = null;

    when(mainFunction.apply(mainObject)).thenReturn(mainReturn);
    when(alternativeFunction.apply(alternativeObject)).thenReturn(alternativeReturn);
  }

  @Test
  public void testSingleFunctionBehaviour() {
    when(mainFunction.apply(alternativeObject)).thenReturn(alternativeReturn);

    assertEquals(mainReturn, OptionalFunction.of(mainFunction).apply(mainObject));
    verify(mainFunction).apply(mainObject);

    assertNull(OptionalFunction.of(mainFunction).apply(alternativeObject));
    verify(mainFunction, never()).apply(alternativeObject);
  }

  @Test
  public void testOptionalFunctionBehaviour() {
    OptionalFunction function = OptionalFunction.of(mainFunction).orElse(alternativeFunction);

    assertEquals(mainReturn, function.apply(mainObject));
    verify(mainFunction).apply(mainObject);

    assertEquals(alternativeReturn, function.apply(alternativeObject));
    verify(alternativeFunction).apply(alternativeObject);
  }

  @Test
  public void testReturnBehaviour() {
    OptionalFunction function = OptionalFunction.of(mainFunction).orElseReturn("OK");

    assertEquals(mainReturn, function.apply(mainObject));
    verify(mainFunction).apply(mainObject);

    assertEquals("OK", function.apply(alternativeObject));
    verify(alternativeFunction, never()).apply(alternativeObject);
  }

  @Test(expected = TruggerException.class)
  public void testThrowBehaviour() {
    Supplier exception = mock(Supplier.class);
    when(exception.get()).thenReturn(new TruggerException());
    try {
      OptionalFunction function = OptionalFunction.of(mainFunction).orElseThrow(exception);

      assertEquals(mainReturn, function.apply(mainObject));
      verify(mainFunction).apply(mainObject);

      function.apply(alternativeObject);
    } finally {
      verify(exception).get();
    }
  }

}
