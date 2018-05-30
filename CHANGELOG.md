# Changelog

## v1.1

Added [SimpleTypedIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectK/realisations/SimpleTypedIObject.kt)

## v1.3

* Add methods [put](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/IObjectK.kt#14), [get](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/IObjectK.kt#40) and [cut](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/IObjectK.kt#51) which work with paths as with lists.
* Add method to [convert string as "/path/to/something" to list](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/IObjectK.kt#)

## v1.4 (important, be careful)

* Change type of IObject - now it is typealias of CommonIObject<String, T>.
* Add [SimpleCommonIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectK/realisations/SimpleCommonIObject.kt)
and reorganize dependency hierarchy (watch commit).

## v1.5

* Make ConcurrentSimpleIObject -> [ConcurrentSimpleCommonIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectK/realisations/ConcurrentSimpleCommonIObject.kt)
to be useful in most cases.
* Return IObject interface status.

## v1.6

* Add [IInputObject as map](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/IInputObject.kt) method. Now
you can get Map which will map actions to your object.
* Add [CommonIObject as map](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/CommonIObject.kt) method. Now
you can get MutableMap which will map actions to your mutable object.
* Add [`val size` to IInputObject](src/main/kotlin/com/github/insanusmokrassar/IObjectK/interfaces/IInputObject.kt). Now
this interface have default value, but strongly recommended to override this val
in your objects. In future versions default value will be removed.

## v1.7

* Update version of Kotlin 1.2.10 -> 1.2.20
* Add [IInputObject<K, V>#contain](src/main/kotlin/com/github/insanusmokrassar/IObjectK/interfaces/IInputObject.kt#L30)
extension
* Rename [IOutputObject<K, V>#addAll -> IOutputObject<K, V>#putAll](src/main/kotlin/com/github/insanusmokrassar/IObjectK/interfaces/IOutputObject.kt#L37)

## v1.8

* Add [operators (+, +=, -, -=, in)](src/main/kotlin/com/github/insanusmokrassar/IObjectK/utils/Operators.kt)
* Fix behaviour of `SimpleIObject` for null set values
* Replace `put` by `set`. Now `put` method is deprecated and replaced by `put` (`[]=`) (
[link](src/main/kotlin/com/github/insanusmokrassar/IObjectK/interfaces/IOutputObject.kt#L28))
* `IInputObject` [now is `Iterable<Pair<K, V>>`](src/main/kotlin/com/github/insanusmokrassar/IObjectK/interfaces/IInputObject.kt#L6).
You can use [StandardIInputObjectIterator](src/main/kotlin/com/github/insanusmokrassar/IObjectK/realisations/StandardIInputObjectIterator.kt)
if you need not some special extends.

## v1.9

* Remove implementation of Iterable for IInputObject's (you can use [StandardIInputObjectIterator](src/main/kotlin/com/github/insanusmokrassar/IObjectK/realisations/StandardIInputObjectIterator.kt)
or [IInputObject#iterator](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/IInputObject.kt#L15))

## 1.9.1

* Added [CommonIObject<String, in Any>#remap](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/CommonIObject.kt#113)
* Added [CommonIObject<String, in Any>#toJsonString](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/CommonIObject.kt#168)

## 1.9.2

* Added in previous update [CommonIObject<String, in Any>#remap](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/CommonIObject.kt#113)
change logic of handling of arrays: now arrays represent multiple variants of paths

## 1.10

* All extensions of `IObject<Any>` was replaced and resigned into `CommonIObject<String, Any>`.

## 1.10.1

* Fixes in `remap` extension

## 1.10.2

* Fixes in `remap` extension (fix case when fied is absent for remap)

## 1.10.3

* Add [IObject<Any>#doRecursively](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/IObject.kt)

## 1.10.4

* Fixes in toJsonString method

## 1.10.5

* Fixes in get/put/cut methods to be available to work with CommonIObjects and other low-level interfaces

## 1.10.6

* Add `IInputObject#duplicate`
([IInputObject extensions](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/IInputObject.kt)) operation

## 1.11

* Replace `IInputObject#iterator` extension into [IInputObject interface](src/main/kotlin/com/github/insanusmokrassar/IObjectK/interfaces/IInputObject.kt)
* Remove deprecated `IOutputObject#put` fun and `IInputObject#contain`
* Fix handling get/set objects in `SimpleCommonIObject`. Now if was found any error will be thrown `ReadException`/`WriteException`

## 1.11.1

* Extension `IInputObject#toJsonString`
* `IInputObject#toString` by default will return `IInputObject#toJsonString`

## 1.11.2

* Fix of `SimpleCommonIObject#toString` - now will be used `IInputString#toJsonString`

## 1.11.3

* Add default realisation for `SimpleCommonIObject#toString`
