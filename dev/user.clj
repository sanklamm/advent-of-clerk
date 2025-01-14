(ns user
  (:require [nextjournal.clerk :as clerk]
            [advent-of-clerk.index :as index]))

(clerk/serve! {:port 7878
               :browse true
               :watch-paths ["src"]})

(comment
  (clerk/build! {:paths (index/build-paths) :browse true}))

