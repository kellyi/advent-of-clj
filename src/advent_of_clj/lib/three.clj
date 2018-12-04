(ns advent-of-clj.lib.three
  (:gen-class)
  (:require [clojure.string])
  (:require [clojure.set]))

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

(defn solve-part-one
  [l]
  (as-> l claims
    (map make-points-from-claim claims)
    (flatten claims)
    (frequencies claims)
    (filter (fn [[k v]] (not (= 1 v))) claims)
    (count claims)))

(defn has-intersecting-claims
  [one two]
  (let [point-set-one (set (make-points-from-claim one))
        point-set-two (set (make-points-from-claim two))
        shared (clojure.set/intersection point-set-one point-set-two)]
    (> (count shared) 0)))

(defn claim-intersects-others
  [claim [head & tail]]
  (cond
    (nil? head) false
    (= (first claim) (first head)) (recur claim tail)
    (has-intersecting-claims claim head) true
    :else (recur claim tail)))

(defn solve-part-two-loop
  [[claim & tail] all-claims]
  (cond
    (not (claim-intersects-others claim all-claims)) claim
    :else (recur tail all-claims)))

(defn solve-part-two
  [l]
  (solve-part-two-loop l l))

(defn solve
  []
  (let [n (read-input)]
    (do (println (str "part one: " (solve-part-one n)))
        (println (str "part two: " (solve-part-two n))))))
