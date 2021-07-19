package com.byted.camp.todolist.beans;

public enum State {
    // State有两种状态
    // TODO表示还未进行，Done状态表示已经完成
    TODO(0), DONE(1);

    public final int intValue;

    State(int intValue) {
        this.intValue = intValue;
    }

    public static State from(int intValue) {
        for (State state : State.values()) {
            if (state.intValue == intValue) {
                return state;
            }
        }
        return TODO; // default
    }
}
