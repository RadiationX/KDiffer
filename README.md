[![Maven Central](https://img.shields.io/maven-central/v/ru.radiationx/kdiffer.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22ru.radiationx%22%20AND%20a:%22kdiffer%22)

# KDiffer
Simple declarative(?) object differ with Kotlin DSL

## Add library to a project

```groovy
allprojects {
  repositories {
    mavenCentral()
  }
}

dependencies {
    implementation "ru.radiationx:kdiffer:1.0.0"
}
```
## Examples
### Sample android app
No arch, only for see difference in real using. Look at DefaultPostItemViewHolder and DifferPostItemViewHolder.\
The example is very abstract, since no application will have data updates at a rate of 60 times per second and with such sub-optimal adapters. But in this example, you can clearly see how the affect is that unnecessary updates occur in the UI.\
**Highly recommended use release build type.** 

### Sample tests
Sample unit tests can run from sample-app module. For playground or if lazy for test on android.

### Live differs
Uses for do something by callbacks on changed fields
```kotlin
data class Model(val title: String, val sub: SubModel)
data class SubModel(val text: String, val size: Int)

val differ = mutableLiveDiffer<Model> {
    value { it.title } call { println("title: $it") }
    value { it.sub } withMutableDiffer {
        value { it.text } call { println("differ sub.text: $it") }
        value { it.size } call { println("differ sub.size: $it") }
        onClear { println("SubModel differ clear") }
    }
    value { it.sub.text } call { println("sub.text: $it") }
    onClear { println("Model differ clear") }
}
val model1 = Model("title1", SubModel("text1", 2))
val model2 = Model("title1", SubModel("text2", 2))
val model3 = Model("title3", SubModel("text2", 10))

differ.accept(model1)
>> title: title1
>> differ sub.text: text1
>> differ sub.size: 2
>> sub.text: text1

differ.accept(model2)
>> differ sub.text: text2
>> sub.text: text2

differ.accept(model3)
>> title: title3
>> differ sub.size: 10

differ.accept(model3)
>> NOTHING

differ.clear()
>> SubModel differ clear
>> Model differ clear
```

### Model differs
Uses for find changes in object with result as Map<String, *>. Fields without changes don't be exist in result.
```kotlin
data class Model(val title: String, val sub: SubModel)
data class SubModel(val text: String, val size: Int)

val differ = mutableModelDiffer<Model> {
    value("title") { it.title }
    value("sub") { it.sub } withMutableDiffer {
        value("text") { it.text }
        value("size") { it.size }
        onClear { println("SubModel differ clear") }
    }
    value("subtext") { it.sub.text }
    value("mapped_title") { it.title } map { "wow $it" }
    onClear { println("Model differ clear") }
}

val model1 = Model("title1", SubModel("text1", 2))
val model2 = Model("title1", SubModel("text2", 2))
val model3 = Model("title3", SubModel("text2", 10))

differ.accept(model1)
>> {title=title1, sub={text=text1, size=2}, mapped_title=wow title1}

differ.accept(model2)
>> {sub={text=text2}}

differ.accept(model3)
>> {title=title3, sub={size=10}, mapped_title=wow title3}

differ.accept(model3)
>> {}

differ.clear()
>> print SubModel differ clear
>> print Model differ clear
```

## Basics

### Mutable differs
"Mutable" differs keep last value and compare with new value
```kotlin
val differ1 = mutableLiveDiffer<Model> { /* live differ context */ }
val differ2 = mutableModelDiffer<Model> { /* live differ context */ }
differ1.accept(model)
differ2.accept(model)
```

### Stateless differs
"Stateless" differs don't keep last value. For comparing required two instances.
In case if in "value selector" performing too much work, this may be affect performance, because value selector invoked two times - for old and new model.
```kotlin
val differ1 = statelessLiveDiffer<Model> { /* live differ context */ }
val differ2 = statelessModelDiffer<Model> { /* live differ context */ }
differ1.accept(model1, model2)
differ2.accept(model1, model2)
```

### Live differ context syntax
```kotlin
mutableLiveDiffer<Model> {
    /* live differ context */
    
    // select field by lambda
    value { it.date } call { /* changes by equals */ }
    ref { it.date } call { /* changes by reference */ }
    any { it.date } call { /* any model change */ }
    
    value {
        // model value
        old
        new
        
        it.date
    } call {
        // model values
        parent.old
        parent.new
        
        // field values
        field.old
        field.new
    }

    // also possible use "ref" and "any"
    value { it.sub } withMutableDiffer {}
    value { it.sub } withStatelessDiffer {}
    value { it.sub }.registerMutableDiffer(anotherDiffer)
    value { it.sub }.registerStatelessDiffer(anotherDiffer)

    // something like ".apply {}"
    value { it.sub } withField {
        call {}
        withMutableDiffer {}
    }
}
```

### Model differ context syntax
```kotlin
mutableModelDiffer<Model> {
    /* model differ context */

    // select field by lambda
    value("val_date") { it.date } /* changes by equals */
    ref("ref_date") { it.date } /* changes by reference */
    any("any_date") { it.date } /* any model change */
    
    value("val_date_ts") {
        // model value
        old
        new
        
        it.date
    } map {
        // model values
        parent.old
        parent.new
        
        // field values
        field.old
        field.new
        
        it.time
    }

    // map field to something. also possible use "ref" and "any"
    value("val_date_mapped") { it.date } map { SimpleDateFormat("HH:mm").format(it) }

    // map field to Map<String, *> with differ. also possible use "ref" and "any"
    value("val_sub") { it.sub } withMutableDiffer {}
    value("val_sub") { it.sub } withStatelessDiffer {}
    value("val_sub") { it.sub }.registerMutableDiffer(anotherDiffer)
    value("val_sub") { it.sub }.registerStatelessDiffer(anotherDiffer)
}
```


### Clearing 
All differs can be cleared
```kotlin
val differ1 = mutableLiveDiffer<Model> { /* live differ context */ }
val differ2 = statelessLiveDiffer<Model> { /* live differ context */ }
val differ3 = mutableModelDiffer<Model> { /* live differ context */ }
val differ4 = statelessModelDiffer<Model> { /* live differ context */ }
differ1.clear()
differ2.clear()
differ3.clear()
differ4.clear()
```
Nested differs cleaning
```kotlin
val externalDiffer1 = mutableLiveDiffer<SubModel> {
    onClear { /* will be called */ }
}
val externalDiffer2 = mutableLiveDiffer<SubModel> {
    onClear { /* !!! will not be called  */ }
}
mutableLiveDiffer<Model> {
    /* live differ context */

    value { it.sub } withMutableDiffer {
        onClear { /* will be called */ }
    }
    value { it.sub } call {
        externalDiffer2.accept(it)
    }
    value { it.sub }.registerMutableDiffer(externalDiffer1)

    onClear { /* will be called */ }
}
```
