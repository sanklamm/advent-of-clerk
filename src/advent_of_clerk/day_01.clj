;; # 🎄 Advent of Code: Day 1
(ns advent-of-clerk.day-01
  (:require [nextjournal.clerk :as clerk]
            [util.utils :as utils]))

(def day 1)

(utils/get-input-data day)

(def data (str "input-" day ".txt"))

(defn parse [lines]
  (->> lines
       (partition-by empty?)
       (remove (comp empty? first))
       (map #(map parse-long %))))

(parse (utils/read-lines data))

;; part 1
(->> (utils/read-lines data)
     parse
     (map #(reduce + %))
     (apply max))
;; => 70116


;; part 2
(->> (utils/read-lines data)
     parse
     (map #(reduce + %))
     sort
     reverse
     (take 3)
     (reduce +))
;; => 206582
