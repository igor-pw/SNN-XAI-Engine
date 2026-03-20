package core;

import structure.Scalar;

public class Pool
{
    private int pointer = 0;
    private Scalar[] store;

    public Pool(int size) {
        store = new Scalar[size];
        for(int i = 0; i < size; i++) {
            store[i] = new Scalar();
        }
    }

    public Scalar borrow() {
        if(pointer >= store.length) {
            doubleSize();
            initEmptyStore();
        }

        return store[pointer++];
    }

    private void doubleSize() {
        Scalar[] newStore = new Scalar[store.length*2];
        System.arraycopy(store, 0, newStore, 0, store.length);
        store = newStore;
    }

    private void initEmptyStore() {
        for(int i = pointer; i < store.length; i++) {
           store[i] = new Scalar();
        }
    }

    public int getPointer() { return pointer; }
}
