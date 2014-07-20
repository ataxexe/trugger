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
import tools.devnull.trugger.reflection.MethodPredicates;
import tools.devnull.trugger.reflection.ReflectionPredicates;
import tools.devnull.trugger.selector.MethodsSelector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * A default implementation for the methods selector.
 * 
 * @author Marcelo Guimarães
 */
public class TruggerMethodsSelector implements MethodsSelector {
  
  private MembersSelector<Method> selector;

  public TruggerMethodsSelector(MembersFinder<Method> finder) {
    selector = new MembersSelector<Method>(finder);
  }

  public MethodsSelector annotated() {
    selector.builder().add(ReflectionPredicates.ANNOTATED);
    return this;
  }
  
  public MethodsSelector notAnnotated() {
    selector.builder().add(ReflectionPredicates.NOT_ANNOTATED);
    return this;
  }
  
  public MethodsSelector annotatedWith(Class<? extends Annotation> type) {
    selector.builder().add(ReflectionPredicates.isAnnotatedWith(type));
    return this;
  }
  
  public MethodsSelector notAnnotatedWith(Class<? extends Annotation> type) {
    selector.builder().add(ReflectionPredicates.isNotAnnotatedWith(type));
    return this;
  }
  
  public MethodsSelector nonStatic() {
    selector.builder().add(ReflectionPredicates.dontDeclare(Modifier.STATIC));
    return this;
  }
  
  public MethodsSelector nonFinal() {
    selector.builder().add(ReflectionPredicates.dontDeclare(Modifier.FINAL));
    return this;
  }
  
  public MethodsSelector that(Predicate<? super Method> predicate) {
    selector.builder().add(predicate);
    return this;
  }
  
  public MethodsSelector named(String name) {
    selector.builder().add(ReflectionPredicates.named(name));
    return this;
  }
  
  public MethodsSelector withParameters(Class<?>... parameterTypes) {
    selector.builder().add(MethodPredicates.takes(parameterTypes));
    return this;
  }
  
  public MethodsSelector returning(Class<?> returnType) {
    selector.builder().add(MethodPredicates.returns(returnType));
    return this;
  }
  
  public MethodsSelector withoutReturnType() {
    selector.builder().add(MethodPredicates.returns(Void.TYPE));
    return this;
  }
  
  public MethodsSelector withoutParameters() {
    return withParameters();
  }
  
  public Set<Method> in(Object target) {
    return selector.in(target);
  }
  
  public MethodsSelector recursively() {
    selector.useHierarchy();
    return this;
  }
  
}
