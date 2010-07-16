/*
 * Copyright 2009-2010 Marcelo Varella Barca Guimarães
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
package net.sf.trugger.format;

import net.sf.trugger.loader.ImplementationLoader;

/**
 * Class for handling format operations.
 *
 * @author Marcelo Varella Barca Guimarães
 * @since 2.7
 */
public class Formatters {

  private static final FormatterFactory factory;

  static {
    factory = ImplementationLoader.getInstance().get(FormatterFactory.class);
  }

  private Formatters() {}

  /**
   * @return the shared factory.
   */
  public static FormatterFactory factory() {
    return factory;
  }

  /**
   * @return a new formatter factory.
   */
  public static FormatterFactory newFactory() {
    return ImplementationLoader.getInstance().get(FormatterFactory.class);
  }

}
