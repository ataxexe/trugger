/*
 * The Apache License
 *
 * Copyright 2009 Marcelo Guimaraes <ataxexe@backpackcloud.com>
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
package com.backpackcloud.trugger.element;

import com.backpackcloud.trugger.element.impl.TruggerElementFactory;
import com.backpackcloud.trugger.element.impl.TruggerElementSelection;
import com.backpackcloud.trugger.Selection;
import com.backpackcloud.trugger.util.NullableArgFunction;

import java.util.function.Consumer;

/**
 * A class for helping {@link Element} selection.
 *
 * @author Marcelo Guimaraes.
 * @since 1.2
 */
public final class Elements {

  private static final ElementFactory factory;

  private Elements() {
  }

  static {
    factory = new TruggerElementFactory();
  }

  /**
   * Registers the given finder
   *
   * @see ElementFactory#register(ElementFinder)
   */
  public static void register(ElementFinder finder) {
    factory.register(finder);
  }

  public static ElementSelection select() {
    return new TruggerElementSelection(factory);
  }

  /**
   * Short for {@code select().element(name)}
   */
  public static ElementSelector element(String name) {
    return select().element(name);
  }

  /**
   * Short for {@code select().elements()}
   */
  public static ElementsSelector elements() {
    return select().elements();
  }

  /**
   * Short for {@code select().element()}
   */
  public static ElementSelector element() {
    return select().element();
  }

  /**
   * Copies elements through objects.
   */
  public static ElementCopier copy() {
    return factory.createElementCopier();
  }

  /**
   * Copies the elements returned by the given selector through objects.
   */
  public static ElementCopier copy(ElementsSelector selector) {
    return factory.createElementCopier(selector);
  }

  /**
   * Returns a function that gets the value of a selected element.
   *
   * @return a function that gets the value of a selected element.
   */
  public static <E> NullableArgFunction<Selection<Element>, E> getValue() {
    return NullableArgFunction.of(selection -> selection.result().getValue());
  }

  /**
   * Returns a function that sets the value of a selected element.
   *
   * @param newValue the value to set
   * @return a function that sets the value of a selected element.
   */
  public static Consumer<Selection<Element>> setValue(Object newValue) {
    return selection -> selection.result().setValue(newValue);
  }

}
