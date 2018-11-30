(ns advent-of-clj.core
  (:gen-class))

(defn not-found
  []
  (println "not found"))

(require 'advent-of-clj.lib.zero)
(defn solve-zero
  []
  (advent-of-clj.lib.zero/solve))

(defn -main
  [& args]
  (cond
    (.contains args "zero") (solve-zero)
    :else (not-found)))
