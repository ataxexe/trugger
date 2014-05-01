/*
 * Copyright 2009-2014 Marcelo Guimarães
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

package org.atatec.trugger.validation.validator.impl;

import org.atatec.trugger.validation.Validation;
import org.atatec.trugger.validation.ValidationEngine;
import org.atatec.trugger.validation.Validator;
import org.atatec.trugger.validation.validator.NotNull;

/**
 * Validator that checks if the value is valid by validating its elements.
 *
 * @since 5.1
 */
public class ValidValidator implements Validator {

  private final ValidationEngine engine;

  /**
   * Creates a new validator using the {@link Validation default} engine to
   * validate the values.
   */
  public ValidValidator() {
    this(new Validation());
  }

  /**
   * Creates a new validator using the given engine to validate the values.
   *
   * @param engine the engine to validate the values
   */
  public ValidValidator(ValidationEngine engine) {
    this.engine = engine;
  }

  @Override
  public boolean isValid(@NotNull Object value) {
    return engine.validate(value).isValid();
  }

}
