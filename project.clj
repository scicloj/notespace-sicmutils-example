(defproject notespace-sicmutils-example "0.1.0"
  :description "An example of using Notespace to write Sicmutils notes"
  :url "https://github.com/scicloj/notespace-sicmutils-example"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :plugins [[lein-tools-deps "0.4.5"]]
  :middleware [lein-tools-deps.plugin/resolve-dependencies-with-deps-edn]
  :lein-tools-deps/config {:config-files [:install :user :project]})
