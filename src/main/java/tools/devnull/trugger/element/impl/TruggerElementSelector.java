/*
 * Copyright 2009-2012 Marcelo Guimarães
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
package tools.devnull.trugger.element.impl;

import tools.devnull.trugger.Finder;
import tools.devnull.trugger.element.Element;
import tools.devnull.trugger.element.ElementPredicates;
import tools.devnull.trugger.predicate.CompositePredicate;
import tools.devnull.trugger.predicate.Predicate;
import tools.devnull.trugger.predicate.PredicateBuilder;
import tools.devnull.trugger.reflection.ReflectionPredicates;
import tools.devnull.trugger.selector.ElementSelector;

import java.lang.annotation.Annotation;

/**
 * A default implementation for {@link ElementSelector}.
 *
 * @author Marcelo Guimarães
 */
public class TruggerElementSelector implements ElementSelector {

  private final PredicateBuilder<Element> builder;
  private final Finder<Element> finder;
  private final String name;

  /**
   * Creates a new selector for the element with the specified name.
   *
   * @param name
   *          the element name for selection.
   */
  public TruggerElementSelector(String name, Finder<Element> finder) {
    this.name = name;
    this.finder = finder;
    builder = new PredicateBuilder<Element>();
  }

  public ElementSelector annotated() {
    builder.add(ReflectionPredicates.ANNOTATED);
    return this;
  }

  public ElementSelector notAnnotated() {
    builder.add(ReflectionPredicates.NOT_ANNOTATED);
    return this;
  }

  public ElementSelector annotatedWith(Class<? extends Annotation> type) {
    builder.add(ReflectionPredicates.isAnnotatedWith(type));
    return this;
  }

  public ElementSelector notAnnotatedWith(Class<? extends Annotation> type) {
    builder.add(ReflectionPredicates.isNotAnnotatedWith(type));
    return this;
  }

  public ElementSelector ofType(Class<?> type) {
    builder.add(ElementPredicates.ofType(type));
    return this;
  }

  public ElementSelector assignableTo(Class<?> type) {
    builder.add(ElementPredicates.assignableTo(type));
    return this;
  }

  public ElementSelector that(final Predicate<? super Element> predicate) {
    builder.add(predicate);
    return this;
  }

  public ElementSelector nonReadable() {
    builder.add(ElementPredicates.NON_READABLE);
    return this;
  }

  public ElementSelector nonWritable() {
    builder.add(ElementPredicates.NON_WRITABLE);
    return this;
  }

  public ElementSelector readable() {
    builder.add(ElementPredicates.READABLE);
    return this;
  }

  public ElementSelector nonSpecific() {
    builder.add(ElementPredicates.NON_SPECIFIC);
    return this;
  }

  public ElementSelector specific() {
    builder.add(ElementPredicates.SPECIFIC);
    return this;
  }

  public ElementSelector writable() {
    builder.add(ElementPredicates.WRITABLE);
    return this;
  }

  public Element in(Object target) {
    Element element = finder.find(name).in(target);
    if (element == null) {
      return null;
    }
    CompositePredicate<Element> predicate = builder.predicate();
    if(predicate != null) {
      return predicate.evaluate(element) ? element : null;
    }
    return element;
  }

  protected PredicateBuilder<Element> builder() {
    return builder;
  }

  protected Finder<Element> finder() {
    return finder;
  }

}
