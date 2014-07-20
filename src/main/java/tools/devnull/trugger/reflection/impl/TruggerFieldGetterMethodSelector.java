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
package tools.devnull.trugger.reflection.impl;

import tools.devnull.trugger.predicate.Predicate;
import tools.devnull.trugger.predicate.PredicateBuilder;
import tools.devnull.trugger.reflection.MethodPredicates;
import tools.devnull.trugger.reflection.ReflectionPredicates;
import tools.devnull.trugger.selector.FieldGetterMethodSelector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * A default implementation for the getter method selector.
 * 
 * @author Marcelo Guimarães
 */
public class TruggerFieldGetterMethodSelector implements FieldGetterMethodSelector {
  
  private final PredicateBuilder<Method> builder = new PredicateBuilder<Method>();
  private final Field field;

  private MembersFinder<Method> finder;

  public TruggerFieldGetterMethodSelector(Field field, MembersFinder<Method> finder) {
    this.field = field;
    this.finder = finder;
    builder.add(MethodPredicates.returns(field.getType()));
  }
  
  public FieldGetterMethodSelector annotated() {
    builder.add(ReflectionPredicates.ANNOTATED);
    return this;
  }
  
  public FieldGetterMethodSelector notAnnotated() {
    builder.add(ReflectionPredicates.NOT_ANNOTATED);
    return this;
  }
  
  public FieldGetterMethodSelector annotatedWith(Class<? extends Annotation> type) {
    builder.add(ReflectionPredicates.isAnnotatedWith(type));
    return this;
  }
  
  public FieldGetterMethodSelector notAnnotatedWith(Class<? extends Annotation> type) {
    builder.add(ReflectionPredicates.isNotAnnotatedWith(type));
    return this;
  }
  
  public FieldGetterMethodSelector that(Predicate<? super Method> predicate) {
    builder.add(predicate);
    return this;
  }
  
  public Method in(Object target) {
    return new MemberSelector<Method>(new GetterFinder(field.getName(), finder), builder.predicate()).in(target);
  }
  
}
