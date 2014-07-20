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
package tools.devnull.trugger.test.reflection;

import tools.devnull.trugger.predicate.Predicates;
import tools.devnull.trugger.selector.MethodSelector;
import tools.devnull.trugger.test.Flag;
import tools.devnull.trugger.test.SelectionTestAdapter;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static tools.devnull.trugger.reflection.Reflection.reflect;
import static tools.devnull.trugger.reflection.ReflectionPredicates.ANNOTATED;
import static tools.devnull.trugger.reflection.ReflectionPredicates.NOT_ANNOTATED;
import static tools.devnull.trugger.reflection.ReflectionPredicates.isAnnotatedWith;
import static tools.devnull.trugger.reflection.ReflectionPredicates.dontDeclare;
import static tools.devnull.trugger.reflection.ReflectionPredicates.isNotAnnotatedWith;
import static tools.devnull.trugger.test.TruggerTest.assertMatch;
import static tools.devnull.trugger.test.TruggerTest.assertNoResult;
import static tools.devnull.trugger.test.TruggerTest.assertResult;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Marcelo Varella Barca Guimarães
 */
public class MethodSelectorTest {

  static class AnnotatedSelectorTest {
    @Flag
    void foo(){}
    void bar(){}
  }

  @Test
  public void testNoSelector() {
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("foo");
      }
    }, AnnotatedSelectorTest.class);
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("bar");
      }
    }, AnnotatedSelectorTest.class);
  }

  @Test
  public void testAnnotatedSelector() {
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("foo");
      }
      public void makeSelections(MethodSelector selector) {
        selector.annotated();
      }
      @Override
      public void assertions(Method method) {
        assertMatch(method, ANNOTATED);
      }
    }, AnnotatedSelectorTest.class);
    assertNoResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("bar");
      }
      public void makeSelections(MethodSelector selector) {
        selector.annotated();
      }
    }, AnnotatedSelectorTest.class);
  }

  @Test
  public void testNotAnnotatedSelector() {
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("bar");
      }
      public void makeSelections(MethodSelector selector) {
        selector.notAnnotated();
      }
      @Override
      public void assertions(Method method) {
        assertMatch(method, NOT_ANNOTATED);
      }
    }, AnnotatedSelectorTest.class);
    assertNoResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("foo");
      }
      public void makeSelections(MethodSelector selector) {
        selector.notAnnotated();
      }
    }, AnnotatedSelectorTest.class);
  }

  @Test
  public void testAnnotatedWithSelector() {
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("foo");
      }
      public void makeSelections(MethodSelector selector) {
        selector.annotatedWith(Flag.class);
      }
      @Override
      public void assertions(Method method) {
        assertMatch(method, isAnnotatedWith(Flag.class));
      }
    }, AnnotatedSelectorTest.class);
    assertNoResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("bar");
      }
      public void makeSelections(MethodSelector selector) {
        selector.annotatedWith(Flag.class);
      }
    }, AnnotatedSelectorTest.class);
  }

  @Test
  public void testNotAnnotatedWithSelector() {
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("bar");
      }
      public void makeSelections(MethodSelector selector) {
        selector.notAnnotatedWith(Flag.class);
      }
      @Override
      public void assertions(Method method) {
        assertMatch(method, isNotAnnotatedWith(Flag.class));
      }
    }, AnnotatedSelectorTest.class);
    assertNoResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("foo");
      }
      public void makeSelections(MethodSelector selector) {
        selector.notAnnotatedWith(Flag.class);
      }
    }, AnnotatedSelectorTest.class);
  }

  @Test
  public void testNonFinalSelector() {
    Object obj = new Object(){
      void foo(){}
      final void bar(){}
    };
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("foo");
      }
      public void makeSelections(MethodSelector selector) {
        selector.nonFinal();
      }
      public void assertions(Method method) {
        assertMatch(method, dontDeclare(Modifier.FINAL));
      }
    }, obj);
    assertNoResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("bar");
      }
      public void makeSelections(MethodSelector selector) {
        selector.nonFinal();
      }
    }, obj);
  }

  static class NonStaticSelector {
    void foo(){}
    static void bar(){}
  }

  @Test
  public void testNonStaticSelector() {
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("foo");
      }
      public void makeSelections(MethodSelector selector) {
        selector.nonStatic();
      }
      public void assertions(Method method) {
        assertMatch(method, dontDeclare(Modifier.STATIC));
      }
    }, NonStaticSelector.class);
    assertNoResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("bar");
      }
      public void makeSelections(MethodSelector selector) {
        selector.nonStatic();
      }
    }, NonStaticSelector.class);
  }

  @Test
  public void testRecursivelySelector() {
    Object obj = new Object(){};
    assertNull(reflect().method("toString").in(obj));
    assertNotNull(reflect().method("toString").recursively().in(obj));
  }

  @Test
  public void testReturnTypeSelector() {
    Object obj = new Object(){
      void foo(){}
      int bar(){return 0;}
    };
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("foo");
      }
      public void makeSelections(MethodSelector selector) {
        selector.withoutReturnType();
      }
      public void assertions(Method method) {
        assertEquals(Void.TYPE, method.getReturnType());
      }
    }, obj);
    assertNoResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("bar");
      }
      public void makeSelections(MethodSelector selector) {
        selector.withoutReturnType();
      }
    }, obj);
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("bar");
      }
      public void makeSelections(MethodSelector selector) {
        selector.returning(int.class);
      }
      public void assertions(Method method) {
        assertEquals(int.class, method.getReturnType());
      }
    }, obj);
  }

  @Test
  public void testPredicateSelector() {
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("toString");
      }
      public void makeSelections(MethodSelector selector) {
        selector.that(Predicates.ALWAYS_TRUE);
      }
    }, Object.class);
    assertNoResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("toString");
      }
      public void makeSelections(MethodSelector selector) {
        selector.that(Predicates.ALWAYS_FALSE);
      }
    }, Object.class);
  }

  @Test
  public void testParameterSelector() throws Exception {
    Object obj = new Object(){
      void foo(boolean b){}
      void foo2(Boolean b){}
      void bar(boolean b, boolean bool){}
    };
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("foo");
      }
      public void makeSelections(MethodSelector selector) {
        selector.withParameters(boolean.class);
      }
      public void assertions(Method method) {
        assertArrayEquals(new Class[]{boolean.class}, method.getParameterTypes());
      }
    }, obj);
    assertNoResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("foo");
      }
      public void makeSelections(MethodSelector selector) {
        selector.withParameters(Boolean.class);
      }
    }, obj);
    assertNoResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("foo2");
      }
      public void makeSelections(MethodSelector selector) {
        selector.withParameters(boolean.class);
      }
    }, obj);
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method("bar");
      }
      public void makeSelections(MethodSelector selector) {
        selector.withParameters(boolean.class, boolean.class);
      }
      public void assertions(Method method) {
        assertArrayEquals(new Class[]{boolean.class, boolean.class}, method.getParameterTypes());
      }
    }, obj);
  }

  @Test
  public void precedenceTest() {
    Object o = new Object(){
      public String toString() {
        return super.toString();
      };
    };
    Method method1 = reflect().method("toString").in(Object.class);
    Method method2 = reflect().method("toString").in(o);

    assertNotNull(method1);
    assertNotNull(method2);

    assertFalse(method1.equals(method2));
  }

}
