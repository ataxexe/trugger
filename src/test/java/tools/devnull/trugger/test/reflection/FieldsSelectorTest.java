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
import tools.devnull.trugger.reflection.ReflectionPredicates;
import tools.devnull.trugger.selector.FieldsSelector;
import tools.devnull.trugger.test.Flag;
import tools.devnull.trugger.test.SelectionTestAdapter;
import org.junit.Test;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import static tools.devnull.trugger.reflection.Reflection.reflect;
import static tools.devnull.trugger.test.TruggerTest.assertMatch;
import static tools.devnull.trugger.test.TruggerTest.assertNoResult;
import static tools.devnull.trugger.test.TruggerTest.assertResult;

/**
 * @author Marcelo Varella Barca Guimarães
 */
public class FieldsSelectorTest {

  static class AnnotatedSelectorTest {
    @Flag
    @Resource
    private String annotatedField;
    private String notAnnotatedField;
    @Resource
    private String field;
  }

  @Test
  public void testNoSelector() {
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>(){
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
    }, AnnotatedSelectorTest.class, 3);
    assertNoResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>(){
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
    }, this);
  }

  @Test
  public void testAnnotatedSelector() {
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>(){
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.annotated();
      }
      public void assertions(Set<Field> fields) {
        assertMatch(fields, ReflectionPredicates.ANNOTATED);
      }
    }, AnnotatedSelectorTest.class, 2);
  }

  @Test
  public void testNotAnnotatedSelector() {
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>(){
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.notAnnotated();
      }
      public void assertions(Set<Field> fields) {
        assertMatch(fields, ReflectionPredicates.NOT_ANNOTATED);
      }
    }, AnnotatedSelectorTest.class, 1);
  }

  @Test
  public void testAnnotatedWithSelector() {
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>(){
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.annotatedWith(Flag.class);
      }
      public void assertions(Set<Field> fields) {
        assertMatch(fields, ReflectionPredicates.isAnnotatedWith(Flag.class));
      }
    }, AnnotatedSelectorTest.class, 1);
  }

  @Test
  public void testNotAnnotatedWithSelector() {
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>(){
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.notAnnotatedWith(Flag.class);
      }
      public void assertions(Set<Field> fields) {
        assertMatch(fields, ReflectionPredicates.isNotAnnotatedWith(Flag.class));
      }
    }, AnnotatedSelectorTest.class, 2);
  }

  static class NonFinalSelectorTest {
    final String finalField = null;
    String nonFinalField;
  }

  @Test
  public void testNonFinalSelector() {
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>(){
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.nonFinal();
      }
      public void assertions(Set<Field> fields) {
        assertMatch(fields, ReflectionPredicates.dontDeclare(Modifier.FINAL));
      }
    }, NonFinalSelectorTest.class, 1);
  }

  static class NonStaticSelectorTest {
    static String staticField;
    String nonStaticField;
  }

  @Test
  public void testNonStaticSelector() {
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>(){
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.nonStatic();
      }
      public void assertions(Set<Field> fields) {
        assertMatch(fields, ReflectionPredicates.dontDeclare(Modifier.STATIC));
      }
    }, NonStaticSelectorTest.class, 1);
  }

  static class BaseClassTest {
    int i;
  }

  static class ExtendedClassTest extends BaseClassTest {

  }

  @Test
  public void testRecursivelySelector() {
    assertNoResult(reflect().fields().in(ExtendedClassTest.class));
    assertResult(reflect().fields().recursively().in(ExtendedClassTest.class));
  }

  @Test
  public void testPredicateSelector() {
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>(){
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.that(Predicates.ALWAYS_TRUE);
      }
    }, BaseClassTest.class, 1);
    assertNoResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>(){
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.that(Predicates.ALWAYS_FALSE);
      }
    }, BaseClassTest.class);
  }

  static class AccessSelectorTest {
    private int privateField;
    protected int protectedField;
    int defaultField;
    public int publicField;
  }

  static class TypeSelectorTest {
    int i;
    Integer I;
    double d;
    Double D;
    String s;
  }

  @Test
  public void testTypeSelector() {
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.ofType(int.class);
      }
    }, TypeSelectorTest.class, 1);
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.ofType(Integer.class);
      }
    }, TypeSelectorTest.class, 1);

    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.ofType(int.class);
      }
    }, TypeSelectorTest.class, 1);
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.ofType(Integer.class);
      }
    }, TypeSelectorTest.class, 1);

    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.ofType(double.class);
      }
    }, TypeSelectorTest.class, 1);
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.ofType(Double.class);
      }
    }, TypeSelectorTest.class, 1);

    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.ofType(Double.class);
      }
    }, TypeSelectorTest.class, 1);
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.ofType(double.class);
      }
    }, TypeSelectorTest.class, 1);

    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.ofType(String.class);
      }
    }, TypeSelectorTest.class, 1);
    assertNoResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.ofType(Object.class);
      }
    }, TypeSelectorTest.class);
  }

  @Test
  public void testAssignableToSelector() {
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.assignableTo(int.class);
      }
    }, TypeSelectorTest.class, 2);
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.assignableTo(Integer.class);
      }
    }, TypeSelectorTest.class, 2);

    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.assignableTo(int.class);
      }
    }, TypeSelectorTest.class, 2);
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.assignableTo(Integer.class);
      }
    }, TypeSelectorTest.class, 2);

    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.assignableTo(double.class);
      }
    }, TypeSelectorTest.class, 2);
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.assignableTo(Double.class);
      }
    }, TypeSelectorTest.class, 2);

    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.assignableTo(Double.class);
      }
    }, TypeSelectorTest.class, 2);
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.assignableTo(double.class);
      }
    }, TypeSelectorTest.class, 2);

    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.assignableTo(String.class);
      }
    }, TypeSelectorTest.class, 1);
    assertResult(new SelectionTestAdapter<FieldsSelector, Set<Field>>() {
      public FieldsSelector createSelector() {
        return reflect().fields();
      }
      public void makeSelections(FieldsSelector selector) {
        selector.assignableTo(Object.class);
      }
    }, TypeSelectorTest.class, 5);
  }

}
