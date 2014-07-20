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
package tools.devnull.trugger.reflection.impl;

import tools.devnull.trugger.Invoker;
import tools.devnull.trugger.exception.ExceptionHandler;
import tools.devnull.trugger.exception.ExceptionHandlers;
import tools.devnull.trugger.reflection.MethodInvoker;
import tools.devnull.trugger.reflection.Reflection;
import tools.devnull.trugger.reflection.ReflectionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A default implementation for invoking {@link Method} objects.
 *
 * @author Marcelo Guimarães
 */
public class TruggerMethodInvoker implements MethodInvoker {

  private final Method method;

  private Object instance;

  private ExceptionHandler handler = ExceptionHandlers.DEFAULT_EXCEPTION_HANDLER;

  public TruggerMethodInvoker(final Method method) {
    if (!method.isAccessible()) {
      Reflection.setAccessible(method);
    }
    this.method = method;
  }

  public MethodInvoker in(Object instance) {
    this.instance = instance;
    return this;
  }

  public <E> E withArgs(Object... args) {
    try {
      try {
        return (E) method.invoke(instance, args);
      } catch (InvocationTargetException e) {
        throw new ReflectionException(e.getCause());
      } catch (IllegalAccessException e) {
        throw new ReflectionException(e);
      } catch (IllegalArgumentException e) {
        throw new ReflectionException(e);
      }
    } catch (RuntimeException e) {
      handler.handle(e);
      return null;
    }
  }

  public <E> E withoutArgs() {
    return (E) withArgs();
  }

  @Override
  public Invoker handlingExceptionsWith(ExceptionHandler handler) {
    this.handler = handler;
    return this;
  }

}
