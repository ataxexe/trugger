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

import tools.devnull.trugger.Result;
import tools.devnull.trugger.reflection.ReflectionException;
import tools.devnull.trugger.reflection.Reflector;
import tools.devnull.trugger.selector.ConstructorSelector;
import tools.devnull.trugger.selector.ConstructorsSelector;
import tools.devnull.trugger.selector.FieldGetterMethodSelector;
import tools.devnull.trugger.selector.FieldSelector;
import tools.devnull.trugger.selector.FieldSetterMethodSelector;
import tools.devnull.trugger.selector.FieldsSelector;
import tools.devnull.trugger.selector.GetterMethodSelector;
import tools.devnull.trugger.selector.MethodSelector;
import tools.devnull.trugger.selector.MethodsSelector;
import tools.devnull.trugger.selector.SetterMethodSelector;
import tools.devnull.trugger.util.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of the reflection operations.
 *
 * @author Marcelo Guimarães
 */
public class TruggerReflector implements Reflector {

  private MemberFindersRegistry registry;

  public TruggerReflector(MemberFindersRegistry defaultRegistry) {
    this.registry = defaultRegistry;
  }

  public TruggerReflector() {
    declared();
  }

  @Override
  public final Reflector visible() {
    registry = new VisibleMemberFindersRegistry();
    return this;
  }

  @Override
  public final Reflector declared() {
    registry = new DeclaredMemberFindersRegistry();
    return this;
  }

  public GetterMethodSelector getterOf(String name) {
    return new TruggerGetterMethodSelector(name, registry.methodsFinder());
  }

  public FieldGetterMethodSelector getterOf(Field field) {
    return new TruggerFieldGetterMethodSelector(field, registry.methodsFinder());
  }

  public SetterMethodSelector setterOf(String name) {
    return new TruggerSetterMethodSelector(name, registry.methodsFinder());
  }

  public FieldSetterMethodSelector setterOf(Field field) {
    return new TruggerFieldSetterMethodSelector(field, registry.methodsFinder());
  }

  public ConstructorSelector constructor() {
    return new TruggerConstructorSelector(registry);
  }

  public ConstructorsSelector constructors() {
    return new TruggerConstructorsSelector(registry.constructorsFinder());
  }

  public FieldSelector field(String name) {
    return new TruggerFieldSelector(name, registry);
  }

  public FieldSelector field() {
    return new TruggerNoNamedFieldSelector(registry);
  }

  public FieldsSelector fields() {
    return new TruggerFieldsSelector(registry.fieldsFinder());
  }

  public MethodSelector method(String name) {
    return new TruggerMethodSelector(name, registry);
  }

  public MethodSelector method() {
    return new TruggerNoNamedMethodSelector(registry);
  }

  public MethodsSelector methods() {
    return new TruggerMethodsSelector(registry.methodsFinder());
  }

  public Result<Set<Class<?>>, Object> interfaces() {
    return new Result<Set<Class<?>>, Object>() {

      private void loop(Class<?> interf, Set<Class<?>> set) {
        set.add(interf);
        for (Class<?> extendedInterfaces : interf.getInterfaces()) {
          loop(extendedInterfaces, set);
        }
      }

      public Set<Class<?>> in(Object target) {
        Class<?> objectClass = Utils.resolveType(target);
        Set<Class<?>> set = new HashSet<Class<?>>(30);
        for (Class<?> c = objectClass; (c != null) && !Object.class.equals(c); c = c.getSuperclass()) {
          for (Class<?> interf : c.getInterfaces()) {
            loop(interf, set);
          }
        }
        return set;
      }
    };
  }

  public Result<Class, Object> genericType(final String parameterName) {
    return new Result<Class, Object>() {

      public Class in(Object target) {
        return TruggerGenericTypeResolver.resolveParameterName(parameterName, Utils.resolveType(target));
      }
    };
  }

  public Result<Class, Object> genericType() {
    return new Result<Class, Object>() {

      public Class in(Object target) {
        Map<Type, Type> typeVariableMap = TruggerGenericTypeResolver.getTypeVariableMap(Utils.resolveType(target));
        Set<Type> keySet = typeVariableMap.keySet();
        Set<String> paramNames = new HashSet<String>(keySet.size());
        for (Type type : keySet) {
          paramNames.add(type.toString());
        }
        if (paramNames.isEmpty()) {
          throw new ReflectionException("No generic type found.");
        } else if (paramNames.size() > 1) {
          throw new ReflectionException("More than one generic type found.");
        }
        String name = paramNames.iterator().next();
        return genericType(name).in(target);
      }
    };
  }

  public Method bridgedMethodFor(Method bridgeMethod) {
    return new TruggerBridgeMethodResolver(bridgeMethod).findBridgedMethod();
  }

}
