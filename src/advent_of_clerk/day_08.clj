;; # 🎄 Advent of Clerk: Day 8
{:nextjournal.clerk/visibility {:code :hide :result :hide}}

(ns advent-of-clerk.day-08
  (:require [nextjournal.clerk :as clerk]
            [util.utils :as utils]
            [clojure.string :as str]
            [clojure.java.io :as io]))

(def day 8)

(utils/get-input-data day)

(def data (str "input-" day ".txt"))

(def sample "30373
25512
65332
33549
35390")

;; parse input to 2D array
(defn parse-input [input]
  (->> input
       (str/split-lines)
       (mapv #(re-seq #"\d" %))
       (mapv #(mapv parse-long %))))

{:nextjournal.clerk/visibility {:code :show :result :show}}

(parse-input sample)
;; => [[3 0 3 7 3] [2 5 5 1 2] [6 5 3 3 2] [3 3 5 4 9] [3 5 3 9 0]]

(defn visible? [[tree & other-trees]]
  (every? #(< % tree) other-trees))

(defn part-1 [input]
  (let [grid (parse-input input)
        dirg (utils/transpose grid)
        length (count grid)
        width (count (first grid))]
    (count
     (for [y (range 0 length)
           x (range 0 width)
           :when (or
                  (visible? (subvec (grid y) x)) ; right
                  (visible? (rseq (subvec (grid y) 0 (inc x)))) ; left
                  (visible? (subvec (dirg x) y)) ; down
                  (visible? (rseq (subvec (dirg x) 0 (inc y)))))] ; up
       nil ))))

(part-1 sample)
;; => 21
(part-1 (slurp (io/resource data)))
;; => 1690

(defn viewing-distance [[tree & other-trees]]
  (let [smaller-trees (take-while #(< % tree) other-trees)]
    (min
     (count other-trees) ; not blocked
     (inc (count smaller-trees))))) ; blocked by tree

(defn part-2 [input]
  (let [grid (parse-input input)
        dirg (utils/transpose grid)
        length (count grid)
        width (count (first grid))]
    (reduce max 0
     (for [y (range 0 length)
           x (range 0 width)]
           (* 
            (viewing-distance (subvec (grid y) x)) ; right
            (viewing-distance (rseq (subvec (grid y) 0 (inc x)))) ; left
            (viewing-distance (subvec (dirg x) y)) ; down
            (viewing-distance (rseq (subvec (dirg x) 0 (inc y))))))))) ; up

(part-2 sample)
;; => 8
(part-2 (slurp (io/resource data)))
;; => 535680
