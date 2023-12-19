import java.util.*;

class Transition {
    String source;
    String symbol;
    String destination;

    Transition(String source, String symbol, String destination) {
        this.source = source;
        this.symbol = symbol;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "(" + source + ", " + symbol + "): " + destination;
    }
}

class Automaton {
    Set<String> states;
    Set<String> alphabet;
    List<Transition> transitions;
    String initialState;
    Set<String> finalStates;

    Automaton(Set<String> states, Set<String> alphabet, List<Transition> transitions, String initialState, Set<String> finalStates) {
        this.states = new HashSet<>(states);
        this.alphabet = new HashSet<>(alphabet);
        this.transitions = new ArrayList<>(transitions);
        this.initialState = initialState;
        this.finalStates = new HashSet<>(finalStates);
    }

    void transformToPartiallyGeneralizedAutomaton() {
        List<Transition> newTransitions = new ArrayList<>();
        int stateCounter = 0;

        for (Transition transition : transitions) {
            
            if (transition.symbol.length() > 1) {
                
                for (int i = 0; i < transition.symbol.length(); i++) {
                    String newSource, newSymbol, newDestination;

                    if (i == 0) {
                        newSource = transition.source;
                    } else {
                        newSource = transition.symbol.substring(i - 1, i) + "" + stateCounter;
                    }

                    newSymbol = transition.symbol.substring(i, i + 1);

                    if (i == transition.symbol.length() - 1) {

                        newDestination = transition.destination;
                    } else {
                                             stateCounter++;

                        newDestination = transition.symbol.substring(i + 1, i + 2) + "" + stateCounter;
                    }

                    newTransitions.add(new Transition(newSource, newSymbol, newDestination));
                    states.add(newSource);
                    states.add(newDestination);

                    if (finalStates.contains(transition.source)) {
                        finalStates.add(newSource);
                    }

                    
                }
            } else {
                newTransitions.add(transition);
            }
        }

        transitions = newTransitions;
    }

        // Fonction pour éliminer les transitions epsilon
public Automaton eliminateEpsilonTransitions() {
    Set<String> newFinalStates = new HashSet<>(finalStates);
    List<Transition> newTransitions = new ArrayList<>();

    boolean epsilonFound = true;

    while (epsilonFound) {
        epsilonFound = false;

        // Première partie: Ajouter les états finaux accessibles via epsilon
        for (Transition transition : transitions) {
            if (transition.symbol.equals("epsilon") && finalStates.contains(transition.destination)) {
                newFinalStates.add(transition.source);
            }
        }

        // Deuxième partie: Ajouter les transitions sans epsilon
        newTransitions.clear();

        for (Transition transition : transitions) {
            if (!transition.symbol.equals("epsilon")) {
                newTransitions.add(transition);
            } else {
                // Ajouter des transitions sans epsilon
                epsilonFound = addTransitions(newTransitions, transition.source, transition.destination) || epsilonFound;
            }
        }
        transitions.clear();
        transitions.addAll(newTransitions);
    }

    // Supprimer les transitions en double dans newTransitions
    Set<Transition> uniqueTransitions = new HashSet<>(newTransitions);
    newTransitions.clear();
    newTransitions.addAll(uniqueTransitions);

    return new Automaton(states, alphabet, newTransitions, initialState, newFinalStates);
}

    private boolean addTransitions(List<Transition> newTransitions, String source, String destination) {
        boolean epsilonTransitionsAdded = false;

        for (Transition transition : transitions) {
            if (transition.source.equals(destination)) {
                newTransitions.add(new Transition(source, transition.symbol, transition.destination));
                epsilonTransitionsAdded = true;
            }
        }

        return epsilonTransitionsAdded;
    }

    void printAutomaton() {
        System.out.println("States: " + states);
        System.out.println("Alphabet: " + alphabet);
        System.out.println("Transitions: " + transitions);
        System.out.println("Initial State: " + initialState);
        System.out.println("Final States: " + finalStates);
    }
}

public class PartiallyG {
    public static void main(String[] args) {

        System.out.println("test de la transformation du generalise vers partiellement generalise ");
        Set<String> states = new HashSet<>(Arrays.asList("q0", "q1", "q2", "q3"));
        Set<String> alphabet = new HashSet<>(Arrays.asList("a", "b"));
        List<Transition> transitions = new ArrayList<>(Arrays.asList(
                new Transition("q0", "aaaaaa", "q1"),
                new Transition("q0", "b", "q2"),
                new Transition("q1", "bb", "q3"),
                new Transition("q2", "a", "q2"),
                new Transition("q2", "bb", "q3")
        ));
        String initialState = "q0";
        Set<String> finalStates = new HashSet<>(Arrays.asList("q3"));

        Automaton automaton = new Automaton(states, alphabet, transitions, initialState, finalStates);

        System.out.println("Automaton before transformation:");
        automaton.printAutomaton();

        automaton.transformToPartiallyGeneralizedAutomaton();

        System.out.println("\nAutomaton after transformation:");
        automaton.printAutomaton();

        System.out.println("_____________________________________________________________");
        System.out.println("test du partiellement generalise vers generalise ");
        Set<String> states2 = new HashSet<>(Arrays.asList("q0", "q1", "q2"));
        Set<String> alphabet2 = new HashSet<>(Arrays.asList("a", "b", "epsilon"));
        List<Transition> transitions2 = Arrays.asList(
                new Transition("q0", "epsilon", "q1"),
                new Transition("q1", "epsilon", "q2"),
                new Transition("q2", "a", "q0")
        );
        String initialState2 = "q0";
        Set<String> finalStates2 = new HashSet<>(Arrays.asList("q2"));

        Automaton automaton2 = new Automaton(states2, alphabet2, transitions2, initialState2, finalStates2);

        // Affichage de l'automate d'origine
        System.out.println("Automaton Before Eliminating Epsilon Transitions:");
        automaton.printAutomaton();

        // Élimination des transitions epsilon
        Automaton newAutomaton = automaton.eliminateEpsilonTransitions();

        // Affichage de l'automate après l'élimination des transitions epsilon
        System.out.println("\nAutomaton After Eliminating Epsilon Transitions:");
        newAutomaton.printAutomaton();
    }
}

