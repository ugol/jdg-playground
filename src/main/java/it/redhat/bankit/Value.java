package it.redhat.bankit;

import java.io.Serializable;

public class Value implements Serializable {

    public Value(String val) {
      this.val = val;
    }

    public Value setVal(String val) {
        this.val = val;
        return this;
    }

    @Override
    public String toString() {
        return val;
    }

    private String val;

}
