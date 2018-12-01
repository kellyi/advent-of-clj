(ns advent-of-clj.core
  (:gen-class))

(defn not-found
  []
  (println "not found"))

(require 'advent-of-clj.lib.zero)
(defn solve-zero
  []
  (advent-of-clj.lib.zero/solve))

(require 'advent-of-clj.lib.one)
(defn solve-one
  []
  (advent-of-clj.lib.one/solve))


(defn -main
  [& args]
  (cond
    (.contains args "zero") (solve-zero)
    (.contains args "one") (solve-one)
    :else (not-found)))
