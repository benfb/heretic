(ns heretic.core-test
  (:require [clojure.test :refer :all]
            [heretic.core :refer :all]))

(deftest a-to-an-test
  (testing "Does a-to-an work?"
    (is (= "I ate an albatross." (a-to-an "I ate a albatross.")))))

(deftest a-to-an-multiple-test
  (testing "Does a-to-an work if there are multiple corrections?"
    (is (= "I ate an albatross and an apple." (a-to-an "I ate a albatross and a apple.")))))

(deftest a-to-an-not-first-test
  (testing "Does a-to-an work if the first 'a' doesn't need to be corrected?"
    (is (= "I ate a bear and an albatross." (a-to-an "I ate a bear and a albatross.")))))
