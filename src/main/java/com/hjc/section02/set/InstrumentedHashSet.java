package com.hjc.section02.set;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

/**
 * @version v1.0
 * @Description: 扩展hashSet
 * @Author: hanjianchun@shouqiev.com
 * @Date: 2020-09-22 19:31
 */
public class InstrumentedHashSet<E> extends HashSet<E> {
    private int addCount = 0;

    public InstrumentedHashSet(){

    }

    @Override
    public boolean add(E e) {
        addCount ++;
        return super.add(e);
    }

    @Override
    public int size(){
        return addCount;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    @Override
    public boolean remove(Object o) {
        addCount --;
        return super.remove(o);
    }

    @Override
    public void clear() {
        addCount = 0;
        super.clear();
    }
}
