(ns heretic.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-list
  "Reads in a list of strings from a file."
  [path]
  (let [list-text (slurp path)]
    (str/split list-text #"\n")))

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

(defn a-to-an-second
  "Converts a to an when appropriate."
  [s]
  (let [a (.indexOf s " a ")
        next-word (+ a 3)]
    (if (and (not= a -1) (vowel? (.charAt s next-word)))
      (str (subs s 0 a) " an " (a-to-an-second (subs s next-word)))
      s)))

(defn find-a-list
  "Finds ' a ' in a string."
  [s]
  (take-while (partial < -1) (iterate #(.indexOf s " a " (+ %1 1)) (.indexOf s " a "))))

;;(defn a-to-an-new
  ;;"Converts a to an when appropriate."
  ;;[s]
  ;;(let [a-list (find-a-list s)
        ;;vowels (filter #(vowel? (.charAt s (+ %1 3))) a-list)]
    ;;; filter out indices of ' a 's which need not be replaced
    ;;(filter #(vowel? (.charAt s (+ %1 3))) a-list)))
    ;;;(subs s 0 i0) " an " (subs s i0+4 i1) " an " (subs s i1+4 i2)

(defn a-to-an
  "Converts a to an when appropriate."
  [s]
  (str/replace s #"( a )([aeiouAEIOU])" " an $2"))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (dotimes [_ 10]
    (let [fs (finalString)]
      (println "New")
      (time (a-to-an fs))
      (println "Second")
      (time (a-to-an-second fs))
      (println "Original")
      (time (a-to-an-original fs))))
  (println (a-to-an (finalString))))

