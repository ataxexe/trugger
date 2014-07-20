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
package tools.devnull.trugger.element.impl;

import tools.devnull.trugger.element.Element;
import tools.devnull.trugger.iteration.Iteration;
import tools.devnull.trugger.transformer.Transformer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A helper class for element finders.
 *
 * @author Marcelo Guimarães
 */
public class ElementFinderHelper {

	public static Set<Element> computeResult(Object target, Collection<Element> elements) {
		if (target instanceof Class<?>) {
			return new HashSet<Element>(elements);
		}
		Set<Element> result = new HashSet<Element>(elements.size());
		Transformer<Element, Element> specific = new SpecificElementTransformer(target);
		Iteration.copy().as(specific).from(elements).to(result);
		return result;
	}

}
