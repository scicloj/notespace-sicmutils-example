(ns notespace-sicmutils-example.double-pendulum
  (:require [notespace.api :as notespace]
            [notespace.kinds :as kind]
            [notespace-sicmutils.setup]
            [sicmutils.env]
            [sicmutils.env :as e]))

^kind/hidden
(comment
  ;; Here is how you can use Notespace in a minimal way to render this namespace. Please keep these calls inside a comment block.
  ;; Notespace does offer a mode dynamic experience, with some integration into popular Clojure editors' functions.
  ;; See the Notespace tutorial:
  ;; https://scicloj.github.io/notespace/doc/notespace/v3-experiment1-test/index.html
  ;; A screencast is coming soon.

  ;; Initialize notespace:
  (notespace/init)

  ;; Alternatively, Initialize notespace and open the browser view:
  (notespace/init-with-browser)

  ;; Evauating all notes in this namespace, and updating the browser view:
  (notespace/eval-this-notespace)

  ;; Notespace does have bugs.
  ;; If the browser seems out-of-sync, you can try to refresh it.
  ;; If the state still seems problematic, you can init again.

  ;; Rendering current browser view into a static html file (under the `docs` directory):
  (notespace/render-static-html))


^kind/hidden
(sicmutils.env/bootstrap-repl!)

["# The double pendulum"]

["## Chapter 0: setup"]

(require '[sicmutils.examples.double-pendulum :as double-pendulum])

(require '[notespace.kinds :as kind]
         '[tablecloth.api :as tablecloth]
         '[aerial.hanami.common :as hanami-common]
         '[aerial.hanami.templates :as hanami-templates])

["## Chapter 1: equations"]

((double-pendulum/state-derivative 'm_1 'm_2 'l_1 'l_2 'g)
 (up 't
     (up 'θ_0 'φ_0)
     (up 'θdot_0 'φdot_0)))

["## Chapter 2: simulation"]

(def step 0.01)
(def horizon 10)

(defonce double-pendulum-data
  (->> (range step horizon step)
       (pmap (fn [t]
               (double-pendulum/evolver
                {:t t})))
       (map (fn [[t [p1x p1y] [p2x p2y]]]
              {:t   t
               :p1x p1x
               :p1y p1y
               :p2x p2x
               :p2y p2y}))))

^kind/dataset
(-> double-pendulum-data
    (tablecloth/dataset "double pendulum"))

["## Chapter 3: data wrangling"]

(def double-pendulum-points-data
  (->> double-pendulum-data
       (mapcat (fn [{:keys [t p1x p1y p2x p2y]}]
                 [{:t  t
                   :x  p1x
                   :y  p1y
                   :id :p1}
                  {:t  t
                   :x  p2x
                   :y  p2y
                   :id :p2}] ))))

^kind/dataset
(-> double-pendulum-points-data
    (tablecloth/dataset "double pendulum points"))

(def double-pendulum-segments-data
  (->> double-pendulum-data
       (mapcat (fn [{:keys [t p1x p1y p2x p2y]}]
                 [{:t  t
                   :x 0
                   :y 0
                   :x2 p1x
                   :y2 p1y
                   :id :p1}
                  {:t  t
                   :x  p1x
                   :y  p1y
                   :x2 p2x
                   :y2 p2y
                   :id :p2}]))))

^kind/dataset
(-> double-pendulum-segments-data
    (tablecloth/dataset "double pendulum segments"))

["## Chapter 4: Visualization"]

^kind/vega
(hanami-common/xform
 hanami-templates/layer-chart
 :LAYER [(hanami-common/xform
          hanami-templates/point-chart
          :DATA double-pendulum-points-data
          :COLOR {:field :id :type :nominal}
          :SIZE {:condition {:test  "abs(selected_t - datum['t']) < 0.00001"
                             :value 50}
                 :value     1}
          :SELECTION {:selected {:fields [:t]
                                 :type   :single
                                 :bind   {:t {:min step
                                              :max (- horizon step)
                                              :input :range
                                              :step step}}}})
         {:data {:values double-pendulum-segments-data}
          :mark "rule"
          :encoding
          {:x  {:field :x, :type "quantitative"}
           :y  {:field :y, :type "quantitative"}
           :x2 {:field :x2, :type "quantitative"}
           :y2 {:field :y2, :type "quantitative"}
           :color {:field :id :type :nominal}
           :opacity {:condition {:test  "abs(selected_t - datum['t']) < 0.00001"
                                 :value 1}
                  :value     0}}}])

["Please play with the slider to see the pendula play together."]

["."]
