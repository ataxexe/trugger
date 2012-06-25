/*
 * Copyright 2009-2012 Marcelo Varella Barca Guimarães
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

package org.atatec.trugger.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** @author Marcelo Varella Barca Guimarães */
public class ClassIterator implements Iterator<Class> {

  private Class target;

  public ClassIterator(Object target) {
    this(Utils.resolveType(target));
  }

  public ClassIterator(Class target) {
    this.target = target;
  }

  @Override
  public boolean hasNext() {
    return target != null;
  }

  @Override
  public Class next() {
    if (target == null) {
      throw new NoSuchElementException();
    }
    Class c = target;
    target = c.getSuperclass();
    return c;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
