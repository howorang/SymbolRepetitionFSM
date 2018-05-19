package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StateMachine {

    private Map<String, State> stateMap = new HashMap<>();
    private List<ProcessingContext> processingContexts = new ArrayList<>();
    private List<ProcessingContext> contextsToChronicle = new ArrayList<>();

    public StateMachine() {
        List<State> states = new ArrayList<>();
        states.add(State.of("qp")
                .on("0").to("q0").to("qp")
                .on("1").to("q1").to("qp")
                .on("2").to("q2").to("qp")
                .on("3").to("q3").to("qp")
                .on("4").to("q4").to("qp")
                .on("a").to("qa").to("qp")
                .on("b").to("qb").to("qp")
                .on("c").to("qc").to("qp")
                .on("e").to("qe").to("qp")
                .on("f").to("qf").to("qp")
                .build());
        states.add(State.of("q0")
                .on("0").to("qsd")
                .build());
        states.add(State.of("q1")
                .on("1").to("qsd")
                .build());
        states.add(State.of("q2")
                .on("2").to("qsd")
                .build());
        states.add(State.of("q3")
                .on("3").to("qsd")
                .build());
        states.add(State.of("q4")
                .on("4").to("qsd")
                .build());
        states.add(State.of("qa")
                .on("a").to("qsl")
                .build());
        states.add(State.of("qb")
                .on("b").to("qsl")
                .build());
        states.add(State.of("qc")
                .on("c").to("qsl")
                .build());
        states.add(State.of("qe")
                .on("e").to("qsl")
                .build());
        states.add(State.of("qf")
                .on("f").to("qsl")
                .build());
        states.add(State.of("qsl")
                .on("0").to("qsl")
                .on("1").to("qsl")
                .on("2").to("qsl")
                .on("3").to("qsl")
                .on("4").to("qsl")
                .on("a").to("qsl")
                .on("b").to("qsl")
                .on("c").to("qsl")
                .on("e").to("qsl")
                .on("f").to("qsl")
                .isCompleteState()
                .build());
        states.add(State.of("qsd")
                .on("0").to("qsd")
                .on("1").to("qsd")
                .on("2").to("qsd")
                .on("3").to("qsd")
                .on("4").to("qsd")
                .on("a").to("qsd")
                .on("b").to("qsd")
                .on("c").to("qsd")
                .on("e").to("qsd")
                .on("f").to("qsd")
                .isCompleteState()
                .build());
        states.forEach(state ->  stateMap.compute(state.name, (key, oldVal) -> state));
        ProcessingContext processingContext = new ProcessingContext("qp", stateMap.get("qp"));
        processingContexts.add(processingContext);
    }

    public void proccess(String symbol) {
        for (ProcessingContext context : new ArrayList<>(processingContexts)) {
            processingContexts.remove(context);
            State currentState = context.getCurrentState();
            List<String> transitionsForSymbol = currentState.getTransitionsForSymbol(symbol);
            if (transitionsForSymbol != null) {
                for (String state : transitionsForSymbol) {
                    processingContexts.add(context.child(stateMap.get(state)));
                }
            } else {
                contextsToChronicle.add(context);
            }
        }
    }

    public RESULT getResult() {
        List<State> completeStates = processingContexts.stream()
                .map(ProcessingContext::getCurrentState)
                .filter(State::isCompleteState)
                .collect(Collectors.toList());
        if (completeStates.isEmpty()) {
            return RESULT.NO_REPETITION;
        }
        if (completeStates.get(0).name.equals("qsd")) {
            return RESULT.DIGIT_REPETITION;
        } else  {
            return RESULT.LETTER_REPETITION;
        }
    }

    public String getHistory() {
        StringBuilder history = new StringBuilder();
        for (ProcessingContext context : contextsToChronicle) {
            history.append(context.getHistory());
            history.append(" X");
            history.append("\n");
        }
        for (ProcessingContext context : processingContexts) {
            history.append(context.getHistory());
            history.append("\n");
        }
        return history.toString();
    }
}
