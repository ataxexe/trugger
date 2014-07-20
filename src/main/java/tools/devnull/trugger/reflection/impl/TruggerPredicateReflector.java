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
import tools.devnull.trugger.selector.ConstructorSelector;
import tools.devnull.trugger.selector.ConstructorsSelector;
import tools.devnull.trugger.selector.FieldGetterMethodSelector;
import tools.devnull.trugger.selector.FieldSelector;
import tools.devnull.trugger.selector.FieldSetterMethodSelector;
import tools.devnull.trugger.selector.FieldsSelector;
import tools.devnull.trugger.selector.GetterMethodSelector;
import tools.devnull.trugger.selector.MethodSelector;
import tools.devnull.trugger.selector.MethodsSelector;
import tools.devnull.trugger.selector.SetterMethodSelector;

import java.lang.reflect.Field;

/**
 * @author Marcelo Guimarães
 * @since 4.1
 */
public class TruggerPredicateReflector extends TruggerReflector {

  private final Predicate predicate;

  public TruggerPredicateReflector(Predicate predicate) {
    this.predicate = predicate;
  }

  @Override
  public GetterMethodSelector getterOf(String name) {
    return super.getterOf(name).that(predicate);
  }

  @Override
  public FieldGetterMethodSelector getterOf(Field field) {
    return super.getterOf(field).that(predicate);
  }

  @Override
  public SetterMethodSelector setterOf(String name) {
    return super.setterOf(name).that(predicate);
  }

  @Override
  public FieldSetterMethodSelector setterOf(Field field) {
    return super.setterOf(field).that(predicate);
  }

  @Override
  public ConstructorSelector constructor() {
    return super.constructor().that(predicate);
  }

  @Override
  public ConstructorsSelector constructors() {
    return super.constructors().that(predicate);
  }

  @Override
  public FieldSelector field(String name) {
    return super.field(name).that(predicate);
  }

  @Override
  public FieldSelector field() {
    return super.field().that(predicate);
  }

  @Override
  public FieldsSelector fields() {
    return super.fields().that(predicate);
  }

  @Override
  public MethodSelector method(String name) {
    return super.method(name).that(predicate);
  }

  @Override
  public MethodSelector method() {
    return super.method().that(predicate);
  }

  @Override
  public MethodsSelector methods() {
    return super.methods().that(predicate);
  }

}
