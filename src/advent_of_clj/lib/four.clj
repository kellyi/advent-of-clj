(ns advent-of-clj.lib.four
  (:gen-class)
  (:require [clojure.string]
            [clojure.set]
            [clj-time.format]))

(declare format-input)

(def test-input (map format-input (shuffle ["[1518-11-01 00:00] Guard #10 begins shift"
                                            "[1518-11-01 00:05] falls asleep"
                                            "[1518-11-01 00:25] wakes up"
                                            "[1518-11-01 00:30] falls asleep"
                                            "[1518-11-01 00:55] wakes up"
                                            "[1518-11-01 23:58] Guard #99 begins shift"
                                            "[1518-11-02 00:40] falls asleep"
                                            "[1518-11-02 00:50] wakes up"
                                            "[1518-11-03 00:05] Guard #10 begins shift"
                                            "[1518-11-03 00:24] falls asleep"
                                            "[1518-11-03 00:29] wakes up"
                                            "[1518-11-04 00:02] Guard #99 begins shift"
                                            "[1518-11-04 00:36] falls asleep"
                                            "[1518-11-04 00:46] wakes up"
                                            "[1518-11-05 00:03] Guard #99 begins shift"
                                            "[1518-11-05 00:45] falls asleep"
                                            "[1518-11-05 00:55] wakes up"])))

(def wake :wake)
(def sleep :sleep)

(defn map-entry-to-guard-id
  [l]
  (let [not-space (fn [x] (not (= " " x)))
        truncated-entry (as-> l s (drop 8 s) (drop-last 13 s))
        guard-id (take-while not-space truncated-entry)]
    (apply str guard-id)))

(defn map-log-to-data
  [l]
  (let [s (apply str l)]
    (cond
      (clojure.string/includes? s "wake") wake
      (clojure.string/includes? s "sleep") sleep
      :else (map-entry-to-guard-id l))))

(defn parse-log-date
  [d]
  (let [custom-formatter (clj-time.format/formatter "[yyyy-MM-dd HH:mm")
        date-string (as-> d s
                      (apply str s)
                      (clj-time.format/parse custom-formatter s))]
    date-string))

(defn sort-entries-by-date
  [l]
  (sort-by :time l))

(defn format-input
  [x]
  (let [s (clojure.string/split x #"]")
        time (parse-log-date (first s))
        log (map-log-to-data (last s))]
    (cond
      (= log wake) {:time time :instruction wake}
      (= log sleep) {:time time :instruction sleep}
      :else {:time time :guard log})))

(defn read-input
  []
  (let [input (as-> "data/four.txt" file
                (slurp file)
                (clojure.string/trim-newline file)
                (clojure.string/split-lines file)
                (map format-input file))]
    input))

(defn get-next-midnight
  "Given a date time entry, get the next midnight"
  [date-time-entry]
  (let [minute (clj-time.core/minute date-time-entry)
        offset (- 60 minute)]
    (clj-time.core/plus date-time-entry (clj-time.core/minutes offset))))

(defn inc-one-minute
  [date-time-entry]
  (clj-time.core/plus date-time-entry (clj-time.core/minutes 1)))

(defn update-map
  [guard-times-map guard current-time instruction]
  (if (= instruction wake) guard-times-map
      (assoc guard-times-map guard (conj (get guard-times-map guard) (clj-time.core/minute current-time)))))

(defn reduce-entries-by-guard-loop
  [[entry & tail :as entries] current-time guard instruction guard-times-map]
  (cond
    (nil? entry) guard-times-map
    :else (let [next-time (get entry :time)
                next-instruction (get entry :instruction)
                next-guard (get entry :guard)
                current-hour (clj-time.core/hour current-time)
                current-minute (clj-time.core/minute current-time)
                next-minute (inc-one-minute current-time)
                adjust-map (partial update-map guard-times-map guard current-time)]
            (cond
              (and (= 1 current-hour)
                   (not (nil? next-guard))) (recur tail next-time next-guard wake guard-times-map)
              (and (= next-time current-time)
                   (not (nil? next-guard)))
              (recur tail current-time next-guard wake guard-times-map)
              (and (= next-time current-time)
                   (not (nil? next-instruction)))
              (recur tail current-time guard next-instruction guard-times-map)
              (= instruction sleep) (recur entries next-minute guard instruction (adjust-map instruction))
              :else (recur entries next-minute guard instruction guard-times-map)))))

(defn create-guard-times-map
  [entries]
  (as-> entries e
    (filter #(contains? % :guard) e)
    (map #(get % :guard) e)
    (distinct e)
    (into {} (map (fn [g] [g '()]) e))))

(defn reduce-entries-by-guard
  [entries]
  (let [initial-guard (get (first entries) :guard)
        initial-time (get (first entries) :time)
        guard-times-map (create-guard-times-map entries)]
    (reduce-entries-by-guard-loop (rest entries) initial-time initial-guard wake guard-times-map)))

(defn guard-minutes-count
  [guard-times-map])

(defn find-most-minutes
  [guard-times-map]
  (let [guard-tuples (into '() guard-times-map)
        reducer (fn [[max-guard-id max-minutes :as acc] [next-guard-id next-minutes :as next-guard]]
                  (cond
                    (> (count next-minutes) (count max-minutes)) next-guard
                    :else acc))]
    (reduce reducer guard-tuples)))

(defn most-frequent
  [items]
  (->> items
       frequencies
       (sort-by val)
       last
       first))

(defn multiply-guard-by-most-frequent-minute
  [[id minutes]]
  (let [numeric-id (read-string id)
        freq (most-frequent minutes)]
    (* numeric-id freq)))

(defn solve-one
  [input]
  (as-> input entries
    (sort-entries-by-date entries)
    (reduce-entries-by-guard entries)
    (find-most-minutes entries)
    (multiply-guard-by-most-frequent-minute entries)))

(defn maybe-repeat-minutes
  [min]
  (cond
    (empty? min) '()
    :else (apply repeat min)))

(defn get-most-frequent-minutes
  [minutes-list]
  (->> minutes-list
       frequencies
       (sort-by val)
       last
       reverse
       maybe-repeat-minutes))

(defn map-minutes-to-most-frequent-minute
  [entries]
  (map (fn [[k v]] [(get-most-frequent-minutes v) k]) entries))

(defn find-guard-with-most-repeated-minutes
  [entries]
  (reduce (fn [[last-minutes last-guard] [next-minutes next-guard]]
            (cond
              (< (count last-minutes) (count next-minutes)) [next-minutes next-guard]
              :else [last-minutes last-guard])) entries))

(defn solve-two
  [input]
  (as-> input entries
    (sort-entries-by-date entries)
    (reduce-entries-by-guard entries)
    (map-minutes-to-most-frequent-minute entries)
    (find-guard-with-most-repeated-minutes entries)
    (* (-> entries first first) (read-string (last entries)))))

(defn solve
  []
  (let [n (read-input)]
    (do (println (str "part one: " (solve-one n)))
        (println (str "part two: " (solve-two n))))))
