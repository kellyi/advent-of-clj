(ns advent-of-clj.lib.two
  (:gen-class))

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

(defn solve-part-two
  [l]
  "part two")

;; (def example-one "abcdef")
;; (def example-two "bababc")
;; (def example-three "abbcde")
;; (def example-four "abcccd")
;; (def example-five "aabcdd")
;; (def example-six "abcdee")
;; (def example-seven "ababab")

(defn solve
  []
  (let [n (create-numbers)]
    (do (println (str "part one: " (solve-part-one n)))
        (println (str "part two: " (solve-part-two n))))))
