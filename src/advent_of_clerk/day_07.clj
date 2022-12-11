;; # 🎄 Advent of Clerk: Day 7
{:nextjournal.clerk/visibility {:code :hide :result :hide}}

(ns advent-of-clerk.day-07
  (:require [nextjournal.clerk :as clerk]
            [util.utils :as utils]
            [clojure.string :as str]
            [clojure.java.io :as io]
            [com.rpl.specter :as sp]))

(def day 7)

(utils/get-input-data day)

(def data (str "input-" day ".txt"))

(def sample "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

{:nextjournal.clerk/visibility {:code :show :result :show}}

(defn parse-tree [input]
  (let [open (-> input
                 (str/replace "$ cd .." "]")
                 (str/replace #"\$ cd (.*?)\n" " [")
                 (str/replace #"[^\d \[\]]" ""))
        missing (- (get (frequencies open) \[)
                   (get (frequencies open) \]))]
    (read-string (str open (apply str (repeat missing "]"))))))

(parse-tree sample)
;; => [14848514 8504156 [29116 2557 62596 [584]] [4060174 8033020 5626152 7214296]]
(parse-tree (slurp (io/resource data)))

(defn dir-sizes [tree]
  (->> tree
       (tree-seq vector? identity)
       (filter vector?)
       (map #(sp/select (sp/walker number?) %))
       (map (partial reduce +))
       sort))

(dir-sizes (parse-tree sample))
;; => (584 94853 24933642 48381165)

(defn part-1 [tree]
  (->> (dir-sizes tree)
       (filter #(>= 100000 %))
       (reduce +)))

(part-1 (parse-tree sample))
;; => 95437
(part-1 (parse-tree (slurp (io/resource data))))
;; => 1077191

(defn part-2 [tree]
  (let [dirs tree
        space-needed (- 30000000 (- 70000000 (last dirs)))]
    (->> dirs
         (drop-while #(> space-needed %))
         first)))

(part-2 (dir-sizes (parse-tree sample)))
;; => 24933642
(part-2 (dir-sizes (parse-tree (slurp (io/resource data)))))
;; => 5649896

