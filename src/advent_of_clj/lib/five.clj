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

(defn count-fully-reacted-polymer
  [[f s & tail] prior]
  (cond
    (nil? s) (count (conj prior f))
    (char-have-opposite-polarity? f s) (recur (concat prior tail) [])
    :else (recur (concat [s] tail) (conj prior f))))

(defn solve-part-one
  [input]
  (count-fully-reacted-polymer input []))

(defn remove-char-from-string
  [c s]
  (as-> s input
    (clojure.string/replace input c "")
    (clojure.string/replace input (clojure.string/upper-case c) "")))

(defn solve-part-two
  [input]
  (let [characters (map (fn [s] (str s)) (map char (range 97 123)))
        test-strings (map (fn [c] (remove-char-from-string c input)) characters)
        polymers (map (fn [s] (count-fully-reacted-polymer s [])) test-strings)]
    (apply min polymers)))

(defn solve
  []
  (let [n (read-input)]
    (do (println (str "part one: " (solve-part-one n)))
        (println (str "part two: " (solve-part-two n))))))
