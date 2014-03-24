/*
 * Copyright 2009-2014 Marcelo Guimarães
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
package org.atatec.trugger.element.impl;

import org.atatec.trugger.Finder;
import org.atatec.trugger.element.Element;
import org.atatec.trugger.selector.ElementsSelector;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A default implementation for {@link ElementsSelector}.
 *
 * @author Marcelo Guimarães
 */
public final class TruggerElementsSelector implements ElementsSelector {

  private final Predicate<? super Element> predicate;
  private final Finder<Element> finder;

  public TruggerElementsSelector(Finder<Element> finder) {
    this.finder = finder;
    this.predicate = null;
  }

  public TruggerElementsSelector(Finder<Element> finder,
                                 Predicate<? super Element> predicate) {
    this.predicate = predicate;
    this.finder = finder;
  }

  @Override
  public ElementsSelector filter(Predicate<? super Element> predicate) {
    return new TruggerElementsSelector(finder, predicate);
  }

  public Set<Element> in(Object target) {
    Set<Element> elements = finder.findAll().in(target);
    if (predicate != null) {
      return elements.stream().filter(predicate).collect(Collectors.toSet());
    }
    return elements;
  }

}
