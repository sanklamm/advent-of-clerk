(ns util.utils
  (:require
   [util.config :as conf]
   [clj-http.client :as client]
   [clojure.java.io :as io]
   [clojure.string :as str]))



(defn get-input-data
  "makes an http request for the input file of the given day"
  [day]
  (->> (client/get (str "https://adventofcode.com/2022/day/" day "/input")
                   {:cookies {"session" {:value conf/aoc-session}}})
       :body
       (spit (str "resources/input-" day ".txt"))))


(defn parse-binary [s]
  (Integer/parseInt s 2))

(defn bitflip [s]
  (map {\1 \0 \0 \1} s))

(defn read [file]
  (slurp (io/resource file)))

(defn read-lines [file]
  (-> file
      read
      (str/split-lines)))

(defn read-longs [file]
  (map parse-long (read-lines file)))
