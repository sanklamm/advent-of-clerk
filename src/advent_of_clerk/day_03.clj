;; # 🎄 Advent of Clerk: Day 3
(ns advent-of-clerk.day-03
  (:require [nextjournal.clerk :as clerk]
            [util.utils :as utils]
            [clojure.set :as set]))

(def day 3)

(utils/get-input-data day)

(def data (str "input-" day ".txt"))

(defn split-in-half [s]
  (let [n (count s)]
    [(subs s 0 (quot n 2)) (subs s (quot n 2))]))

;; Lowercase item types a through z have priorities 1 through 26.
;; Uppercase item types A through Z have priorities 27 through 52.
(defn compute-priority [c]
  (let [n (int c)]
    (if (<= 97 n 122)
      (- n 96)
      (- n 38))))

(defn parse-1 [lines]
  (->> lines
       (mapv split-in-half)
       (map #(map set %))))

(defn part-1 [data]
  (->> data
     (map #(apply set/intersection %))
     (map first)
     (map compute-priority)
     (reduce +)))

(time (part-1 (parse-1 (utils/read-lines data))))
;; => 7742

(defn parse-2 [lines]
  (->> lines
       (map set)
       (partition 3)))

(defn part-2 [data]
  (->> data
     (map #(apply set/intersection %))
     (map first)
     (map compute-priority)
     (reduce +)))

(time (part-2 (parse-2 (utils/read-lines data))))
;; => 2276
