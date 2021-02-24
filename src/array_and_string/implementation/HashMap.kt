package array_and_string.implementation

/* https://github.com/PacktPublishing/Hands-On-Data-Structures-and-Algorithms-with-Kotlin/blob/master/Chapter05/HashMap.kt */

class HashMap<K, V> {
    /*
    Reason why choose power of 2 is to make the bucket index calculation
    a bit easier with bitwise operators.

    1 shl 4 = 10000 = 16 = 2.pow(4)
    */
    private val minCapacity = 1 shl 4
    private val maxCapacity = 1 shl 30

    // HashTable
    private var table: Array<Node<K, V>?>

    // defines the number of entries (key-value pairs) present in HashMap at any particular time.
    var size = 0
        private set

    constructor() {
        this.table = arrayOfNulls(minCapacity)
    }

    /*
    * creates an array with a bucket size in the range of minCapacity and maxCapacity.
    * Note that if the capacity passed is not a power of two,
    * we get the nearest capacity from the fetchNearestCapacity method
    *
    * */
    constructor(capacity: Int) {
        if (capacity < 0) throw IllegalArgumentException("Invalid Capacity: $capacity")
        val finalCapacity = when {
            capacity < minCapacity -> minCapacity
            capacity > maxCapacity -> maxCapacity
            else -> fetchNearestCapacity(capacity)
        }
        this.table = arrayOfNulls(finalCapacity)
    }

    constructor(map: Map<K, V>) {
        val size = map.size
        val newSize = when {
            size < minCapacity -> minCapacity
            else -> fetchNearestCapacity(size)
        }
        this.table = arrayOfNulls(newSize)
        if (size > 0) {
            for (entry in map) {
                putVal(entry.key, entry.value)
            }
        }
    }

    private fun hash(key: K): Int {
        // use hashCode so we can generate hash value from Any type
        val h = key?.hashCode() ?: 0
        // do bit calculation to make it unique and reduce the possibility of a hash collision
        return h xor (h ushr 16)
    }

    fun isEmpty() = size == 0

    fun getOrDefault(key: K, defaultVal: V): V? {
        val e = getNode(hash(key), key)
        return if (e == null) defaultVal else e.value
    }

    fun put(key: K, value: V) {
        putVal(key, value)
    }

    fun putIfAbsent(key: K, value: V) {
        putVal(key, value, true)
    }

    fun replace(key: K, value: V) {
        val e = getNode(hash(key), key)
        if (e != null) e.value = value
    }

    fun clear() {
        if (size > 0) {
            size = 0
            table.fill(null)
        }
    }

    // finding the bucket index and then doing a LinkedList search until you find the required node to put the value.
    private fun putVal(key: K, value: V, onlyIfAbsent: Boolean = false) {
        /* calculate the bucket index; Determining the bucket from the key section */
        val hash = hash(key)
        val n = table.size
        // and is bitwise operation
        val index = (n - 1) and hash

        /* get the head node of LinkedList associated with the bucket index calculated in the preceding step */
        var first = table[index]

        /* If no entries in HashMap with the given key. So, create a new Node and assign it to the bucket index */
        if (first == null) {
            table[index] = Node(hash, key, value, null)
            ++size
        } else {
            // head of linkedList is not null
            var k = first.key
            if (first.hash == hash
                    && k == key
                    && !onlyIfAbsent)
                /* we've found the required node, so assign the head's value with input value */
                first.value = value
            else {
                var node: Node<K, V>?
                while (true) {
                    first?.let { firstNotNull ->
                        node = firstNotNull.next

                        if (node == null) {
                            firstNotNull.next = Node(hash, key, value, null)
                            return
                        }

                        node?.let { nodeNotNull ->
                            k = nodeNotNull.key
                            if (nodeNotNull.hash == hash
                                    && k == key
                                    && !onlyIfAbsent) {
                                nodeNotNull.value = value
                                return
                            }
                        }

                        first = node
                    }
                }
            }
        }
    }

    fun get(key: K): V? = getNode(hash(key), key)?.value

    private fun getNode(hash: Int, key: K): Node<K, V>? {
        val n = table.size
        if (n > 0) {
            //get the head node of LinkedList associated with the calculated bucket index
            val first = table[(n - 1) and hash]
            if (first != null) {
                // Checking the 1st node
                if (first.hash == hash && first.key == key) return first

                // if head details not the same as input then do linear search
                val e = first.next ?: return null
                do {
                    if (e.hash == hash && e.key == key) return e
                } while (e.next != null)
            }
        }
        // there's no bucket created yet so return null
        return null
    }

    fun remove(key: K): V? {
        /* calculate the bucket index and get the head node. */
        val hash = hash(key)
        val n = table.size
        val index = (n - 1) and hash

        // get the head
        var first = table[index]

        // If the head node is not null,
        // then check its details with the input's.
        if (n > 0 && first != null) {
            var node: Node<K, V>? = null
            var k = first.key
            if (first.hash == hash && key == k) node = first // remove head
            else {
                // If the details of the head node are not the same
                // as the input's, do a linear search until we find the required node, then remove it.
                var nextNode = first.next
                nextNode?.let {
                    do {
                        k = it.key
                        if (it.hash == hash && key == k) {
                            node = nextNode
                            break
                        }
                        first = nextNode
                        nextNode = it.next
                    } while (nextNode != null)
                }
            }

            node?.let {
                // make its next node the head of LinkedList of that particular bucket
                if (node == first) table[index] = it.next
                else first?.next = it.next
                --size
                return it.value
            }
        }
        return null
    }

    fun containsKey(key: K) = getNode(hash(key), key) != null

    fun containsValue(value: V): Boolean {
        if (size > 0) {
            for (index in table.indices) {
                var e = table[index]
                while (e != null) {
                    if (value == e.value) return true
                    e = e.next
                }
            }
        }
        return false
    }

    private fun fetchNearestCapacity(i: Int): Int {
        var retVal = i - 1 // If input is a power of two, shift its high-order bit right.
        // "Smear" the high-order bit all the way to the right.
        retVal = retVal or retVal ushr 1
        retVal = retVal or retVal ushr 2
        retVal = retVal or retVal ushr 4
        retVal = retVal or retVal ushr 8
        retVal = retVal or retVal ushr 16

        return retVal + 1
    }

    private class Node<K, V>(
            val hash: Int,
            val key: K,
            var value: V,
            var next: Node<K, V>?) {

        override fun toString() = "$key=$value"

        /* since HashMap is a generic class, we have to create a hash function that can generate hash value from the Any type. */
        /* we are also trying to do some bit calculation (XOR) to make hash more unique and reduce the possibility of a hash collision as much as possible. */
        override fun hashCode() = (key?.hashCode() ?: 0).xor(value?.hashCode() ?: 0)

        override fun equals(other: Any?): Boolean {
            if (other === this) return true
            if (other is Node<*, *> && this.key == other.key && this.value == other.value) return true
            return false
        }
    }
}