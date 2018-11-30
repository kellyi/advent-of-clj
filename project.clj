(defproject advent-of-clj "0.1.0-SNAPSHOT"
  :description "Programming puzzles in Clojure"
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot advent-of-clj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
