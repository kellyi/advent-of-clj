(ns advent-of-clj.lib.seven
  (:gen-class)
  (:require [clojure.string]
            [clojure.set]))

(def test-input ["Step C must be finished before step A can begin."
                 "Step C must be finished before step F can begin."
                 "Step A must be finished before step B can begin."
                 "Step A must be finished before step D can begin."
                 "Step B must be finished before step E can begin."
                 "Step D must be finished before step E can begin."
                 "Step F must be finished before step E can begin."])

(defn read-input
  "Read puzzle input"
  []
  (let [input (as-> "data/seven.txt" file
                (slurp file)
                (clojure.string/split file #"\n"))]
    input))

(defn parse-dependency-string-into-map
  "Parse a dependency string into a map"
  [dep-string]
  (let [split-string (clojure.string/split dep-string #" must be finished before ")
        antecedent (apply str (drop 5 (first split-string)))
        consequent (apply str (take 1 (drop 5 (last split-string))))]
    [antecedent consequent]))

(defn parse-dependencies-into-maps
  "Parse a list of dependency strings into maps"
  [dependencies]
  (map parse-dependency-string-into-map dependencies))

(defn fill-in-dependency-map
  "Create a dependency map from a list of antecedent-consequent entries"
  ([entries] (fill-in-dependency-map {} entries))
  ([dep-map [h & t]]
   (cond
     (nil? h) dep-map
     :else (let [key (str (first (first h)))
                 value (str (last (last h)))
                 updated-map (assoc dep-map key (into #{} (conj (get dep-map key) value)))]
             (recur updated-map t)))))

(defn solve-one-loop
  "Recursively solve part one"
  [filled-map steps completed-steps]
  (cond
    (empty? steps) completed-steps
    :else (let [dependent-steps (apply clojure.set/union (vals filled-map))
                independent-steps (clojure.set/difference (set steps) dependent-steps)
                next-step (apply str (first (sort (vec independent-steps))))
                updated-map (dissoc filled-map next-step)
                updated-steps (remove #(= next-step %) steps)]
            (recur updated-map updated-steps (str completed-steps next-step)))))

(defn solve-one
  "Solve part one"
  [input]
  (let [dependency-maps-vector (parse-dependencies-into-maps input)
        filled-map (fill-in-dependency-map dependency-maps-vector)
        steps (-> dependency-maps-vector flatten distinct)]
    (solve-one-loop filled-map steps "")))

;; (solve-one (read-input))

(defn solve
  []
  (let [n (read-input)]
    (do (println (pr-str "part one: " (solve-one n)))
        (println (str "part two: " nil)))))
