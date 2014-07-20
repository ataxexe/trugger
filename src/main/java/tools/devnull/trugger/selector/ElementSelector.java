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
package tools.devnull.trugger.selector;

import tools.devnull.trugger.Result;
import tools.devnull.trugger.element.Element;
import tools.devnull.trugger.predicate.Predicate;

import java.lang.annotation.Annotation;

/**
 * Interface that defines a selector for {@link Element} objects.
 * 
 * @author Marcelo Guimarães
 */
public interface ElementSelector extends ElementSpecifier, Result<Element, Object> {
  
  ElementSelector readable();
  
  ElementSelector nonReadable();
  
  ElementSelector writable();
  
  ElementSelector nonWritable();
  
  ElementSelector annotated();
  
  ElementSelector notAnnotated();
  
  ElementSelector annotatedWith(Class<? extends Annotation> type);
  
  ElementSelector notAnnotatedWith(Class<? extends Annotation> type);
  
  ElementSelector ofType(Class<?> type);
  
  ElementSelector assignableTo(Class<?> type);
  
  ElementSelector that(Predicate<? super Element> predicate);
  
  ElementSelector specific();
  
  ElementSelector nonSpecific();
  
}
