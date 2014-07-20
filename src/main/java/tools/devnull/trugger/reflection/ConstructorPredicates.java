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

package tools.devnull.trugger.reflection;

import tools.devnull.trugger.predicate.CompositePredicate;
import tools.devnull.trugger.predicate.Predicate;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import static tools.devnull.trugger.predicate.Predicates.wrap;

/**
 * A set of predicates to use with <code>Constructor</code> objects.
 *
 * @author Marcelo Guimarães
 * @since 4.1
 */
public class ConstructorPredicates {

  private ConstructorPredicates() {
  }

  /**
   * @return a predicate that returns <code>true</code> if the evaluated constructor takes
   *         the specified parameters.
   */
  public static CompositePredicate<Constructor> takes(final Class... parameterTypes) {
    return wrap(new Predicate<Constructor>() {

      public boolean evaluate(Constructor element) {
        return Arrays.equals(element.getParameterTypes(), parameterTypes);
      }

      @Override
      public String toString() {
        return "With parameters " + Arrays.toString(parameterTypes);
      }
    });
  }

}
