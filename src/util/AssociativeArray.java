package edu.grinnell.csc207.util;

import static java.lang.reflect.Array.newInstance;
/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @param <K> the key type
 * @param <V> the value type
 *
 * @author Sam Schmidt
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V>[] pairs;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   *
   * @return a new copy of the array
   */
  public AssociativeArray<K, V> clone() {
    // KVPair<K, V>[] clone;
    AssociativeArray<K, V> clone = new AssociativeArray<K, V>();
    for (int i = 0; i < size; i++) {
      try {
        clone.set(pairs[i].key, pairs[i].val);
      } catch (NullKeyException e) {
        System.err.println("Key not found");
      } // try/catch
    } // for

    return clone;
  } // clone()

  /**
   * Convert the array to a string.
   *
   * @return a string of the form "{Key0:Value0, Key1:Value1, ... KeyN:ValueN}"
   */
  public String toString() {
    String arrayString = "{";
    for (int i = 0; i < size; i++) {
      String key = pairs[i].key.toString();
      String val = pairs[i].val.toString();
      if (i == size - 1) {
        arrayString = arrayString + key + ":" + val;
      } else {
        arrayString = arrayString + key + ":" + val + ", ";
      } //elseif
    } // for
    arrayString = arrayString.concat("}");
    return arrayString;
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   *
   * @param key
   *   The key whose value we are seeting.
   * @param value
   *   The value of that key.
   *
   * @throws NullKeyException
   *   If the client provides a null key.
   */
  public void set(K key, V value) throws NullKeyException {
    if (key == null) {
      throw new NullKeyException();
    } //endif
    size++;
    if (size > pairs.length) {
      expand();
    } //endif
    int found = find(key);
    if (found != -1) {
      pairs[found] = (new KVPair<K, V>(key, value));
      size--;
    } else {
      int index = 0;
      while (pairs[index] != null) {
        index++;
      } //while
      pairs[index] = (new KVPair<K, V>(key, value));
    } // end ifelse
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @param key
   *   A key
   *
   * @throws KeyNotFoundException
   *   when the key is null or does not appear in the associative array.
   *
   * @return V
   *  The value associated with the key
   */
  public V get(K key) throws KeyNotFoundException {
    int found = find(key);
    try {
      return pairs[found].val;
    } catch (Exception e) {
      throw new KeyNotFoundException();
    } // try/catch
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   *
   * @param key
   *  The key searching for.
   *
   * @return boolean
   *  True if found, false if not.
   */
  public boolean hasKey(K key) {
    int found = find(key);
    if (found == -1) {
      return false;
    } else {
      return true;
    } //endif
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) {
    int found = find(key);
    if (found == -1) {
    } else {
      size--;
      pairs[found] = null;
    } //endif
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   * @return
   *  The amount of elements in the array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   *
   * @param key
   *   The key of the entry.
   *
   * @throws KeyNotFoundException
   *   If the key does not appear in the associative array.
   *
   * @return int
   *  The index of the key
   */
  int find(K key) {
    String stringKey = key.toString();
    for (int i = 0; i < size; i++) {
      if (pairs[i] == null) {
        i++;
        if (i >= size) {
          return -1;
        } //endif
      } //endif
      if (pairs[i].key.toString().equals(stringKey)) {
        return i;
      } //endif
    } // for
    return -1;
  } // find(K)
} // class AssociativeArray
