package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {
    public final String name;
    private Map<String, List<String>> transitions = new HashMap<>();
    private boolean isCompleteState = false;

    private State(String name, boolean isCompleteState) {
        this.name = name;
        this.isCompleteState = isCompleteState;
    }

    public List<String> getTransitionsForSymbol(String symbol) {
        return transitions.get(symbol);
    }

    public static StateBuilder of(String name) {
        return new StateBuilder(name);
    }

    public boolean isCompleteState() {
        return isCompleteState;
    }

    public static class StateBuilder {
        private String name;
        private List<TranstitionBuilder> transtitionBuilders = new ArrayList<>();
        private boolean isCompleteState = false;

        public TranstitionBuilder on(String symbol) {
            TranstitionBuilder transtitionBuilder = new TranstitionBuilder(symbol, this);
            transtitionBuilders.add(transtitionBuilder);
            return transtitionBuilder;
        }

        public StateBuilder(String name) {
            this.name = name;
        }

        public StateBuilder isCompleteState() {
            this.isCompleteState = true;
            return this;
        }

        public State build() {
            State newState = new State(this.name, this.isCompleteState);
            for (TranstitionBuilder transtitionBuilder : transtitionBuilders) {
                newState.transitions.put(transtitionBuilder.symbol, transtitionBuilder.states);
            }
            return newState;
        }
    }

    public static class TranstitionBuilder {
        private String symbol;
        private List<String> states = new ArrayList<>();
        private StateBuilder parent;
        
        public TranstitionBuilder(String symbol, StateBuilder parent) {
            this.symbol = symbol;
            this.parent = parent;
        }
        
        public TranstitionBuilder to(String stateName){
            states.add(stateName);
            return this;
        }

        public TranstitionBuilder on(String symbol) {
            return parent.on(symbol);
        }

        public State build() {
            return parent.build();
        }

        public StateBuilder isCompleteState() {
            return parent.isCompleteState();
        }

    }
}
