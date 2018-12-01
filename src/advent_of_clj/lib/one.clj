(ns advent-of-clj.lib.one
  (:gen-class))

(defn create-numbers
  []
  (let [input (clojure.string/split (clojure.string/trim-newline (slurp "data/one.txt")) #"\n")]
    (map (fn [x] (read-string x)) input)))

(defn solve
  []
  (let [n (create-numbers)]
  (println (reduce + n))))
