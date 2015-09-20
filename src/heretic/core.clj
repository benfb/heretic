(ns heretic.core
  (:gen-class)
  (:require [clojure.string :as s]))

(defn read-list
  "Reads in a list of strings from a file."
  [path]
  (let [list-text (slurp path)]
    (s/split list-text #"\n")))

(def decades ["00s" "60s" "70s" "80s" "90s"])
(def bands (read-list "resources/bands.txt"))
(def pretentions (read-list "resources/pretentions.txt"))
(def modifiers (read-list "resources/modifiers.txt"))
(def nouns (read-list "resources/nouns.txt"))
(def p4k (read-list "resources/p4k.txt"))

(defn modifier
  []
  (if (> (rand 2) 1.7)
    (str (rand-nth modifiers) ", " (modifier))
    (rand-nth modifiers)))

(defn oldBandComparison
  []
  (str "Similar, but not quite identical to the " (rand-nth pretentions) " of " (rand-nth decades) "-era " (rand-nth bands) " and " (rand-nth decades) "-era " (rand-nth bands)))

(defn previousEffort
  []
  (str "their previous effort feels " (modifier) " in comparison."))

(defn firstHalf
  []
  (str "The first half of the album is a " (modifier) " " (rand-nth nouns) ", while"))

(defn secondHalf
  []
  (str "the second half is more of a " (modifier) " " (rand-nth nouns) "."))

(defn finalString
  []
  (str (oldBandComparison) ", " (previousEffort) " " (firstHalf) " " (secondHalf) " "
       (when (> (rand 2) 1.0)
         (rand-nth p4k))))

(defn vowel? [c] (contains? (set "aeiouAEIOU") c))

(defn a-to-an-original
  "Converts a to an when appropriate."
  [fs]
  (if (not= (.indexOf fs " a ") -1)
    (let [a (.indexOf fs " a ")]
      (if (vowel? (.charAt fs (+ a 3)))
        (let [substr1 (subs fs 0 a)
              substr2 (subs fs (+ a 3))]
          (str substr1 " an " (a-to-an-original substr2)))
        fs))
    fs))

(defn a-to-an
  "Converts a to an when appropriate."
  [s]
  (let [a (.indexOf s " a ")
        next-word (+ a 3)]
    (if (and (not= a -1) (vowel? (.charAt s next-word)))
      (str (subs s 0 a) " an " (a-to-an (subs s next-word)))
      s)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Original")
  (dotimes [_ 10] (time (a-to-an-original (finalString))))
  (println "New")
  (dotimes [_ 10] (time (a-to-an (finalString))))
  (println (a-to-an (finalString))))

