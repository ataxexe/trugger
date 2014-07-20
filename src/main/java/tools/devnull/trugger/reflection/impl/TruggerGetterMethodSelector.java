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
import tools.devnull.trugger.reflection.ReflectionPredicates;
import tools.devnull.trugger.selector.GetterMethodSelector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * A default implementation for the getter method selector.
 * 
 * @author Marcelo Guimarães
 */
public class TruggerGetterMethodSelector implements GetterMethodSelector {
  
  private final PredicateBuilder<Method> builder = new PredicateBuilder<Method>();
  private boolean recursively;
  private final String name;
  private MembersFinder<Method> finder;
  
  public TruggerGetterMethodSelector(String name, MembersFinder<Method> finder) {
    this.name = name;
    this.finder = finder;
  }
  
  public GetterMethodSelector annotated() {
    builder.add(ReflectionPredicates.ANNOTATED);
    return this;
  }
  
  public GetterMethodSelector notAnnotated() {
    builder.add(ReflectionPredicates.NOT_ANNOTATED);
    return this;
  }
  
  public GetterMethodSelector annotatedWith(Class<? extends Annotation> type) {
    builder.add(ReflectionPredicates.isAnnotatedWith(type));
    return this;
  }
  
  public GetterMethodSelector notAnnotatedWith(Class<? extends Annotation> type) {
    builder.add(ReflectionPredicates.isNotAnnotatedWith(type));
    return this;
  }
  
  public GetterMethodSelector that(Predicate<? super Method> predicate) {
    builder.add(predicate);
    return this;
  }
  
  public Method in(Object target) {
    return new MemberSelector<Method>(new GetterFinder(name, finder), builder.predicate(), recursively).in(target);
  }
  
  public GetterMethodSelector recursively() {
    recursively = true;
    return this;
  }
  
}
