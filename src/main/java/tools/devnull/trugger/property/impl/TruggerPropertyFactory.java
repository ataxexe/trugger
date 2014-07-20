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
package tools.devnull.trugger.property.impl;

import tools.devnull.trugger.Finder;
import tools.devnull.trugger.element.Element;
import tools.devnull.trugger.element.impl.TruggerElementSelector;
import tools.devnull.trugger.element.impl.TruggerElementsSelector;
import tools.devnull.trugger.property.PropertyFactory;
import tools.devnull.trugger.selector.ElementSelector;
import tools.devnull.trugger.selector.ElementsSelector;

/**
 * The default PropertyFactory implementation.
 *
 * @author Marcelo Guimarães
 */
public class TruggerPropertyFactory implements PropertyFactory {

  private Finder<Element> finder = new TruggerPropertyFinder();

  /**
   * Returns a new {@link TruggerElementSelector}.
   */
  public ElementSelector createPropertySelector(String name) {
    return new TruggerElementSelector(name, finder);
  }

  /**
   * Returns a new {@link TruggerElementsSelector}.
   */
  public ElementsSelector createPropertiesSelector() {
    return new TruggerElementsSelector(finder);
  }
}
