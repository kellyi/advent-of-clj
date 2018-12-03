(ns advent-of-clj.lib.zero
  (:gen-class)
  (:require [clojure.string]))

(defn create-numbers
  []
  (let [input (clojure.string/split (clojure.string/trim-newline (slurp "data/zero.txt")) #"")]
    (map (fn [x] (read-string x)) input)))

(defn sum-adjacent-identical-recursive
  [[head nxt & tail] final accumulator]
  (cond
    (and (nil? nxt) (= head final)) (+ final accumulator)
    (nil? nxt) accumulator
    :else (cond
            (and (= head nxt) (nil? tail)) (recur [nxt nil] final (+ accumulator head))
            (nil? tail) (recur [nxt nil] final accumulator)
            (= head nxt) (recur (vec (cons nxt tail)) final (+ accumulator head))
            :else (recur (vec (cons nxt tail)) final accumulator))))

(defn sum-adjacent-identical
  [[head & tail]]
  (sum-adjacent-identical-recursive (vec (cons head tail)) head 0))

(def n (create-numbers))

(defn solve
  []
  (println (str (sum-adjacent-identical (vec n)))))
