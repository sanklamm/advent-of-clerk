;; # 🎄 Advent of Clerk: Day 5
{:nextjournal.clerk/visibility {:code :hide}}

(ns advent-of-clerk.day-05
  (:require [nextjournal.clerk :as clerk]
            [util.utils :as utils]
            [clojure.string :as str]
            [clojure.java.io :as io]))

(def day 5)

(utils/get-input-data day)

(def data (str "input-" day ".txt"))
(def input (slurp (io/resource data)))

{:nextjournal.clerk/visibility {:code :show}}

(def sample
  "    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 
move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")

(defn parse-stacks [data]
  (let [; filter input for stack lines only
        lines (->> data
                   (str/split-lines)
                   (filter #(re-find #"\[" %)))
        ; look for the sequence of either [\w] or \s\s\s in each line
        rows  (map #(map second (re-seq #"(?:\[(\w)\]| ( ) ) ?" %)) lines)
        ; transpose, reverse and drop nils
        cols  (->> (utils/transpose rows)
                   (mapv #(->> % reverse (take-while some?) vec)))]
    cols))

(parse-stacks sample)
;; => [["Z" "N"] ["M" "C" "D"] ["P"]]
;(slurp (io/resource data))
(parse-stacks input) 

(defn parse-moves [data]
  (let [; filter input for moves lines only
        lines (->> data
                   (str/split-lines)
                   (filter #(str/starts-with? % "move ")))]
    (vec
     (for [line lines
           :let [[_ amount from to] (re-matches #"move (\d+) from (\d+) to (\d+)" line)]]
       {:from   (dec (parse-long from)) ; zero-based index
        :to     (dec (parse-long to))   ; zero-based index
        :amount (parse-long amount)}))))

(parse-moves sample)
;; => [{:from 1, :to 0, :amount 1} {:from 0, :to 2, :amount 3} {:from 1, :to 0, :amount 2} {:from 0, :to 1, :amount 1}]
(parse-moves input)

(defn move-one [stacks from to]
  (let [el (peek (nth stacks from))]
    (-> stacks
        (update from pop)
        (update to conj el))))

(defn move [stacks {:keys [from to amount]}]
  (reduce
   (fn [s _]
     (move-one s from to))
   stacks
   (range amount)))

(defn part-1 [data]
  (let [stacks (parse-stacks data)
        moves (parse-moves data)
        stacks' (reduce move stacks moves)]
    (str/join (map peek stacks'))))

(part-1 sample)
;; => "CMZ"
(part-1 input)
;; => "VJSFHWGFT"

(defn move-2 [stacks {:keys [from to amount]}]
  (let [stack (take-last amount (nth stacks from))]
    (-> stacks
        (update from #(vec (drop-last amount %)))
        (update to #(vec (concat % stack))))))

(defn part2 [data]
  (let [stacks  (parse-stacks data)
        moves   (parse-moves data)
        stacks' (reduce move-2 stacks moves)]
    (str/join (map peek stacks'))))

(part2 sample)
;; => "MCD"
(part2 input)
;; => "LCTQFBVZV"
