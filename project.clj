(defproject ticket-system "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]

                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [hiccup "1.0.5"]
                 [http-kit "2.3.0"]
                 [ring/ring-json "0.5.0"]
                 [org.clojure/java.jdbc "0.7.5"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [cljs-ajax "0.8.1"]
                 [day8.re-frame/http-fx "0.2.1"]

                 [reagent "0.8.1"]
                 [re-frame "0.10.6"]
                 [clj-commons/cljss "1.6.4"]
                 [com.andrewmcveigh/cljs-time "0.5.2"]
                 [binaryage/devtools "1.0.2"]]

  :main ^:skip-aot ticket-system.server.core

  :target-path "target/%s"
  
  :source-paths ["src" "target"]

  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[org.clojure/clojurescript "1.10.764"]
                                  [com.bhauman/figwheel-main "0.2.0"]
                                  [cider/piggieback "0.4.0"]
                                  [figwheel-sidecar "0.5.18"]]
                   :source-paths ["dev"]
                   :repl-options {:init-ns dev.user
                                  :nrepl-middleware [cider.piggieback/wrap-cljs-repl]}}
             :dev-server {:dependencies [[ring/ring-mock "0.3.2"]]
                          :repl-options {:init-ns ticket-system.server.core}}})
