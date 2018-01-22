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

