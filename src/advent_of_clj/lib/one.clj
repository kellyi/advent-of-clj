(ns advent-of-clj.lib.one
  (:gen-class)
  (:require [clojure.string]))

(defn create-numbers
  []
  (let [input (clojure.string/split (clojure.string/trim-newline (slurp "data/one.txt")) #"\n")]
    (map (fn [x] (read-string x)) input)))

(defn solve-part-one
  [n]
  (reduce + n))

(defn rotate
  [[h & t]]
  (concat t [h]))

(defn solve-part-two-recursive
  [l accumulator seen iterations]
  (cond
;;    (> iterations 1000) seen ;; for debugging
    (contains? seen accumulator) accumulator
    :else (recur (rotate l) (+ accumulator (first l)) (conj seen accumulator) (+ 1 iterations))))

(defn solve-part-two
  [l]
  (solve-part-two-recursive l 0 #{} 0))

;; debugging test input
;; (solve-part-two [1 -1])
;; (solve-part-two [1 -2 3 1 1 -2])
;; (solve-part-two [3 3 4 -2 -4])
;; (solve-part-two [-6 3 8 5 -6])
;; (solve-part-two [7 7 -2 -7 -4])

(defn solve
  []
  (let [n (create-numbers)]
    (do (println (str "part one: " (solve-part-one n)))
        (println (str "part two: " (solve-part-two n))))))
