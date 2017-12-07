package com.github.insanusmokrassar.IObjectK.realisations

import java.util.concurrent.ConcurrentHashMap

class ConcurrentSimpleCommonIObject<K, V> : SimpleCommonIObject<K, V>(
        ConcurrentHashMap<K, V>()
)