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
package io.backpackcloud.trugger.element;

import java.util.List;
import java.util.function.Predicate;

/**
 * Interface that defines a selector for {@link Element} objects.
 *
 * @author Marcelo "Ataxexe" Guimarães
 */
public interface ElementsSelector {

  /**
   * Selects the elements that matches with the given predicate.
   *
   * @param predicate
   *          the predicate to match.
   * @return a new selector with the given filter
   */
  ElementsSelector filter(Predicate<? super Element> predicate);

  /**
   * Applies the selection using the given target
   *
   * @param target the target to apply the selection
   * @return the selected elements
   */
  List<Element> from(Object target);

}
