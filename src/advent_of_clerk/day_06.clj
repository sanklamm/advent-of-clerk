;; # 🎄 Advent of Clerk: Day 6
{:nextjournal.clerk/visibility {:code :hide :result :hide}}

(ns advent-of-clerk.day-06
  (:require [nextjournal.clerk :as clerk]
            [util.utils :as utils]
            [clojure.string :as str]
            [clojure.java.io :as io]))

(def day 6)

(utils/get-input-data day)

(def data (str "input-" day ".txt"))

{:nextjournal.clerk/visibility {:code :show :result :show}}

(def sample-1 "mjqjpqmgbljsphdztnvjfqwrcgsmlb")
(def sample-2 "bvwbjplbgvbhsrlpgdmjqwftvncz")

(defn find-packet-start [data packet-length]
  (->> data
       (partition packet-length 1)
       (map #(set %))
       (map #(count %))
       (take-while #(not= packet-length %))
       (count)
       (+ packet-length)))

(find-packet-start sample-1 4)
;; => 7
(find-packet-start sample-2 4)
;; => 5

;; part 1
(find-packet-start (slurp (io/resource data)) 4)
;; => 1802

;; part 2
(find-packet-start sample-1 14)
;; => 19
(find-packet-start (slurp (io/resource data)) 14)
;; => 3551
