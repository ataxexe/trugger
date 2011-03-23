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
package net.sf.trugger.validation.validator.pt.br;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import net.sf.trugger.validation.ValidatorClass;

/**
 * Indicates that the annotated String must be a valid CEP.
 *
 * @author Marcelo Varella Barca Guimarães
 * @since 2.3
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValidatorClass(CEPValidator.class)
public @interface CEP {

  /**
   * The message for this validation or the name of the key that must be used to
   * get the message.
   */
  String message() default "validation.CEP";

  /**
   * The context that this validation belongs.
   */
  String[] context() default {};

}
