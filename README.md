# Overview

Trugger is a framework that helps you write code that anyone can read. The
idea is to provide a set of fluent interfaces to deal with operations related
to reflection (such as creating proxies, reflecting members and scanning
classes)

## What a hell does the name trugger mean?

When I was learning java, I choosed "Atatec" (*Ataxexe Technology*) to be the
name of my fictitious company and then I sent a message to my friend Ives:

> Atatec

And the response was:

> trugger

When I asked him why he came up with this, he said:

> Isn't that the name of that special kick in Street Fighter?

The special kick mentioned is the *Tatsumaki Senpuu Kyaku* and Ken says
something like "atatec trugger" (at least in Portuguese) when he does the kick.
So the name Trugger became my first choice when I start to write this framework.

## How To Use

Just put the jar file on your **classpath** and you're done. No dependencies
are required by Trugger at runtime.

## How To Build

Just make sure you have [Gradle][]. The markdown files are converted at build
 time using [pandoc][] so you should have it too.

[gradle]: <http://gradle.org>
[pandoc]: <http://johnmacfarlane.net/pandoc/README.html>

## How To Contribute

Just fork the project, do some stuff and send me a pull request. You can also
fire an issue or tell your friends to use Trugger.

## About Maven Repository

Trugger currently is not in the Maven Central Repository, but a pom is generated
and distributed in the download so you can import it on your local repository.

# Reflection

The Reflection module was the first one to show up in Trugger, it allows simple
and much less verbose use of the Reflection API to reflect constructors,
methods, fields and generic type declarations.

The base class of this module is `org.atatec.trugger.reflection.Reflection`
and you will find a nice fluent interface there with the methods `reflect`,
`invoke` and `handle`.

## Fields

### Single

Reflecting a field is done with the code `reflect().field(field name)`. You can
also apply a filter by using the method `filter` and the target class (or
object) by using the method `in`. If you need a recursive search through the
entire target class hierarchy, just use the method `deep`.

Here is some examples (assuming a `static import`):

```java
Field value = reflect().field("value").in(String.class);

Field name = reflect().field("name").deep().in(MyClass.class);

Field id = reflect().field("id")
    .filter(field -> field.getType().equals(Long.class))
    .in(someInstance);
```

The `filter` method receive a `java.util.function.Predicate` to stay in touch
with Java 8.

### Multiple

If you need to reflect a set of fields, use the `reflect().fields()`. The same
features of a single reflect is available here.

~~~java
List<Field> stringFields = reflect().fields()
    .filter(field -> field.getType().equals(String.class))
    .deep()
    .in(MyClass.class);
~~~

### Predicates

There are some builtin predicates in the class
`org.atatec.trugger.reflection.FieldPredicates`.

~~~java
List<Field> stringFields = reflect().fields()
    .filter(type(String.class)) // a static import
    .deep()
    .in(MyClass.class);
~~~

### Handling Values

A field can have its value manipulated through the method `Reflection#handle`.
It will create a handler to set and get the field's value without the verbosity
of the Reflection API. To handle static fields, you can call the handler methods
directly:

~~~java
String value = handle(field).get();
handle(field).set("new value");
~~~

For instance fields, just specify an instance using the method `in`:

~~~java
String value = handle(field).in(instance).get();
handle(field).in(instance).set("new value");
~~~

You can also use a a `FieldSelector`:

~~~java
// static import Reflection#field
String value = handle(field("name")).in(instance).value();
~~~

## Constructors

### Single

To reflect a constructor, use `reflect().constructor()` and specify the
parameter types and optionally a filter. If the class has only one constructor,
it can be reflected without supplying the parameter types.

~~~java
Constructor constructor = reflect()
  .constructor().withParameters(String.class).in(MyClass.class);

Constructor constructor = reflect().constructor()
  .withoutParameters().in(MyClass.class);

Constructor constructor = reflect().constructor()
  .filter(c -> c.isAnnotationPresent(SomeAnnotation.class))
  .withParameters(String.class).in(MyClass.class);

Constructor constructor = reflect().constructor().in(MyClass.class)
~~~

### Multiple

A set of constructors can be reflected by using `reflect().constructors()`. As
in fields, the features in single reflection are present in multiple reflection.

~~~java
List<Constructor> constructors = reflect().constructors().in(MyClass.class);

List<Constructor> constructors = reflect().constructors()
  .filter(c -> c.getParameterCount() == 2)
  .in(MyClass.class);
~~~

Note that in multiple selection you cannot specify the parameter types directly
in the fluent interface (for obvious reasons).

### Predicates

A few useful predicates are included in the class
`org.atatec.trugger.reflection.ConstructorPredicates`

~~~java
List<Constructor> constructors = reflect().constructors()
  .filter(annotated())
  .in(MyClass.class);
~~~

### Invocation

To invoke a constructor you need an `Invoker`. The method `Reflection#invoke`
returns a Invoker for a constructor. Specify the parameters and the constructor
will be invoked.

~~~java
Constructor c = reflect().constructor()
  .withParameters(String.class).in(String.class)
String name = invoke(c).withArgs("Trugger");
~~~

## Methods

### Single

To reflect a single method, just pass it name to `Reflection#method`. Filtering
is allowed and you can specify parameter types too.

~~~java
Method toString = reflect().method("toString").in(Object.class);

Method remove = reflect().method("remove")
  .withParameters(Object.class, Object.class)
  .in(Map.class);

Method someMethod = reflect().method("foo")
  .filter(method -> method.isAnnotationPresent(PostConstruct.class))
  .in(instance);
~~~

As in field reflection, you can do a deep search with `deep`.

~~~java
Method toString = reflect().method("toString").deep().in(MyClass.class);
~~~

### Multiple

A set of methods can be reflected by using `Reflection#methods`:

~~~java
List<Method> methods = reflect().methods().in(Object.class);
~~~

Deep search and filtering are also supported:

~~~java
List<Method> methods = reflect().methods()
  .filter(method -> method.isAnnotationPresent(PostConstruct.class)
  .deep()
  .in(MyClass.class);
~~~

### Predicates

A set of predicates to deal with methods is in
`org.atatec.trugger.reflection.MethodPredicates`:

~~~java
List<Method> methods = reflect().methods()
  .filter(annotatedWith(PostConstruct.class))
  .deep()
  .in(MyClass.class);
~~~

### Invocation

To invoke a method, use the Invoker returned by `Reflection#invoke`. Instance
methods needs an instance provided using the method `in`:

~~~java
Method toString = reflect().method("toString").in(String.class);
invoke(toString).in("A string").withoutArgs();
~~~

Static methods don't need it:

~~~java
Method parseInt = reflect().method("parseInt")
  .withParameters(String.class)
  .in(Integer.class);
int number = invoke(parseInt).withArgs("10");
~~~

Note that you can also use a `MethodSelector`:

~~~java
Method toString = reflect().method("toString").in(String.class);
invoke(toString).in("A string").withoutArgs();
~~~

## Generic Type

Generic declarations in a class are present in the bytecode. Trugger can reflect
them by using the method `genericType`. Suppose we have this interface:

~~~java
public interface Repository<T> {
  // ... some useful methods here
}
~~~

A generic base implementation can use that **T**:

~~~java
public class BaseRepository<T> {

  private final Class<T> type;

  protected BaseRepository() {
    this.type = reflect().genericType("T").in(this);
  }
}
~~~

The constructor was declared `protected` to warn that this will only work for
subclasses (it is a Java limitation). A workaround to use this trick in a
variable-like way is by declaring an anonymous class:

~~~java
Repository<MyType> repo = new BaseRepository<MyType>(){};
~~~

I think this is an ugly solution, but works.

# Class Scanning

Another cool feature Trugger has is the class scanning. Just give a package name
and it scans for any classes in there. The class scanning feature starts at
`org.atatec.trugger.scan.ClassScan`.

The scanning starts in the method `ClassScan#scan`, which returns a
`ClassScanner` that allows changing the ClassLoader and defining the type of
scan.

~~~java
// scans everything in the package
List<Class> classes = scan().classes().in("my.package");
// scans everything in the package and its subpackages
List<Class> classes = scan().allClasses().in("my.package");
~~~

## Predicates

## Defining a Protocol Handler

# Proxy Creation

# Elements of an Object

## What is an Element?

## Creating a Custom Element Finder

# Utilities

## Annotation Mock

# Extending

## How To Implement the Fluent Interfaces

The fluent interfaces are always defined through java interfaces and may be
customized by your own implementation. Trugger uses a `ServiceLoader` to load
a factory that knows the implementations to instantiate,
so you can override the implementation of any fluent interface by defining a
file in your **META-INF/services** directory with the factory implementation.

The factory interfaces that can be customized are listed bellow:

- `org.atatec.trugger.element.ElementFactory`: used for reflecting elements
- `org.atatec.trugger.property.PropertyFactory`: used for reflecting properties
- `org.atatec.trugger.reflection.ReflectionFactory`: used for reflection in
    general
- `org.atatec.trugger.scan.ClassScannerFactory`: used for class scanning
- `org.atatec.trugger.interception.InterceptorFactory`: used for method
  interception
