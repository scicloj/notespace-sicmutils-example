(defproject notespace-sicmutils-examples "0.18.1"
  :description "Demonstrating the use of Sicmutils with Notespace"
  :url "github.com/scicloj/notespace-sicmutils-example"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :plugins [[lein-tools-deps "0.4.5"]]
  :middleware [lein-tools-deps.plugin/resolve-dependencies-with-deps-edn]
  :lein-tools-deps/config {:config-files [:install :user :project]})
