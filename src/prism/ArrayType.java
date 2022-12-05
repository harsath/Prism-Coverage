package prism;

import java.io.Serializable;
import java.util.*;

public class ArrayType implements AtomType, Serializable {
        private List<AtomType> internal_array;
        private String type;

        public ArrayType(String type) {
                this.internal_array = new ArrayList<AtomType>();
        }

        public void setType(String type) {
                this.type = type;
        }

        public List<AtomType> getInternalArray() {
                return this.internal_array;
        }

        public void setInternalArray(List<AtomType> internal_array) {
                this.internal_array = internal_array;
        }

        public String getType() {
                return this.type;
        }

        public int getArraySize() {
                return this.internal_array.size();
        }

        public AtomType getItemAtIndex(int i) throws Exception {
                if (i >= internal_array.size()) {
                        throw new RuntimeException("Index out of bound for AT");
                }
                return internal_array.get(i);
        }

        public void putItem(AtomType item) {
                internal_array.add(item);
        }

        public AtomType removeAt(int i) throws Exception {
                if (i >= internal_array.size()) {
                        throw new RuntimeException("Index out ouf bound for REMOVEAT");
                }
                return internal_array.remove(i);
        }

        public void removeAll() {
                internal_array.clear();
        }

        public AtomType removeLastItem() throws Exception {
                if (internal_array.size() == 0) {
                        throw new RuntimeException("Cannot remove last item of size 0 array");
                }
                AtomType returner = internal_array.get(internal_array.size() - 1);
                internal_array.remove(internal_array.size() - 1);
                return returner;
        }

        @Override
        public String toString() {
                return this.type;
        }

}
