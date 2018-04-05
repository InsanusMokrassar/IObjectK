# IObject

## How to import?

```xml
<dependency>
    <groupId>com.github.insanusmokrassar</groupId>
    <artifactId>IObjectK</artifactId>
    <version>1.9</version>
</dependency>
```

```
compile 'com.github.insanusmokrassar:IObjectK:1.9'
```

Optionally, you can replace version by one of next:

* 1.1
* 1.3
* 1.4
* 1.5
* 1.6
* 1.7
* 1.8
* 1.9
    * 1.9.1
    * 1.9.2

## What for?
This repository was created for give an opportunity to simplify using objects such as:

1. JSON
2. Map/HashMap 
3. Databases (simple)
4. Some web/local databases via sockets
5. Some web/local connections
6. Common databases

### JSON and Map/HashMap
JSON - format of data such as
```json
{
  "firstField" : "firstValue",
  "secondField" : [
    "it",
    "is",
    "second",
    "value",
    "as",
    "array"
  ],
  "thirdField" : {
    "it is" : "inner json"
  }
}
```

It's can be converted to [simple iobject](src/main/java/com/github/insanusmokrassar/iobjectk/realisations/SimpleIObject.java).

### Databases (simple)
In simple ways database it is repository which contain key-value pairs. It seems that we can create some simple IOStream for work with this repository.

### Databases
For common and complicated databases we have [CommonIObject](src/main/java/com/github/insanusmokrassar/iobjectk/interfaces/CommonIObject.java)

This interface can be used as:
```java
import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject;
import com.github.insanusmokrassar.IObjectK.exceptions.ReadException;

public class SomeDatabaseStream implements CommonIObject<YourCustomClassAsChooser, Object> {
    //Body of class
    @Override
    public void get(YourCustomClassAsChooser chooser) throws InputException{
        return chooser.choose();
    }
}

public class YourCustomClassAsChooser {
    protected Object[] arguments;
    
    public YourCustomClassAsChooser(Object... argumentsToChoose) {
        this.arguments = argumentsToChoose;
    }
    
    public Object choose() {
        //some choose strategy
        return result;
    }
}
```

As you can see, you can realise any strategy to get your values - it seems that if you want to add new feature - you may not override old system

## Safe operations
If you watch to dependency tree, you can see that [CommonIObject](src/main/java/com/github/insanusmokrassar/iobjectk/interfaces/CommonIObject.java) is extend to classes:

* [IInputObject](src/main/java/com/github/insanusmokrassar/iobjectk/interfaces/IInputObject.java)
* [IOutputObject](src/main/java/com/github/insanusmokrassar/iobjectk/interfaces/IOutputObject.java)

And all heirs of [CommonIObject](src/main/java/com/github/insanusmokrassar/iobjectk/interfaces/CommonIObject.java) can put in other methods as safety objects.

For example, if you have some user interface that must only read from database, you can do that as:
```java
import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject;
import com.github.insanusmokrassar.IObjectK.exceptions.ReadException;

public class ExampleClass {
    /**
    * this method can be put instance of SomeDatabaseStream, 
    * but methods to change database state is not accessible for class
    */
    public void someMethod(IInputObject<YourCustomClassAsChooser, Object> inputObject) {
        System.out.println(inputObject.get(someKeyInObject));
    }
}
```
