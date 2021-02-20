(ns notespace-sicmutils-example.core
  (:require [notespace.api :as notespace]
            [notespace.kinds :as kind]
            [notespace-sicmutils.setup]
            [sicmutils.env]))

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

["# Chapter 1
Let us compute something:"]

(log (/ (tan 'x)))

