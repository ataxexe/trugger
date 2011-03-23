/*
 * Copyright 2009-2011 Marcelo Varella Barca Guimarães
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
package net.sf.trugger.validation.impl;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.trugger.validation.Validator;

/**
 * @author Marcelo Varella Barca Guimarães
 */
final class CompositeValidator implements Validator {
  
  private final Collection<Validator> validators;
  
  public CompositeValidator() {
    this.validators = new ArrayList<Validator>();
  }
  
  public void add(Validator validator) {
    validators.add(validator);
  }
  
  public boolean isValid(Object value) {
    for (Validator validator : validators) {
      if(!validator.isValid(value)) {
        return false;
      }
    }
    return true;
  }
  
}
