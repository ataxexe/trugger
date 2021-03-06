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

package com.backpackcloud.trugger.util.factory;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class DefaultContext implements Context {

  private List<Entry> entries;
  private Function<Parameter, Object> defaultFunction = parameter -> null;

  public DefaultContext() {
    this.entries = new ArrayList<>();
  }

  @Override
  public Mapper use(Function<Parameter, Object> function) {
    return new Mapper() {
      @Override
      public Context when(Predicate<? super Parameter> condition) {
        entries.add(new Entry(function, condition));
        return DefaultContext.this;
      }

      @Override
      public Context byDefault() {
        defaultFunction = function;
        return DefaultContext.this;
      }
    };
  }

  @Override
  public Optional<Object> resolve(Parameter parameter) {
    for (Entry entry : entries) {
      if (entry.predicate.test(parameter)) {
        return Optional.ofNullable(entry.function.apply(parameter));
      }
    }
    return Optional.ofNullable(defaultFunction.apply(parameter));
  }

  private static class Entry {

    private final Function<Parameter, Object> function;
    private final Predicate<? super Parameter> predicate;

    private Entry(Function<Parameter, Object> supplier,
                  Predicate<? super Parameter> predicate) {
      this.function = supplier;
      this.predicate = predicate;
    }

  }

}
