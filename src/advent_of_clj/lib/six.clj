(ns advent-of-clj.lib.six
  (:gen-class)
  (:require [clojure.string]))

(defn read-input
  []
  (let [input (as-> "data/five.txt" file
                (slurp file)
                (clojure.string/trim-newline file))]
    input))

(defn sum-list
  ([x]
   (sum-list x 0))
  ([[h & t] counter]
   (cond
     (nil? h) counter
     :else (recur t (+ counter h)))))

(defn solve
  []
  (let [n (read-input)]
    (do (println (str "part one: " (sum-list '(1 2 3 4))))
        (println (str "part two: " nil)))))
