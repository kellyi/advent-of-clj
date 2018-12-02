(ns advent-of-clj.core
  (:gen-class)
  (:require [advent-of-clj.lib.zero]
            [advent-of-clj.lib.one]
            [advent-of-clj.lib.two]))

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

(defn -main
  [& args]
  (cond
    (.contains args "zero") (solve-zero)
    (.contains args "one") (solve-one)
    (.contains args "two") (solve-two)
    :else (not-found)))
