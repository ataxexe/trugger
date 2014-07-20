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

package tools.devnull.trugger.iteration.impl;

import tools.devnull.trugger.iteration.DestinationSelector;
import tools.devnull.trugger.iteration.SourceSelector;
import tools.devnull.trugger.predicate.Predicate;
import tools.devnull.trugger.transformer.Transformer;

import java.util.Collection;
import java.util.Iterator;

/** @author Marcelo Guimarães */
public class CopyOperation implements SourceSelector {

  private final Predicate predicate;
  private Transformer transformer;

  public CopyOperation(Predicate predicate) {
    this.predicate = predicate;
  }

  @Override
  public SourceSelector as(Transformer transformer) {
    this.transformer = transformer;
    return this;
  }

  @Override
  public DestinationSelector from(final Collection src) {
    return new DestinationSelector() {
      @Override
      public void to(Collection dest) {
        if (predicate == null && transformer == null) {
          src.addAll(src);
        } else {
          Iterator iterator = src.iterator();
          while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (predicate == null || predicate.evaluate(obj)) {
              if (transformer != null) {
                obj = transformer.transform(obj);
              }
              dest.add(obj);
            }
          }
        }
      }
    };
  }
}
