# Changelog

## v1.3

* Add methods [put](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/IObjectK.kt#14), [get](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/IObjectK.kt#40) and [cut](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/IObjectK.kt#51) which work with paths as with lists.
* Add method to [convert string as "/path/to/something" to list](src/main/kotlin/com/github/insanusmokrassar/IObjectK/extensions/IObjectK.kt#)

## v1.4 (important, be careful)

* Change type of IObject - now it is typealias of CommonIObject<String, T>.
* Add [SimpleCommonIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectK/realisations/SimpleCommonIObject.kt)
and reorganize dependency hierarchy (watch commit).

