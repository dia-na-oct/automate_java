

// import java.util.*;

// class Transition {
//     String source;
//     String symbol;
//     String destination;

//     Transition(String source, String symbol, String destination) {
//         this.source = source;
//         this.symbol = symbol;
//         this.destination = destination;
//     }

//     @Override
//     public String toString() {
//         return "(" + source + ", " + symbol + "): " + destination;
//     }
// }

// class Automaton {
//     Set<String> states;
//     Set<String> alphabet;
//     List<Transition> transitions;
//     String initialState;
//     Set<String> finalStates;

//     Automaton(Set<String> states, Set<String> alphabet, List<Transition> transitions, String initialState, Set<String> finalStates) {
//         this.states = new HashSet<>(states);
//         this.alphabet = new HashSet<>(alphabet);
//         this.transitions = new ArrayList<>(transitions);
//         this.initialState = initialState;
//         this.finalStates = new HashSet<>(finalStates);
//     }

//     // Fonction pour éliminer les transitions epsilon
// public Automaton eliminateEpsilonTransitions() {
//     Set<String> newFinalStates = new HashSet<>(finalStates);
//     List<Transition> newTransitions = new ArrayList<>();

//     boolean epsilonFound = true;

//     while (epsilonFound) {
//         epsilonFound = false;

//         // Première partie: Ajouter les états finaux accessibles via epsilon
//         for (Transition transition : transitions) {
//             if (transition.symbol.equals("epsilon") && finalStates.contains(transition.destination)) {
//                 newFinalStates.add(transition.source);
//             }
//         }

//         // Deuxième partie: Ajouter les transitions sans epsilon
//         newTransitions.clear();

//         for (Transition transition : transitions) {
//             if (!transition.symbol.equals("epsilon")) {
//                 newTransitions.add(transition);
//             } else {
//                 // Ajouter des transitions sans epsilon
//                 epsilonFound = addTransitions(newTransitions, transition.source, transition.destination) || epsilonFound;
//             }
//         }
//         transitions.clear();
//         transitions.addAll(newTransitions);
//     }

//     // Supprimer les transitions en double dans newTransitions
//     Set<Transition> uniqueTransitions = new HashSet<>(newTransitions);
//     newTransitions.clear();
//     newTransitions.addAll(uniqueTransitions);

//     return new Automaton(states, alphabet, newTransitions, initialState, newFinalStates);
// }

//     private boolean addTransitions(List<Transition> newTransitions, String source, String destination) {
//         boolean epsilonTransitionsAdded = false;

//         for (Transition transition : transitions) {
//             if (transition.source.equals(destination)) {
//                 newTransitions.add(new Transition(source, transition.symbol, transition.destination));
//                 epsilonTransitionsAdded = true;
//             }
//         }

//         return epsilonTransitionsAdded;
//     }

//     // Fonction pour afficher l'automate
//     public void printAutomaton() {
//         System.out.println("States: " + states);
//         System.out.println("Alphabet: " + alphabet);
//         System.out.println("Transitions: " + transitions);
//         System.out.println("Initial State: " + initialState);
//         System.out.println("Final States: " + finalStates);
//     }
// }

// public class PartiallyG {
//     public static void main(String[] args) {
//         // Création d'un automate avec transitions epsilon
//         Set<String> states = new HashSet<>(Arrays.asList("q0", "q1", "q2"));
//         Set<String> alphabet = new HashSet<>(Arrays.asList("a", "b", "epsilon"));
//         List<Transition> transitions = Arrays.asList(
//                 new Transition("q0", "epsilon", "q1"),
//                 new Transition("q1", "epsilon", "q2"),
//                 new Transition("q2", "a", "q0")
//         );
//         String initialState = "q0";
//         Set<String> finalStates = new HashSet<>(Arrays.asList("q2"));

//         Automaton automaton = new Automaton(states, alphabet, transitions, initialState, finalStates);

//         // Affichage de l'automate d'origine
//         System.out.println("Automaton Before Eliminating Epsilon Transitions:");
//         automaton.printAutomaton();

//         // Élimination des transitions epsilon
//         Automaton newAutomaton = automaton.eliminateEpsilonTransitions();

//         // Affichage de l'automate après l'élimination des transitions epsilon
//         System.out.println("\nAutomaton After Eliminating Epsilon Transitions:");
//         newAutomaton.printAutomaton();
//     }
// }
