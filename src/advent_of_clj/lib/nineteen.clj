(ns advent-of-clj.lib.nineteen
  (:gen-class)
  (:require [clojure.string]
            [clojure.set]))

(defn read-input
  "Read puzzle input"
  []
  (let [input (as-> "data/nineteen.txt" file
                (slurp file)
                (clojure.string/split file #"\n"))]
    input))

(defn map-instruction-to-form
  [instruction]
  (str "(" instruction ")"))

(defn ip
  [register]
  register)

(defn seti
  [x y z]
  (str x y z))

(defn setr
  [x y z]
  (str x y z))

(defn addi
  [x y z]
  (str x y z))

(defn addr
  [x y z]
  (str x y z))

(defn solve-one
  [input]
  (as-> input instructions
    (map (comp load-string map-instruction-to-form) instructions)))

(def test-input ["ip 0"
                 "seti 5 0 1"
                 "seti 6 0 2"
                 "addi 0 1 0"
                 "addr 1 2 3"
                 "setr 1 0 0"
                 "seti 8 0 4"
                 "seti 9 0 5"])

(solve-one test-input)

(defn solve
  []
  (let [n (read-input)]
    (do (println (pr-str "part one: " (solve-one test-input)))
        (println (str "part two: " nil)))))
