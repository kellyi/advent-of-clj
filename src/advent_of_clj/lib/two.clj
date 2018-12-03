(ns advent-of-clj.lib.two
  (:gen-class)
  (:require [clojure.string]))

(declare solve-part-one solve-part-two)

(defn create-numbers
  []
  (let [input (clojure.string/split (clojure.string/trim-newline (slurp "data/two.txt")) #"\n")]
    (map (fn [x] (clojure.string/split x #"")) input)))

(defn count-repeated-elements
  [l]
  (set (map (fn [[k v]] v) (frequencies l))))

(defn has-three-repeated-elements
  [l]
  (contains? (count-repeated-elements l) 3))

(defn has-two-repeated-elements
  [l]
  (contains? (count-repeated-elements l) 2))

(defn solve-part-one
  [l]
  (let [twos (filter has-two-repeated-elements l)
        threes (filter has-three-repeated-elements l)]
    (* (count twos) (count threes))))

(defn remove-at-position
  [l position]
  (concat (take position l) (drop (+ 1 position) l)))

(defn maybe-get-duplicate
  [l]
  (first (first (filter (fn [[k v]] (= 2 v)) (frequencies l)))))

(defn solve-part-two-loop
  [l iteration stop]
  (cond
    (= iteration stop) "not found"
    :else (let [drop-element (fn [x] (remove-at-position x iteration))
                adjusted-elements (map drop-element l)
                dup (maybe-get-duplicate adjusted-elements)]
            (cond
              (some? dup) (apply str dup)
              :else (recur l (+ 1 iteration) stop)))))

(defn solve-part-two
  [[h :as l]]
  (solve-part-two-loop l 0 (count l)))

(defn solve
  []
  (let [n (create-numbers)]
    (do (println (str "part one: " (solve-part-one n)))
        (println (str "part two: " (solve-part-two n))))))
