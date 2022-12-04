;; # 🎄 Advent of Clerk: Day 4
(ns advent-of-clerk.day-04
  (:require [nextjournal.clerk :as clerk]
            [util.utils :as utils]
            [clojure.string :as str]
            [clojure.set :as set]))

(def day 4)

(utils/get-input-data day)

(def data (str "input-" day ".txt"))

(defn str-range->set [s]
  (let [parts (str/split s #"-")]
    (set (range (Integer/parseInt (first parts))
           (inc (Integer/parseInt (last parts)))))))

(defn parse [lines]
  (->> lines
       (map #(str/split % #","))
       (map #(map str-range->set %))))

(defn set-contains? [s1 s2]
  (or (set/subset? s1 s2)
      (set/subset? s2 s1)))

(defn set-overlapping? [s1 s2]
  (not (empty? (set/intersection s1 s2))))

(defn part-1 [data]
  (->> data
       (map #(apply set-contains? %))
       (filter true?)
       (count)))

(time (part-1 (parse (utils/read-lines data))))
;; => 477

(defn part-2 [data]
  (->> data
       (map #(apply set-overlapping? %))
       (filter true?)
       (count)))

(time (part-2 (parse (utils/read-lines data))))
;; => 830
