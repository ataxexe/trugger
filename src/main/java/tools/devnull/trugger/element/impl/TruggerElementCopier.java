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

import tools.devnull.trugger.element.DestinationSelector;
import tools.devnull.trugger.element.Element;
import tools.devnull.trugger.element.ElementCopier;
import tools.devnull.trugger.element.ElementCopy;
import tools.devnull.trugger.element.Elements;
import tools.devnull.trugger.selector.ElementsSelector;
import tools.devnull.trugger.transformer.Transformer;
import tools.devnull.trugger.util.Utils;

import java.util.Set;

/**
 * The default implementation for the property copy operation.
 *
 * @author Marcelo Guimarães
 */
public final class TruggerElementCopier implements ElementCopier, DestinationSelector {

  private ElementsSelector selector;
  private Transformer<Object, ElementCopy> transformer;

  private boolean copyNull = true;

  private Object src;
  private Object dest;

  public TruggerElementCopier() {

  }

  public TruggerElementCopier(ElementsSelector selector) {
    this.selector = selector.readable();
  }

  public DestinationSelector notNull() {
    this.copyNull = false;
    return this;
  }

  public DestinationSelector from(Object src) {
    this.src = src;
    return this;
  }

  public void to(Object object) {
    dest = object;
    startCopy();
  }

  @Override
  public DestinationSelector as(Transformer transformer) {
    this.transformer = transformer;
    return this;
  }

  private void startCopy() {
    Set<Element> elements;
    if (selector == null) {
      elements = Elements.elements().readable().in(src);
    } else {
      elements = selector.in(src);
    }
    boolean transform = (transformer != null);
    Element destProperty;
    for (Element element : elements) {
      String name = element.name();
      if (src.getClass().equals(dest.getClass())) {
        destProperty = element.isWritable() ? element : null;
      } else {
        destProperty = Elements.element(name).writable().in(dest);
      }
      if (destProperty != null) {
        copy(transform, destProperty, element);
      }
    }
  }

  private void copy(boolean transform, Element dest, Element src) {
    Object value = src.in(this.src).value();
    PropertyCopyImpl copy = new PropertyCopyImpl(src, dest, value);
    if (transform) {
      value = transformer.transform(copy);
    }
    if (value != null) {
      if (Utils.areAssignable(dest.type(), value.getClass())) {
        dest.in(this.dest).value(value);
      }
    } else if (copyNull) {
      dest.in(this.dest).value(value);
    }
  }

  private static class PropertyCopyImpl implements ElementCopy {

    private final Element from;

    private final Element to;

    private final Object value;

    private PropertyCopyImpl(Element from, Element to, Object value) {
      this.from = from;
      this.to = to;
      this.value = value;
    }

    public Element sourceElement() {
      return from;
    }

    public Object value() {
      return value;
    }

    public Element destinationElement() {
      return to;
    }

  }

}
