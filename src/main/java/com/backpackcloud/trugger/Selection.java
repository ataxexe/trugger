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

package com.backpackcloud.trugger;

/**
 * Interface that represents a selection.
 *
 * @param <T> the type of the selected object
 * @since 6.0
 */
public interface Selection<T> {

  /**
   * Returns the result of the selection.
   *
   * @return the result of the selection.
   */
  T result();

  /**
   * Returns the target in which the selection was done.
   *
   * @return the target in which the selection was done.
   */
  Object target();

}
