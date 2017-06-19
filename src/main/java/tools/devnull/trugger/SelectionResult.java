/*
 * The Apache License
 *
 * Copyright 2009 Marcelo "Ataxexe" Guimarães <ataxexe@devnull.tools>
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

package tools.devnull.trugger;

/**
 * A class that represents the result of a query.
 *
 * @since 6.0
 */
public class SelectionResult<E> implements Optional<Selection<E>> {

  private final Object target;
  private final E value;

  public SelectionResult(Object target, E value) {
    this.target = target;
    this.value = value;
  }

  @Override
  public Selection<E> value() {
    return new Selection<E>() {
      @Override
      public E result() {
        return value;
      }

      @Override
      public Object target() {
        return target;
      }
    };
  }

  public E result() {
    return value;
  }

}
