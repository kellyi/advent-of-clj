(ns advent-of-clj.core
  (:gen-class)
  (:require [advent-of-clj.lib.zero]
            [advent-of-clj.lib.one]
            [advent-of-clj.lib.two]
            [advent-of-clj.lib.three]
            [advent-of-clj.lib.four]))

(defn not-found
  []
  (println "not found"))

(defn solve-zero
  []
  (advent-of-clj.lib.zero/solve))

(defn solve-one
  []
  (advent-of-clj.lib.one/solve))

(defn solve-two
  []
  (advent-of-clj.lib.two/solve))

(defn solve-three
  []
  (advent-of-clj.lib.three/solve))

(defn solve-four
  []
  (advent-of-clj.lib.four/solve))

(defn -main
  [& args]
  (cond
    (.contains args "zero") (solve-zero)
    (.contains args "one") (solve-one)
    (.contains args "two") (solve-two)
    (.contains args "three") (solve-three)
    (.contains args "four") (solve-four)
    :else (not-found)))
