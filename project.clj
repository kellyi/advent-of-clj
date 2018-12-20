(defproject advent-of-clj "0.1.0-SNAPSHOT"
  :description "Programming puzzles in Clojure"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [clj-time "0.15.1"]]
  :main ^:skip-aot advent-of-clj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :plugins [[lein-cljfmt "0.6.2"]])
