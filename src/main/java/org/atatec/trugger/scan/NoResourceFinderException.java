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

package org.atatec.trugger.scan;

/**
 * Exception thrown to indicate that there is no ResourceFinder mapped for a given
 * protocol.
 *
 * @author Marcelo Varella Barca Guimarães
 * @since 4.0
 */
public class NoResourceFinderException extends ClassScanningException {

  public NoResourceFinderException() {
  }

  public NoResourceFinderException(String message) {
    super(message);
  }

  public NoResourceFinderException(String format, Object... args) {
    super(format, args);
  }

  public NoResourceFinderException(Throwable cause, String format, Object... args) {
    super(cause, format, args);
  }

  public NoResourceFinderException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoResourceFinderException(Throwable cause) {
    super(cause);
  }

}