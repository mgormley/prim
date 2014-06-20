package edu.jhu.prim.map;

/**
 * An entry in a primitives map from longs to ints.
 * @author mgormley
 */
public interface LongIntEntry {
	long index();
	int get();
}