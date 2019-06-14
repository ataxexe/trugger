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

package io.backpackcloud.trugger.reflection;

import io.backpackcloud.trugger.util.Utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Predicate;

/**
 * A set of predicates to use with <code>Field</code> objects.
 *
 * @author Marcelo "Ataxexe" Guimarães
 * @since 4.1
 */
public interface FieldPredicates {

  /**
   * @return a predicate that returns <code>true</code> if a field is of the
   * given type.
   */
  static Predicate<Field> ofType(Class type) {
    return field -> type.equals(field.getType());
  }

  /**
   * @return a predicate that returns <code>true</code> if a field is assignable
   * to the given type.
   */
  static Predicate<Field> assignableTo(Class type) {
    return field -> Utils.areAssignable(type, field.getType());
  }

  /**
   * @return a predicate that returns <code>true</code> if a field has at least
   * one annotation.
   */
  static Predicate<Field> annotated() {
    return field -> field.getAnnotations().length > 0;
  }

  /**
   * @return a predicate that returns <code>true</code> if a field is annotated
   * with the given annotation.
   */
  static Predicate<Field> annotatedWith(
      Class<? extends Annotation> annotationType) {
    return field -> field.isAnnotationPresent(annotationType);
  }

}
