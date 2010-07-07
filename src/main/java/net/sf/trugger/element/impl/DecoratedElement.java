/*
 * Copyright 2009-2010 Marcelo Varella Barca Guimarães
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
package net.sf.trugger.element.impl;

import java.lang.annotation.Annotation;

import net.sf.trugger.HandlingException;
import net.sf.trugger.ValueHandler;
import net.sf.trugger.element.Element;
import net.sf.trugger.element.NonSpecificElementException;

/**
 * Base class for all decorated elements.
 *
 * @author Marcelo Varella Barca Guimarães
 * @since 2.7
 */
public class DecoratedElement implements Element {

  protected final Element element;

  public DecoratedElement(Element decorated) {
    this.element = decorated;
  }

  public Class declaringClass() {
    return element.declaringClass();
  }

  public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
    return element.getAnnotation(annotationClass);
  }

  public Annotation[] getAnnotations() {
    return element.getAnnotations();
  }

  public Annotation[] getDeclaredAnnotations() {
    return element.getDeclaredAnnotations();
  }

  public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
    return element.isAnnotationPresent(annotationClass);
  }

  public boolean isSpecific() {
    return element.isSpecific();
  }

  public <E> E target() {
    return element.target();
  }

  public String name() {
    return element.name();
  }

  public Class type() {
    return element.type();
  }

  @Override
  public void value(Object value) throws HandlingException {
    if(isSpecific()) {
      in(target()).value(value);
    } else {
      throw new NonSpecificElementException();
    }
  }

  @Override
  public <E> E value() throws HandlingException {
    if (isSpecific()) {
      return (E) in(target()).value();
    }
    throw new NonSpecificElementException();
  }

  @Override
  public ValueHandler in(Object target) {
    return element.in(target);
  }

  public boolean isReadable() {
    return element.isReadable();
  }

  public boolean isWritable() {
    return element.isWritable();
  }

}
