package sample;

public class ProcessingContext {
    private String history;
    private State currentState;

    public ProcessingContext child (State newState) {
        return new ProcessingContext(this.history + " " + newState.name, newState);
    }

    public ProcessingContext(String history, State currentState) {
        this.history = history;
        this.currentState = currentState;
    }

    public String getHistory() {
        return history;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}

