(ns advent-of-clj.lib.three
  (:gen-class)
  (:require [clojure.string]))

(defn clean-input
  [l]
  (map (fn [x]
         (as-> x s
           (clojure.string/replace s #":" "")
           (clojure.string/replace s #"@ " "")
           (clojure.string/split s #" "))) l))

(defn read-input
  []
  (let [input (as-> "data/three.txt" file
                (slurp file)
                (clojure.string/trim-newline file)
                (clojure.string/split file #"\n")
                (clean-input file))]
    input))

(defn make-points-from-claim
  [[n point size]]
  (let [[w h] (clojure.string/split size #"x")
        [l t] (clojure.string/split point #",")
        height (read-string h)
        width (read-string w)
        left (read-string l)
        top (read-string t)]
    (for [new-left (range left (+ left width)) new-top (range top (+ top height))]
      (str new-left "," new-top))))

(def test-claims (clean-input ["#1 @ 1,3: 4x4" "#2 @ 3,1: 4x4" "#3 @ 5,5: 2x2"]))

(defn solve-part-one
  [l]
  (as-> l claims ;; l claims
    (map make-points-from-claim claims)
    (flatten claims)
    (frequencies claims)
    (filter (fn [[k v]] (not (= 1 v))) claims)
    (count claims)))

(defn solve
  []
  (let [n (read-input)]
    (do (println (str "part one: " (solve-part-one n)))
        (println (str "part two: ")))))

