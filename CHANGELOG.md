# Changelog

### 1.1

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

## v1.6-experimental

Experimental version with replaced IObject interfaces by maps. Thinking, 
that we can use Map/MutableMap for safety when it need.
