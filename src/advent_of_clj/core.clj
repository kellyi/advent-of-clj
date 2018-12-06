(ns advent-of-clj.core
  (:gen-class)
  (:require [advent-of-clj.lib.zero :as zero]
            [advent-of-clj.lib.one :as one]
            [advent-of-clj.lib.two :as two]
            [advent-of-clj.lib.three :as three]
            [advent-of-clj.lib.four :as four]
            [advent-of-clj.lib.five :as five]))
;;            [advent-of-clj.lib.six :as six]))

(defn -main
  [& args]
  (let [argset (set args)]
    (cond
      (contains? argset "zero") (zero/solve)
      (contains? argset "one") (one/solve)
      (contains? argset "two") (two/solve)
      (contains? argset "three") (three/solve)
      (contains? argset "four") (four/solve)
      (contains? argset "five") (five/solve)
;;      (contains? argset "six") (six/solve)
      :else (println "not found"))))
