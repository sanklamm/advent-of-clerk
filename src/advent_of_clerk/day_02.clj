;; # 🎄 Advent of Clerk: Day 2
(ns advent-of-clerk.day-02
  (:require [nextjournal.clerk :as clerk]
            [util.utils :as utils]
            [clojure.string :as str]))

(def day 2)

(utils/get-input-data day)

(def data (str "input-" day ".txt"))

(def get-score-1 {'(:A :X) 4
                  '(:B :Y) 5
                  '(:C :Z) 6
                  '(:A :Y) 8
                  '(:A :Z) 3
                  '(:B :X) 1
                  '(:B :Z) 9
                  '(:C :X) 7
                  '(:C :Y) 2})

(def get-score-2 {'(:A :X) 3
                  '(:A :Y) 4
                  '(:A :Z) 8
                  '(:B :X) 1
                  '(:B :Y) 5
                  '(:B :Z) 9
                  '(:C :X) 2
                  '(:C :Y) 6
                  '(:C :Z) 7})

(defn parse [lines]
 (->> lines
      (map #(str/split % #" "))
      (map #(map keyword %))))

(parse (utils/read-lines data))

(defn part-1 [input]
  (->> input
       (map #(get-score-1 %))
       (apply +)))

(defn part-2 [input]
  (->> input
       (map #(get-score-2 %))
       (apply +)))

(part-1 (parse (utils/read-lines data)))
;; => 10404

(part-2 (parse (utils/read-lines data)))
;; => 10334
