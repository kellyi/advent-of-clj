(ns advent-of-clj.lib.five
  (:gen-class)
  (:require [clojure.string])
  (:require [clojure.set]))

(defn read-input
  []
  (let [input (as-> "data/five.txt" file
                (slurp file)
                (clojure.string/trim-newline file))]
    input))

(defn char-have-opposite-polarity?
  [one two]
  (let [not-same-case (not (= one two))
        char-are-equal (= (clojure.string/lower-case one)
                          (clojure.string/lower-case two))]
    (and not-same-case char-are-equal)))

(defn solve-part-one-loop
  [[f s & tail] prior]
  (cond
    (nil? s) (count (conj prior f))
    (char-have-opposite-polarity? f s) (recur (concat prior tail) [])
    :else (recur (concat [s] tail) (conj prior f))))

(defn solve-part-one
  [input]
  (solve-part-one-loop input []))

(def example-input "dabAcCaCBAcCcaDA")
(solve-part-one example-input)

(defn solve
  []
  (let [n (read-input)]
    (do (println (str "part one: " (solve-part-one n)))
        (println (str "part two: " nil)))))
