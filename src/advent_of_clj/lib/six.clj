(ns advent-of-clj.lib.six
  (:gen-class)
  (:require [clojure.string]))

(declare map-string-to-coordinates)

(def test-input (map map-string-to-coordinates ["1, 1"
                                                "1, 6"
                                                "8, 3"
                                                "3, 4"
                                                "5, 5"
                                                "8, 9"])) ;; 17

(def alternate-test-input (map map-string-to-coordinates ["1, 1"
                                                          "1, 101"
                                                          "48, 51"
                                                          "51, 48"
                                                          "51, 51"
                                                          "51, 54"
                                                          "54, 51"
                                                          "101, 1"
                                                          "101, 101"])) ;; 9

(defn map-string-to-coordinates
  "Parse a string into a pair of int coordinates"
  [s]
  (map (fn [n] (read-string n)) (clojure.string/split s #", ")))

(defn read-input
  "Read puzzle input"
  []
  (let [input (as-> "data/six.txt" file
                (slurp file)
                (clojure.string/trim-newline file)
                (clojure.string/split file #"\n")
                (map map-string-to-coordinates file))]
    input))

(defn get-max-x
  "Find maximum x coordinate from list of coordinates"
  [l]
  (apply max (map first l)))

(defn get-max-y
  "Find maximum y coordinate from list of coordinates"
  [l]
  (apply max (map second l)))

(defn get-min-x
  "Find minimum x coordinate from list of coordinates"
  [l]
  (apply min (map first l)))

(defn get-min-y
  "Find minimum y coordinate from list of coordinates"
  [l]
  (apply min (map second l)))

(defn make-point
  "Make a point from lat lng input"
  [x y]
  [x y])

(defn manhattan-distance
  "Calculate Manhattan distance"
  [[x y] [a b]]
  (+ (Math/abs (- x a)) (Math/abs (- y b))))

(defn has-repeated-elements?
  "Check whether a list has any repeated elements"
  [l]
  (let [elements (first (map (fn [[k v]] v) (frequencies l)))]
    (> elements 1)))

(defn create-bbox-from-input
  "Create a bounding box polygon from a list of points"
  [input]
  (let [max-x (get-max-x input)
        max-y (get-max-y input)
        min-x (get-min-x input)
        min-y (get-min-y input)
        xrange (range min-x (+ 1 max-x))
        yrange (range min-y (+ 1 max-y))
        top (for [x xrange] (list x min-y))
        left (for [y yrange] (list min-x y))
        bottom (for [x xrange] (list x max-y))
        right (for [y yrange] (list max-x y))]
    (concat top left bottom right)))

(defn make-grid
  "Create a grid bounded by min & max x and y values from a list of points"
  [input]
  (for [x (range 0 (+ 1 (get-max-x input)))
        y (range 0 (+ 1 (get-max-y input)))]
    (make-point x y)))

(defn create-point-dictionary
  "Make a dictionary for each point containing the point, nearest point, and distance to nearest point"
  [[distance nearest-point point]]
  {:distance distance :nearest nearest-point :point point})

(defn find-nearest-point
  "Given a point and a list, find the other point nearest the first point. nil if tied"
  [input pt]
  (let [get-distance (fn [pt-from-input] [(manhattan-distance pt pt-from-input) pt-from-input pt])
        distances-with-points (map get-distance input)
        distances-without-points (map (fn [l] (first l)) distances-with-points)
        minimum-distance (apply min distances-without-points)]
    (cond
      (has-repeated-elements? distances-without-points) [nil nil pt]
      :else (last (filter (fn [[d]] (= d minimum-distance)) distances-with-points)))))

(find-nearest-point test-input '(1 5))

(defn point-is-on-bbox?
  "Given a point and a bbox polygon, check whether the point is on the bbox"
  [pt bbox]
  (contains? (set bbox) pt))

(defn create-filtered-point-set
  "Given a list of points and a bbox, return a list of points without any points on the bbox"
  [pts bbox]
  (let [reducer (fn [acc [d n next-pt]]
                  (cond
                    (point-is-on-bbox? next-pt bbox) acc
                    :else (conj acc next-pt)))]
    (set (reduce reducer '() pts))))

(defn select-eligible-points
  [point-dicts input]
  (as-> input points
    (create-bbox-from-input points)
    (map (partial find-nearest-point input) points)
    (filter #(-> % first nil? not) points)
    (map create-point-dictionary points)))
;;    (map second points)
;;    (set (keep identity points))))
;;    (filter #(contains? points (get % :point)) point-dicts)))

(defn solve-one
  [input]
  (as-> input points
    (make-grid points)
    (map (partial find-nearest-point input) points)
    (filter #(-> % first nil? not) points)
    (map create-point-dictionary points)
    (select-eligible-points points input)))

(solve-one test-input)

(defn solve
  []
  (let [n (read-input)]
    (do (println (str "part one: " (solve-one n)))
        (println (str "part two: " nil)))))
